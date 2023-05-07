package com.versionone.demo1server.threads;

import com.versionone.demo1server.object.dto.Image;
import com.versionone.demo1server.utils.CommandUtil;
import com.versionone.demo1server.utils.GetRandomObjectUtil;

/**
 * 图片创建线程，不断创建新的图片
 */
public class ImageCreateThread extends Thread{

    /**
     * 线程执行逻辑
     */
    @Override
    public void run() {
        while (true){  //死循环

            Image.nowImage = CommandUtil.getImage();

            try {
                Thread.sleep(1000); //间隔一秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
