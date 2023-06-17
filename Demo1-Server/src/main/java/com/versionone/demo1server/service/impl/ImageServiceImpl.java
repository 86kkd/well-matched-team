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

package com.versionone.demo1server.service.impl;

import com.versionone.demo1server.object.dto.File;
import com.versionone.demo1server.service.ImageService;
import com.versionone.demo1server.statics.IntelligentImageQueue;
import com.versionone.demo1server.utils.Queue;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 图片事务
 */
@Service
public class ImageServiceImpl implements ImageService {

    /**
     * 获取随机图片
     * @param response 响应对象
     * @throws IOException IO异常
     */
    @Override
    public void getNewImage(HttpServletResponse response) throws IOException{
        byte[] bytes = getOne();
        response.setContentType("image/png");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.close();
    }

    @Override
    public void videoToImages() {
        Thread thread = new VideoToImagesThread();
        thread.start();
    }

    private static class VideoToImagesThread extends Thread{
        @Override
        public void run() {
            try {
                byte[] videoBytes = File.nowVideoFile; // 输入的视频byte数组
                ByteArrayInputStream inputStream = new ByteArrayInputStream(videoBytes); // 将byte数组转为inputStream
                // 使用OpenCVFrameGrabber读取视频流
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream);
                grabber.start();
                int i = 0;
                // 遍历视频帧
                while (true) {
                    Frame frame = grabber.grabImage();
                    if (frame == null) {
                        break;
                    }

                    // 将当前帧转为bufferedImage
                    Java2DFrameConverter converter = new Java2DFrameConverter();
                    BufferedImage bufferedImage = converter.convert(frame);

                    // 将bufferedImage转为byte数组
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();
                    System.out.print("*");
                    // 对imageBytes进一步处理
                    entry(imageBytes);
                    if (i == 0){
                        IntelligentImageQueue.start();
                        i++;
                    }
                }

                grabber.stop();
                System.out.println("grabber.stop();");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public byte[] getOne() {
        return IntelligentImageQueue.getNewImg();
    }

    private static void entry(byte[] bytes){
        Queue<byte[]> queue = IntelligentImageQueue.beforeQueue;
        queue.enqueue(bytes);
    }

    /*
     * 重复的代码段
     */
   /* private static class ImageProcessThread extends Thread{
        @Override
        public void run() {
            Queue<byte[]> queue = IntelligentImageQueue.beforeQueue;
            while (!queue.isEmpty()){
                byte[] after = CommandUtil.getImage(queue.dequeue());
                IntelligentImageQueue.entry(after);
            }
        }
    }

    private void process(){
        Thread p = new ImageProcessThread();
        p.start();
    }*/
}
