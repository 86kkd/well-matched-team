package com.versionone.demo1server.statics;

import java.util.Random;

/**
 * 静态对象类
 */
public class StaticObject {

    public  static String  SERVER_NAME   = "automobile_dash_board";
    public  static String  PASSWORD      = "123456";
    public  static String  POS_ADDRESS   = "192.168.116.130";
    public  static int     POS_PORT      = 3000;

    public static String SpeedPlayload(int speed){
        return "{\"speed\":" +
                speed +
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
