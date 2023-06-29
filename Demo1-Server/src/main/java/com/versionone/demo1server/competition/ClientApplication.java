package com.versionone.demo1server.competition;

import com.acoinfo.vsoa.*;
import com.acoinfo.vsoa.Error;

import java.net.InetSocketAddress;

/**
 * VSOA客户端类
 */
public class ClientApplication {
    private  static String  SERVER_NAME   = "automobile_dash_board";
    private  static String  PASSWORD      = "123456";
    private  static String  POS_ADDRESS   = "192.168.116.130";
    private  static int     POS_PORT      = 3000;

    public   static Client client;

    public static void main(String[] args) {

        /*
         * Initialize client
         */
        client = new Client(new ClientOption(PASSWORD, 6000, 4000, 3, false)) {

            @Override
            public void onError(Error error) {
                System.out.println("Client error:" + error.message);
            }

            @Override
            public void onConnected(String info) {
                System.out.println("Connected with server:" + info);
            }
        };

        if (true) {
            VsoaSocketAddress address;
            try {
                address = Position.lookup(new InetSocketAddress(POS_ADDRESS, POS_PORT), SERVER_NAME, 0);
                if (address == null) {
                    System.out.println("Could not find " + SERVER_NAME + " address!");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Could not find " + SERVER_NAME + " address!");
                return;
            }

            System.out.println("Server address:" + address.toString());

            if (!client.connect(address, null, Constant.VSOA_DEF_CONN_TIMEOUT)) {
                System.out.println("Connected with server failed" + address.toString());
                return;
            }
        } else {
            if (!client.connect("vsoa://" + SERVER_NAME, null, Constant.VSOA_DEF_CONN_TIMEOUT)) {
                System.out.println("Connected with server failed");
                return;
            }
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
