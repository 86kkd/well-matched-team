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
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

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
//        System.out.println(Arrays.toString(bytes));
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
                IntelligentImageQueue.IS_TO_BE_PROCESSED = true;                         //开始处理图片
                int i = 0;
                // 遍历视频帧
                while (true) {
                    Frame frame = grabber.grabImage();
                    if (frame == null) {
                        break;
                    }

                    // 将当前帧转为Mat对象
                    OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
                    Mat mat = converter.convert(frame);

                    // 将当前帧转为bufferedImage
                    Java2DFrameConverter converter2D = new Java2DFrameConverter();
                    BufferedImage bufferedImage = converter2D.convert(frame);
                    System.out.println(File.extendName);
                    // 旋转图像
                    BufferedImage rotatedImage = rotateImage(bufferedImage, "mp4".equals(File.extendName) ? 0 : 270 ); // 旋转90度


                    // 将bufferedImage转为byte数组
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(rotatedImage, "jpg", byteArrayOutputStream);
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();
                    System.out.print("*");
                    // 对imageBytes进一步处理
                    entry(imageBytes);
                    if (i == 0){
                        IntelligentImageQueue.start();
                        i++;
                    }
                }
                IntelligentImageQueue.IS_TO_BE_PROCESSED = false;                     //图片处理完成
                grabber.stop();
                System.out.println("grabber.stop();");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 旋转图像方法
    private static BufferedImage rotateImage(BufferedImage image, double degrees) {
        double radians = Math.toRadians(degrees);
        int width = image.getWidth();
        int height = image.getHeight();
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.floor(width * cos + height * sin);
        int newHeight = (int) Math.floor(height * cos + width * sin);

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g2d = rotatedImage.createGraphics();
        AffineTransform transform = new AffineTransform();
        transform.translate((newWidth - width) / 2, (newHeight - height) / 2);
        transform.rotate(radians, width / 2, height / 2);
        g2d.setTransform(transform);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
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
