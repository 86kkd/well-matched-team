package com.versionone.demo1server.threads;

public class Thread {

    static {
        //开启一个图片创建线程
        java.lang.Thread imageThread = new ImageCreateThread();
        imageThread.start();
    }

    public static void start(){
    }
}
