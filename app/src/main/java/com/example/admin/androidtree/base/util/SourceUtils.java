package com.example.admin.androidtree.base.util;

import java.util.Random;

/**
 * @author Diana
 * @date 2017/7/7
 */

public class SourceUtils {
    private static final String[] imageUrls = {
            "http://img1.gtimg.com/19/1998/199805/19980526_980x1200_0.jpg",
            "http://img1.gtimg.com/19/1998/199805/19980519_980x1200_0.jpg",
            "http://img1.gtimg.com/19/1998/199829/19982943_980x1200_0.jpg",
            "http://img1.gtimg.com/19/1998/199829/19982944_980x1200_0.jpg",
            "http://img1.gtimg.com/19/1998/199829/19982946_980x1200_0.jpg",
            "http://img1.gtimg.com/19/1998/199829/19982947_980x1200_0.jpg",
            "http://img1.gtimg.com/19/1998/199829/19982949_980x1200_0.jpg",
            "http://img1.gtimg.com/19/1998/199829/19982952_980x1200_0.jpg",
            "http://img1.gtimg.com/19/1998/199829/19982954_980x1200_0.jpg",
            "http://img1.gtimg.com/19/1996/199618/19961826_980x1200_0.jpg",
            "http://img1.gtimg.com/19/1996/199618/19961828_980x1200_0.jpg",
            "http://img1.gtimg.com/19/1998/199886/19988668_980x1200_0.jpg",
            "http://img1.gtimg.com/19/1997/199750/19975024_980x1200_0.jpg",
            "http://img1.gtimg.com/19/1997/199750/19975061_980x1200_0.jpg",
            "http://img1.gtimg.com/19/1997/199750/19975062_980x1200_0.jpg",
            "http://img1.gtimg.com/17/1761/176128/17612897_980x1200_0.jpg",
            "http://img1.gtimg.com/18/1896/189608/18960867_980x1200_0.jpg",
            "http://img1.gtimg.com/18/1896/189608/18960880_980x1200_0.jpg",
            "http://img1.gtimg.com/18/1899/189922/18992273_980x1200_0.jpg",
            "http://img1.gtimg.com/18/1899/189922/18992266_980x1200_0.jpg",
            "http://img1.gtimg.com/18/1899/189922/18992269_980x1200_0.jpg"
    };

    //随机获取一个imageurl
    public static String randomImageUrl() {
        return imageUrls[new Random().nextInt(imageUrls.length)];
    }
}
