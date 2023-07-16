package com.versionone.demo1server;

import com.versionone.demo1server.utils.Base64Util;
import com.versionone.demo1server.utils.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) {
        while (true){
            try {
                ServerSocket serverSocket = new ServerSocket(5000);
                System.out.println("Server started. Waiting for connections...");

                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String line = reader.readLine();

                String[] split = line.split("#");
                System.out.println(split.length);
                File file = FileUtil.byteToFile(Base64Util.getImgBase64ToImgFile(split[0]), "./", "1.png");
                System.out.println(file.createNewFile());
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
