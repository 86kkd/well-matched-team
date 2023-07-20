package com.versionone.demo1server.threads;

import com.versionone.demo1server.object.vo.ResultObject;
import com.versionone.demo1server.statics.Redis;
import com.versionone.demo1server.utils.Base64Util;
import com.versionone.demo1server.utils.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketClientThread extends java.lang.Thread {

    @Override
    public void run() {
        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(5000);
                System.out.println("Server started. Waiting for connections...");

                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String line = reader.readLine();

                String[] split = line.split("#");
                /*System.out.println(split.length);
                File file = FileUtil.byteToFile(Base64Util.getImgBase64ToImgFile(split[0]), "./", "1.png");
                System.out.println(file.createNewFile());*/

                Redis.STRING_QUEUE.enqueue(line);
                Redis.RESULT_OBJECT_QUEUE.enqueue(new ResultObject(split));

                System.out.println("Client disconnected.");
                reader.close();
                clientSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
