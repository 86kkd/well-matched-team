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

    public static boolean IS_TO_BE_PROCESSED;

    public static final Queue<byte[]> beforeQueue = new Queue<>();

    private static final Queue<byte[]> imageQueue = new Queue<>();

    private static byte[] temp ;

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
            while (IS_TO_BE_PROCESSED || isContinue()){
                byte[] buf = getImgByte();
                if (buf == null){
                    continue;
                }
                temp = buf;
//                System.out.println(1);
                byte[] res = CommandUtil.getImage(buf);
//                System.out.println(2);
                if (res!=null){
                    entry(res);
                }
                System.out.println(111);
            }
        }
    }

    private static byte[] getImgByte(){
        return isContinue() ? beforeQueue.dequeue() : null;
    }

    private static boolean isContinue(){
        return !beforeQueue.isEmpty();
    }

    /**
     * 获取最新的图片
     * @return 图片字节流
     */
    public static byte[] getNewImg(){

        return temp;
    }

    /**
     * 图片入队
     * @param buf 图片字节流
     */
    public static void entry(byte[] buf){
        imageQueue.enqueue(buf);
    }

}
