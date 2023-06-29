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

            // 发送数据给Python服务器
            OutputStream outputStream = socket.getOutputStream();
            String message = Base64Util.getImgFileToBase642(bytes);
            outputStream.write(message.getBytes());
            // 接收Python服务器返回的数据

            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println("\n1111111111111");
            String response = reader.readLine();
            socket.close();
            System.out.println("2");
            System.out.println("Received from Python server: ");
            // 关闭套接字

            return Base64Util.getImgBase64ToImgFile(response);
        } catch (IOException e) {
            e.printStackTrace();
            return Base64Util.getImgBase64ToImgFile(null);
        }
    }
}
