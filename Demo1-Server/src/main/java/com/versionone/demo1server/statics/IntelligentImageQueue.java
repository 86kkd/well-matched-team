package com.versionone.demo1server.statics;

import com.versionone.demo1server.utils.CommandUtil;
import com.versionone.demo1server.utils.Queue;

/**
 * * 　　　　　　　　┏┓　　　┏┓+ +
 * * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * * 　　　　　　　┃　　　　　　　┃
 * * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * * 　　　　　　 ████━████ ┃+
 * * 　　　　　　　┃　　　　　　　┃ +
 * * 　　　　　　　┃　　　┻　　　┃
 * * 　　　　　　　┃　　　　　　　┃ + +
 * * 　　　　　　　┗━┓　　　  ┏━┛
 * * 　　　　　　　　　┃　　　┃
 * * 　　　　　　　　　┃　　　┃ + + + +
 * * 　　　　　　　　　┃　　　┃　　　　 Code is far away from bug with the animal protecting
 * * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无 bug
 * * 　　　　　　　　　┃　　　┃
 * * 　　　　　　　　　┃　　　┃　　+
 * * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * * 　　　　　　　　　┃ 　　　　　　　┣┓
 * * 　　　　　　　　　┃ 　　　　　　　┏┛
 * * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * * 　　　　　　　　　　┃┫┫　┃┫┫
 * * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 */

/**
 * 智能队列，也不管智能不智能，名字牛逼就对了
 */
public class IntelligentImageQueue {

    public static final Queue<byte[]> beforeQueue = new Queue<>();

    private static final Queue<byte[]> imageQueue = new Queue<>();

    private static byte[] bufImg = null;

    public static void start(){
        Thread t = new ImageProcessThread();
        System.out.println("Thread t = new ImageProcessThread();");
        t.start();
        System.out.println("t.start();");
    }

    private static class ImageProcessThread extends java.lang.Thread{
        @Override
        public void run() {
            while (isContinue()){
                byte[] res = CommandUtil.getImage(beforeQueue.dequeue());
                entry(res);
            }
        }
    }

    public static boolean isContinue(){
        return !beforeQueue.isEmpty();
    }

    /**
     * 获取最新的图片
     * @return 图片字节流
     */
    public static byte[] getNewImg(){
        if (imageQueue.size()<=1){
            return bufImg;
        }

        bufImg = imageQueue.dequeue();

        return bufImg;
    }

    /**
     * 图片入队
     * @param buf 图片字节流
     */
    public static void entry(byte[] buf){
        imageQueue.enqueue(buf);
    }

}
