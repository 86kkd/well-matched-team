package com.versionone.demo1server.threads;

import com.acoinfo.vsoa.Client;
import com.acoinfo.vsoa.Position;
import com.acoinfo.vsoa.VsoaSocketAddress;
import com.versionone.demo1server.statics.Redis;

import java.net.InetSocketAddress;

import static com.acoinfo.vsoa.Constant.VSOA_DEF_CONN_TIMEOUT;
import static com.versionone.demo1server.statics.Redis.*;

public class CarClientCreateThread extends java.lang.Thread {
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
}
