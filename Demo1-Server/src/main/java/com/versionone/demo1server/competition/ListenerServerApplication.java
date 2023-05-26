package com.versionone.demo1server.competition;

import com.acoinfo.vsoa.*;

import java.net.InetSocketAddress;

public class ListenerServerApplication {
    private  static String  SERVER_NAME   = "light_server";
    private  static String  SERVER_INFO   = "\"java language VSOA server\"";
    private  static String  PASSWORD      = "123456";
    private  static String  SERVER_ADDR   = "0.0.0.0";
    private  static int     SERVER_PORT   = 3001;

    static Server server;

    public static void main(String[] args) {

        /*
         * Initialize server
         */
        try {
            ServerOption opt = new ServerOption(SERVER_INFO, PASSWORD, false);
            InetSocketAddress address = new InetSocketAddress(SERVER_ADDR, SERVER_PORT);
            server = new Server(opt) {
                @Override
                public void onClient(CliHandle client, boolean link) {
                    if (!client.isConnected()) {
                        System.out.println("disconnected");
                    }

                    System.out.println("Client link " + link + " address: " + client.address().toString());
                }
            };

            /*
             * Start server
             */
            if (!server.start(address, null)) {
                return;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            return ;
        }

        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
