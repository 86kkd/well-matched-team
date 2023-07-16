package com.versionone;


import com.versionone.demo1server.utils.Base64Util;
import com.versionone.demo1server.utils.CommandUtil;
import com.versionone.demo1server.utils.FileUtil;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SocketDemo {
    public static void main(String[] args) {
        while (true) {
            File file = new File("C:\\Users\\26349\\Pictures\\cabc30fc-e7726578.jpg");
            byte[] bytes = FileUtil.fileToByte(file);
            byte[] image = CommandUtil.getImage(bytes);
            System.out.println("new String(image)");
        }
    }
}
