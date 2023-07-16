package com.versionone.demo1server.threads;

public class SocketClientBoot {
    public static void start(){
        java.lang.Thread thread = new SocketClientThread();
        thread.start();
    }
}
