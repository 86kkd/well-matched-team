package com.versionone.demo1server.service.impl;

import com.acoinfo.vsoa.*;
import com.acoinfo.vsoa.Error;
import com.versionone.demo1server.service.VSOACarService;
import com.versionone.demo1server.statics.Redis;
import com.versionone.demo1server.utils.RuleMatch;
import org.springframework.stereotype.Service;

import static com.acoinfo.vsoa.Request.VSOA_METHOD_GET;
import static com.versionone.demo1server.utils.PayloadUtil.*;

@Service
public class VSOACarServiceImpl implements VSOACarService {


    @Override
    public String setDoors(int doors) {
        if (!Redis.IS_CONNECTED){
            return "中控平台未连接成功，请重新启动系统";
        }
        if (!RuleMatch.doorMatching(doors)){
            return "车门值非法";
        }
        return sendRequest("/door",getDoorsPayload(doors));
    }

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
        if (!RuleMatch.powerMatching(power)){
            return "电量值非法";
        }
        return sendRequest("/power",getPowerPayload(power));
    }


    @Override
    public String setMileage(int mileage) {
        if (!Redis.IS_CONNECTED){
            return "中控平台未连接成功，请重新启动系统";
        }
        return sendRequest("/mileage",getMileagePayload(mileage));
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
        return sendRequest("/gear",getGearPayload(gear));
    }

    private String sendRequest(String url){
        Client client = Redis.CAR_CLIENT;
        client.call(url, VSOA_METHOD_GET, null, new CBCall() {
            @Override
            public void callback(Error error, Payload payload, int tunid) {
                if (error != null) {
                    System.out.println("RPC call error:" + error.message);
                    Redis.MSG = error.message;
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
                            Redis.MSG = strData.toString();
                        }
                    }
                }
            }
        }, 2000);
        return Redis.MSG;
    }

    private String sendRequest(String url, Payload payload){
        Client carClient = Redis.CAR_CLIENT;
        carClient.call(url, com.acoinfo.vsoa.Request.VSOA_METHOD_SET,payload, new CBCall() {
            @Override
            public void callback(Error error, Payload payload, int tunid) {
                if (error != null) {
                    System.out.println("RPC call error:" + error.message);
                    Redis.MSG = error.message;
                } else {
                    System.out.println("RPC call reply:" + payload.param);
                    Redis.MSG = payload.param;
                }
            }
        },2000);
        return Redis.MSG;
    }
}
