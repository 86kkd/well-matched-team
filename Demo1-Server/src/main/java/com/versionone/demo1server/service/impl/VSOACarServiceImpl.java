package com.versionone.demo1server.service.impl;

import com.acoinfo.vsoa.*;
import com.acoinfo.vsoa.Error;
import com.versionone.demo1server.service.VSOACarService;
import com.versionone.demo1server.statics.Redis;
import com.versionone.demo1server.utils.RuleMatch;
import org.springframework.stereotype.Service;

import static com.acoinfo.vsoa.Request.VSOA_METHOD_GET;
import static com.versionone.demo1server.statics.StaticObject.*;

@Service
public class VSOACarServiceImpl implements VSOACarService {
    @Override
    public String getSpeed() {
        return sendRequest("/speed");
    }

    @Override
    public String setSpeed(int speed) {
        if (!Redis.IS_CONNECTED){
            return "中控平台未连接成功，请重新启动系统";
        }
        if (!RuleMatch.speedMatching(speed)){
            return "速度值非法";
        }
        return sendRequest("/speed",getSpeedPayload(speed));
    }

    @Override
    public String getPower() {
        return sendRequest("/power");
    }

    @Override
    public String setPower(int power) {
        if (!Redis.IS_CONNECTED){
            return "中控平台未连接成功，请重新启动系统";
        }
        if (RuleMatch.powerMatching(power)){
            return "电量值非法";
        }
        return sendRequest("/power",getPowerPayload(power));
    }

    //TODO
    @Override
    public String setMileage(int mileage) {
        if (!Redis.IS_CONNECTED){
            return "中控平台未连接成功，请重新启动系统";
        }
        return null;
    }

    @Override
    public String setLight(int light) {
        if (!Redis.IS_CONNECTED){
            return "中控平台未连接成功，请重新启动系统";
        }
        if (!RuleMatch.lightMatching(light)){
            return "灯光值非法";
        }
        return sendRequest("/light",getLightPayload(light));
    }

    //TODO
    @Override
    public String setGear(int gear) {
        if (!Redis.IS_CONNECTED){
            return "中控平台未连接成功，请重新启动系统";
        }
        if (!RuleMatch.gearMatching(gear)){
            return "挡位值非法";
        }
        return null;
    }

    private String sendRequest(String url){
        Client client = Redis.CAR_CLIENT;
        final String[] msg = new String[1];
        client.call(url, VSOA_METHOD_GET, null, new CBCall() {
            @Override
            public void callback(Error error, Payload payload, int tunid) {
                if (error != null) {
                    System.out.println("RPC call error:" + error.message);
                    msg[0] = error.message;
                } else {
                    Stream stream = client.createStream(tunid, Stream.DEF_TIMEOUT);
                    if (stream != null) {
                        byte[] data;
                        while ((data = stream.read()) != null) {
                            StringBuilder strData = new StringBuilder();
                            for (byte datum : data) {
                                strData.append(Integer.toHexString(datum));
                            }
                            System.out.println("Stream received:" + strData);
                            msg[0] = strData.toString();
                        }
                    }
                }
            }
        }, 2000);
        return msg[0];
    }

    private String sendRequest(String url, Payload payload){
        Client carClient = Redis.CAR_CLIENT;
        final String[] msg = new String[1];
        carClient.call(url, com.acoinfo.vsoa.Request.VSOA_METHOD_SET,payload, new CBCall() {
            @Override
            public void callback(Error error, Payload payload, int tunid) {
                if (error != null) {
                    System.out.println("RPC call error:" + error.message);
                    msg[0] = error.message;
                } else {
                    System.out.println("RPC call reply:" + payload.param);
                    msg[0] = payload.param;
                }
            }
        },2000);
        return msg[0];
    }
}
