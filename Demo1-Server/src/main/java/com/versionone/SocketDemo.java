package com.versionone;


import java.io.*;
import java.net.Socket;

public class SocketDemo {
    public static void main(String[] args) {
        while (true) {
            String host = "127.0.0.1";  // Python服务器的地址
            int port = 8888;            // Python服务器的端口号

            try {
                // 连接到Python服务器
                Socket socket = new Socket(host, port);
                System.out.println("Connected to Python server");

                // 发送数据给Python服务器
                OutputStream outputStream = socket.getOutputStream();
                String message = StaticString.STR;
                outputStream.write(message.getBytes());
                // 接收Python服务器返回的数据

                InputStream inputStream = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String response = reader.readLine();
                socket.close();
                System.out.println("2");
                System.out.println("Received from Python server: ");
                // 关闭套接字
                System.out.println(response.substring(0, 10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
