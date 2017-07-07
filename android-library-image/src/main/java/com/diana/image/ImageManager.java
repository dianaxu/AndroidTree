package com.diana.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * @author Diana
 * @date 2017/6/30
 */

public class ImageManager {
    private static final int MAX_WIDTH = 1080;
    private static final int MAX_HEIGHT = 1920;
    private static final int THREAD_COUNT = 5;
    private static final int MAX_MEMORY_SIZE = 2 * 1024 * 1024;
    private static final int MAX_DISC_SIZE = 50 * 1024 * 1024;
    private static final int MAX_DISC_COUNT = 100;
    private static final int MAX_CONNECT_TIMEOUT = 5 * 1000;
    private static final int MAX_READ_TIMEOUT = 30 * 1000;


    private static ImageManager sImageManager;

    private ImageManager(Context context) {
        init(context);
    }


    private void init(Context context) {
        //缓存文件夹路径
        String path = "Android/data/" + context.getPackageName() + "/Cache";
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, path);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                // default = device screen dimensions 内存缓存文件的最大长宽
                .memoryCacheExtraOptions(MAX_WIDTH, MAX_HEIGHT)
                // default  线程池内加载的数量
                .threadPoolSize(THREAD_COUNT)
                // default 设置当前线程的优先级
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                // 如果缓存的图片总量超过限定值，先删除使用频率最小的bitmap
                //.memoryCache(new UsingFreqLimitedMemoryCache(MAX_MEMORY_SIZE))
                //没有限制缓存
                .memoryCache(new WeakMemoryCache())
                // 内存缓存的最大值
                .memoryCacheSize(MAX_MEMORY_SIZE)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // 50 Mb sd卡(本地)缓存的最大值
                .diskCacheSize(MAX_DISC_SIZE)
                //使用MD5进行加密命名，default为使用HASHCODE对UIL进行加密命名
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                // 可以缓存的文件数量
                .diskCacheFileCount(MAX_DISC_COUNT)
                // default 可以自定义缓存路径
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(context, MAX_CONNECT_TIMEOUT, MAX_READ_TIMEOUT))
                //打印debug log
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }


    public static final ImageManager getInstance(Context context) {
        if (sImageManager == null) {
            sImageManager = new ImageManager(context);
        }
        return sImageManager;
    }


    public static class ImageOption {

        public static final int IMAGE_SHAPE_RAW = 0;           //原图
        public static final int IMAGE_SHAPE_CIRCLE = 1;        //圆形
        public static final int IMAGE_SHAPE_ROUNDED = 2;       //圆角

        public Drawable failDrawable;           //加载失败的图片
        public Drawable loadingDrawable;        //加载过程的图片
        public int shape;                       //图片形状
        public int cornerRadius;                //圆角图片的角度
        public int inSampleSize;                //图片缩放比例


        public ImageOption() {
            failDrawable = null;
            loadingDrawable = null;
            shape = IMAGE_SHAPE_RAW;
            cornerRadius = 0;
        }

    }

    /**
     * 加载普通图片
     *
     * @param imageUri
     * @param imageView
     */
    public void displayImage(String imageUri, ImageView imageView) {
        displayImage(imageUri, imageView, new ImageOption(), null);
    }


    /**
     * 加载普通图片
     *
     * @param imageUri
     * @param imageView
     */
    public void displayImage(String imageUri, ImageView imageView, ImageLoadListener loadedListener) {
        displayImage(imageUri, imageView, new ImageOption(), loadedListener);
    }

    /**
     * 加载带有参数的图片
     *
     * @param imageUri
     * @param imageView
     * @param option
     */
    public void displayImage(String imageUri, ImageView imageView, ImageOption option) {
        displayImage(imageUri, imageView, option, null);
    }

    /**
     * 加载带有参数的图片
     *
     * @param imageUri
     * @param imageView
     * @param option
     */
    public void displayImage(String imageUri, ImageView imageView, ImageOption option, final ImageLoadListener loadListener) {


        if (TextUtils.isEmpty(imageUri) || imageView == null || option == null) {
            return;
        }

        ImageLoader imageLoader = ImageLoader.getInstance();


        //设置下载监听
        imageLoader.setDefaultLoadingListener(loadListener);


        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();

        builder.cacheOnDisk(true).cacheInMemory(true);

        //设置加载失败的图片
        setImageOnFailDrawable(builder, option.failDrawable);


        //设置加载过程的图片
        setImageOnLoadingDrawable(builder, option.loadingDrawable);

        //设置图片形状
        setImageShape(builder, option.shape, option.cornerRadius);


        //设置图片缩放
        setImageScale(builder, option.inSampleSize);


        builder.bitmapConfig(Bitmap.Config.RGB_565);
        builder.imageScaleType(ImageScaleType.EXACTLY);

        DisplayImageOptions options = builder.build();
        imageLoader.displayImage(imageUri, imageView, options);

    }


    /**
     * 加载普通图片,包含加载失败图片和加载过程图片
     *
     * @param imageUri
     * @param imageView
     * @param failDrawble
     * @param loadingDrawable
     */
    @Deprecated
    public void displayImage(String imageUri, ImageView imageView, Drawable failDrawble,
                             Drawable loadingDrawable) {
        ImageOption option = new ImageOption();
        option.loadingDrawable = loadingDrawable;
        option.failDrawable = failDrawble;
        displayImage(imageUri, imageView, option);
    }


    /**
     * 异步下载图片,回调中处理图片
     *
     * @param uri
     * @param listener
     */
    public void loadImage(String uri, final ImageLoadListener listener) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        DisplayImageOptions options = builder.cacheOnDisk(true).cacheInMemory(true).build();
        imageLoader.loadImage(uri, options, listener);
    }


    /**
     * 设置加载过程图片
     *
     * @param builder
     * @param loadingDrawable
     */
    private void setImageOnLoadingDrawable(DisplayImageOptions.Builder builder, Drawable loadingDrawable) {
        if (loadingDrawable != null) {
            builder.showImageOnLoading(loadingDrawable);
        }
    }


    /**
     * 设置加载失败的图片
     *
     * @param builder
     * @param failDrawable
     */
    private void setImageOnFailDrawable(DisplayImageOptions.Builder builder, Drawable failDrawable) {
        if (failDrawable != null) {
            builder.showImageOnFail(failDrawable);
        }
    }


    /**
     * 设置图片形状
     *
     * @param builder
     * @param shape
     * @param cornerRadius
     */
    private void setImageShape(DisplayImageOptions.Builder builder, int shape, int cornerRadius) {
        switch (shape) {
            case ImageOption.IMAGE_SHAPE_CIRCLE:
                builder.displayer(new CircleBitmapDisplayer());
                break;
            case ImageOption.IMAGE_SHAPE_ROUNDED:
                builder.displayer(new RoundedBitmapDisplayer(cornerRadius));
                break;
        }
    }


    /**
     * 设置图片缩放的inSampleSize
     *
     * @param builder
     * @param inSampleSize
     */
    private void setImageScale(DisplayImageOptions.Builder builder, int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        builder.decodingOptions(options);
    }


}
