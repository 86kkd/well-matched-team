package com.versionone.demo1server.statics;

import com.acoinfo.vsoa.Payload;

import java.util.Random;

/**
 * 静态对象类
 */
public class StaticObject {

    public  static String  SERVER_NAME   = "automobile_dash_board";
    public  static String  PASSWORD      = "123456";
    public  static String  POS_ADDRESS   = "192.168.116.130";
    public  static int     POS_PORT      = 3000;

    public static Payload getSpeedPayload(int speed){
        String p = speedPayload(speed);
        return new Payload(p,null);
    }

    private static String speedPayload(int speed){
        return "{\"speed\":" +
                speed +
                "}";
    }

    public static Payload getPowerPayload(int power){
        String p = powerPayload(power);
        return new Payload(p,null);
    }

    private static String powerPayload(int power){
        return "{\"power\":" +
                power +
                "}";
    }

    static {
        RANDOM = new Random();
    }

    /**
     * 随机数变量
     */
    public static final Random RANDOM ;

}
