package com.versionone.demo1server.utils;

import com.versionone.demo1server.object.dto.File;

import java.io.*;
import java.net.Socket;

/**
 * 命令工具类
 */
public class CommandUtil {

    public static byte[] getImage(){
        return socket(File.pendingProcessingImg);
    }

    public static byte[] getImage(byte[] bytes){
        return socket(bytes);
    }

    private static byte[] socket(byte[] bytes){
        String host = "127.0.0.1";  // Python服务器的地址
        int port = 8888;            // Python服务器的端口号

        try {
            // 连接到Python服务器
            Socket socket = new Socket(host, port);
            System.out.println("Connected to Python server");
//            socket.setSoTimeout(500);
            // 发送数据给Python服务器
//            System.out.println(1.1);
            OutputStream outputStream = socket.getOutputStream();
            String message = Base64Util.getImgFileToBase642(bytes);
            message += "#";
            outputStream.write(message.getBytes());
            // 接收Python服务器返回的数据
            System.out.println(1.2);
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            System.out.println(1.3);
//            System.out.println("\n1111111111111");
            String response = reader.readLine();
//            System.out.println(1.4);
            socket.close();
//            System.out.println("2");
//            System.out.println("Received from Python server: ");
            // 关闭套接字
//            System.out.println(1.5);
            return Base64Util.getImgBase64ToImgFile(response);
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }
}
