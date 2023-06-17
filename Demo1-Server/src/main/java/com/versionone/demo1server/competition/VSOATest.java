package com.versionone.demo1server.competition;

import com.acoinfo.vsoa.*;
import com.acoinfo.vsoa.Error;
import com.versionone.demo1server.statics.Redis;

import java.net.InetSocketAddress;

import static com.acoinfo.vsoa.Constant.VSOA_DEF_CONN_TIMEOUT;
import static com.acoinfo.vsoa.Request.*;
import static com.versionone.demo1server.statics.StaticObject.*;

public class VSOATest {

    public static void main(String[] args) {
        /*Thread thread = new ClientCreateThread();
        thread.start();*/

        while (true) {
            if (!Redis.IS_CONNECTED){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            Client carClient = Redis.CAR_CLIENT;
            Payload payload = new Payload(SpeedPlayload(20),null);
            carClient.call("/speed",VSOA_METHOD_SET,payload, new CBCall() {
                @Override
                public void callback(Error error, Payload payload, int tunid) {
                    if (error != null) {
                        System.out.println("RPC call error:" + error.message);
                    } else {
                        System.out.println("RPC call reply:" + payload.param);
                    }
                }
            },2000);
            break;
        }

    }
    /*private static class ClientCreateThread extends Thread{
        @Override
        public void run() {
            Client carClient = Redis.CAR_CLIENT;
            VsoaSocketAddress address = Position.lookup(new InetSocketAddress(POS_ADDRESS, POS_PORT), SERVER_NAME, 0);
            if (!carClient.connect(address,null,VSOA_DEF_CONN_TIMEOUT)) {
                System.out.println("Connected with server failed" + address.toString());
                return;
            }
            System.out.println("qqqq");
            Redis.IS_CONNECTED = true;
        }
    }*/
}
