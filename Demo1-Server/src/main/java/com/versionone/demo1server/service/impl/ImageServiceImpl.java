package com.versionone.demo1server.service.impl;

import com.versionone.demo1server.object.dto.Image;
import com.versionone.demo1server.service.ImageService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public void getNewImage(HttpServletResponse response) throws IOException{
        byte[] bytes = Image.nowImage;
        response.setContentType("image/png");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
    }
}
