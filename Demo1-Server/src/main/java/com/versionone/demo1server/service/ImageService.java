package com.versionone.demo1server.service;

import com.versionone.demo1server.object.dto.Image;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 图片操作服务类
 */
public interface ImageService {

    /**
     * 获取随机图片业务逻辑
     * @param response 响应对象
     * @throws IOException 抛出IO异常
     */
     void getRandomImage(HttpServletResponse response) throws IOException ;
}
