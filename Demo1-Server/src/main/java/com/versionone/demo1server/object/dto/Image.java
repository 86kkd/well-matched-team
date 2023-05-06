package com.versionone.demo1server.object.dto;

import com.versionone.demo1server.threads.ImageCreateThread;

import java.awt.image.BufferedImage;

public class Image {

    public static BufferedImage nowImage;

    static {
        //开启一个图片创建线程
        Thread imageThread = new ImageCreateThread();
        imageThread.start();
    }
}
