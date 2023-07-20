package com.versionone.demo1server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Controller
public class VncController {
    @RequestMapping(value = "/vnc", method = RequestMethod.GET, produces = "image/png")
    public void getVncScreenshot(HttpServletResponse response) throws IOException, AWTException {
        ProcessBuilder builder = new ProcessBuilder("C:\\Program Files\\RealVNC\\VNC Viewer\\vncviewer.exe", "-viewonly", "-shared", "192.168.116.129:5900");
        builder.redirectErrorStream(true);
        Process process = builder.start();
        InputStream inputStream = process.getInputStream();
        String output = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        System.out.println(output); // Debugging output

        Robot robot = new Robot();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle rectangle = new Rectangle(dimension);
        BufferedImage bufferedImage = robot.createScreenCapture(rectangle);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        byte[] imageData = baos.toByteArray();

        response.setContentType("image/png");
        response.setContentLength(imageData.length);
        response.getOutputStream().write(imageData);

        process.destroy();
    }
}
