package com.versionone.demo1server.utils;

import com.acoinfo.vsoa.Payload;

/**
 * Payload工具类
 */
public class PayloadUtil {


    /**
     * 获取车门Payload
     * @param door 车门值
     * @return Payload
     */
    public static Payload getDoorsPayload(int door){
        CommonUtil.printDoors(door);
        String p = doorsPayload(door);
        return new Payload(p,null);
    }

    /**
     * 获取车门Payload字符串
     * @param door 车门值
     * @return String
     */
    public static String doorsPayload(int door){
        return "{\"door\":" +
                door +
                "}";
    }

    /**
     * 获取挡位Payload
     * @param gear 挡位值
     * @return Payload
     */
    public static Payload getGearPayload(int gear){
        String p = gearPayload(gear);
        return new Payload(p,null);
    }

    /**
     * 获取挡位Payload字符串
     * @param gear 挡位值
     * @return String
     */
    private static String gearPayload(int gear){
        return "{\"gear\":" +
                gear +
                "}";
    }

    /**
     * 获取灯光Payload
     * @param light 灯光值
     * @return Payload
     */
    public static Payload getLightPayload(int light){
        String p = lightPayload(light);
        return new Payload(p,null);
    }

    /**
     * 获取灯光Payload字符串
     * @param light 灯光值
     * @return String
     */
    private static String lightPayload(int light){
        return "{\"light\":" +
                light +
                "}";
    }

    /**
     * 获取里程Payload
     * @param mileage 里程数
     * @return Payload
     */
    public static Payload getMileagePayload(int mileage){
        String p = mileagePayload(mileage);
        return new Payload(p,null);
    }

    /**
     * 获取里程Payload字符串
     * @param mileage 里程值
     * @return String
     */
    private static String mileagePayload(int mileage){
        return "{\"mileage\":" +
                mileage +
                "}";
    }

    /**
     * 获取速度Payload
     * @param speed 速度值
     * @return Payload
     */
    public static Payload getSpeedPayload(int speed){
        String p = speedPayload(speed);
        return new Payload(p,null);
    }

    /**
     * 获取速度Payload字符串
     * @param speed 速度值
     * @return String
     */
    private static String speedPayload(int speed){
        return "{\"speed\":" +
                speed +
                "}";
    }

    /**
     * 获取电量Payload
     * @param power 电量值
     * @return Payload
     */
    public static Payload getPowerPayload(int power){
        String p = powerPayload(power);
        return new Payload(p,null);
    }

    /**
     * 获取电量Payload字符串
     * @param power 电量值
     * @return String
     */
    private static String powerPayload(int power){
        return "{\"power\":" +
                power +
                "}";
    }
}
