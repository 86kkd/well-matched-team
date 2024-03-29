package com.versionone.demo1server.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 图片操作服务类
 */
public interface ImageService {

    /**
     * 获取随机图片业务逻辑
     */
     void getNewImage(HttpServletResponse response) throws IOException;

     void videoToImages();

     byte[] getOne();
}
