package com.example.admin.androidtree.server;

import android.content.Context;

import com.example.admin.androidtree.base.util.Constant;
import com.example.admin.androidtree.base.util.ProjectConstant;
import com.example.admin.androidtree.base.util.SharedPrefsUtils;
import com.example.admin.androidtree.base.util.SystemUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Server {

    private static ServerApi mDeliverApi;
    private static Context mContext;


    public static ServerApi getServerApi(Context context) {
        mContext = context;
        if (mDeliverApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getServerIp())
                    //增加请求头
                    .client(initRequestHeader(context))
                    //增加返回值为String的支持
                    .addConverterFactory(ScalarsConverterFactory.create())
                    //增加返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(GsonConverterFactory.create())
                    //增加返回值为Oservable<T>的支持
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            //这里采用的是Java的动态代理模式
            mDeliverApi = retrofit.create(ServerApi.class);
        }
        return mDeliverApi;
    }

    /**
     * 初始化请求头
     *
     * @return
     */
    private static OkHttpClient initRequestHeader(Context context) {
        final String token = getToken(context);
        final int version = SystemUtils.getVersionCode(context);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .addHeader("Authorization", "token" + token)
                        .addHeader("app-name", Constant.APP_NAME)
                        .addHeader("app-version", "" + version)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        //只针对debug测试环境下日志的打印
        if (ProjectConstant.SERVER_DEBUG) {
            //retrofit2.0中是没有日志功能,但是retrofit2.0中依赖OkHttp，
            // 所以也就能够通过OkHttp中的interceptor来实现实际的底层的请求和响应日志
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        return httpClient.build();
    }

    private static String getServerIp() {
        return ProjectConstant.SERVER_DEBUG ?
                ServerApi.DEV_SERVER_URL : ServerApi.SERVER_URL;
    }

    private static String getToken(Context context) {
        SharedPrefsUtils sharedPrefsUtils = new SharedPrefsUtils(context, Constant.SharedPrefrence.SHARED_NAME);
        return sharedPrefsUtils.getStringSP(Constant.SharedPrefrence.TOKEN, "");
    }
}
