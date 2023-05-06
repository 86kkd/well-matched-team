package com.versionone.demo1server.service;

import com.versionone.demo1server.object.dto.Image;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 图片操作服务类
 */
public class ImageService {

    /**
     * 获取随机图片业务逻辑
     * @param response 响应对象
     * @throws IOException 抛出IO异常
     */
    public static void getRandomImage(HttpServletResponse response) throws IOException {
        //服务器通知浏览器不要缓存
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片

        BufferedImage image = Image.nowImage;

        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        ImageIO.write(image,"PNG",response.getOutputStream());
    }
}
