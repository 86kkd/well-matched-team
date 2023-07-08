package com.versionone.demo1server.controller.endpoint;



import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

@ServerEndpoint("/vnc")
public class VncWebSocketEndpoint {

    private static final String VNC_SERVER_HOST = "192.168.116.129";
    private static final int VNC_SERVER_PORT = 5900;
    private Socket vncSocket;
    private Session webSocketSession;

    @OnOpen
    public void onOpen(Session session) {
        webSocketSession = session;
        try {
            vncSocket = new Socket(VNC_SERVER_HOST, VNC_SERVER_PORT);
            System.out.println("vncSocket = new Socket(VNC_SERVER_HOST, VNC_SERVER_PORT);");
            new Thread(new VncToWebSocketBridge(session)).start();
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    @OnMessage
    public void onMessage(ByteBuffer message, Session session) {
        // 处理从浏览器接收到的二进制数据
        try {
            vncSocket.getOutputStream().write(message.array(), 0, message.remaining());
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    @OnClose
    public void onClose() {
        close();
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
        close();
    }

    private void close() {
        try {
            if (vncSocket != null) {
                vncSocket.close();
            }
            if (webSocketSession != null) {
                webSocketSession.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class VncToWebSocketBridge implements Runnable {

        private final Session webSocketSession;

        public VncToWebSocketBridge(Session webSocketSession) {
            this.webSocketSession = webSocketSession;
        }

        @Override
        public void run() {
            try {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = vncSocket.getInputStream().read(buffer)) != -1) {
                    ByteBuffer byteBuffer = ByteBuffer.wrap(buffer, 0, bytesRead);
                    webSocketSession.getBasicRemote().sendBinary(byteBuffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

    }

    /*private class WebSocketToVncBridge implements Runnable {
        @Override
        public void run() {
            try {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = webSocketSession.getInputStream().read(buffer)) != -1) {
                    vncSocket.getOutputStream().write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }*/
}
