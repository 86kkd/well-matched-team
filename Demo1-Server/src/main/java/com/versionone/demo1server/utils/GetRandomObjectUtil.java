package com.versionone.demo1server.utils;

import java.awt.image.BufferedImage;

import static com.versionone.demo1server.statics.StaticObject.RANDOM;

/**
 * 获取随机对象工具类
 * 作者：016
 */
public class GetRandomObjectUtil {
//    public static final Random RANDOM = new Random();    //静态的获取随机数对象

    /**
     * 获取图片
     * @return 缓冲区图片对象
     */
    public static BufferedImage image(){
        /*
         *生成随机的高和宽
         * 宽：[640-1640]
         * 高：[320-1320]
         */
        int width = RANDOM.nextInt(1001)+640 , height = RANDOM.nextInt(1001)+320;

        /*
         * BufferedImage介绍
         * image是一个抽象类，BufferedImage是其实现类，
         * 是一个带缓冲区图像类，主要作用是将一幅图片加载
         * 到内存中（BufferedImage生成的图片在内存里有一
         * 个图像缓冲区，利用这个缓冲区我们可以很方便地操作
         * 这个图片），提供获得绘图对象、图像缩放、选择图像
         * 平滑度等功能，通常用来做图片大小变换、图片变灰、
         * 设置透明不透明等。
         */
        BufferedImage image =
                new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB); //创建图片对象
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //生成像素点
                int a = RANDOM.nextInt(256); //0-255
                int r = RANDOM.nextInt(256); //0-255
                int g = RANDOM.nextInt(256); //0-255
                int b = RANDOM.nextInt(256); //0-255

                int p = (a<<24) | (r<<16) | (g<<8) | b;
                image.setRGB(x,y,p);
            }
        }

        return image;
    }
}
