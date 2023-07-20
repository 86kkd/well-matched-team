package com.versionone.demo1server.utils;

import com.acoinfo.vsoa.Payload;

/**
 * Payload工具类
 */
public class PayloadUtil {


    /**
     * 获取转向灯Payload
     * @param turnlight 转向灯值
     * @return Payload
     */
    public static Payload getTurnlightPayload(int turnlight){
        String p = turnlightPayload(turnlight);
        return new Payload(p,null);
    }

    /**
     *获取转向灯Payload字符串
     * @param turnlight 转向灯值
     * @return String
     */
    private static String turnlightPayload(int turnlight){
        return "{\"turnlight\":" +
                turnlight +
                "}";
    }

    /**
     * 获取右后胎压Payload
     * @param rr 右后胎压值
     * @return Payload
     */
    public static Payload getRightRearTirePayload(double rr){
        String p = rightRearTirePayload(rr);
        return new Payload(p,null);
    }

    /**
     * 获取右后胎压Payload字符串
     * @param rr 右后胎压值
     * @return String
     */
    private static String rightRearTirePayload(double rr){
        return "{\"rightreartire\":" +
                rr +
                "";
    }

    /**
     * 获取左后胎压Payload
     * @param lr 左后胎压值
     * @return Payload
     */
    public static Payload getLeftRearTirePayload(double lr){
        String p = leftRearTirePayload(lr);
        return new Payload(p,null);
    }

    /**
     * 获取左后胎压Payload字符串
     * @param lr 左后胎压值
     * @return String
     */
    private static String leftRearTirePayload(double lr){
        return "{\"leftreartire\":" +
                lr +
                "}";
    }

    /**
     * 获取右前胎压Payload
     * @param rf 右前胎压值
     * @return Payload
     */
    public static Payload getRightFrontTirePayload(double rf){
        String p = rightFrontTirePayload(rf);
        return new Payload(p,null);
    }

    /**
     * 获取右前胎压Payload字符串
     * @param rf 右前胎压值
     * @return String
     */
    private static String rightFrontTirePayload(double rf){
        return "{\"rightfronttire\":" +
                rf +
                "}";
    }

    /**
     * 获取左前胎压Payload
     * @param lf 左前胎压值
     * @return Payload
     */
    public static Payload getLeftFrontTirePayload(double lf){
        String p = leftFrontTirePayload(lf);
        return new Payload(p,null);
    }

    /**
     * 获取左前胎压Payload字符串
     * @param lf 左前胎压值
     * @return String
     */
    private static String leftFrontTirePayload(double lf){
        return "{\"leftfronttire\":" +
                lf +
                "}";
    }

    /**
     * 获取安全带指示灯Payload
     * @param seatbelt 安全带状态
     * @return Payload
     */
    public static Payload getSeatbeltPayload(int seatbelt){
        String p = seatbeltPayload(seatbelt);
        return new Payload(p,null);
    }

    /**
     * 获取安全带指示灯Payload字符串
     * @param seatbelt 安全带状态
     * @return String
     */
    private static String seatbeltPayload(int seatbelt){
        return "{\"seatbeltwaring\":" +
                seatbelt +
                "}";
    }

    /**
     * 获取刹车Payload
     * @param brake 刹车状态
     * @return Payload
     */
    public static Payload getBrakingPayload(int brake){
        String p = brakingPayload(brake);
        return new Payload(p,null);
    }

    /**
     * 获取刹车Payload字符串
     * @param brake 刹车状态
     * @return String
     */
    private static String brakingPayload(int brake){
        return "{\"brakingwaring\":" +
                brake +
                "}";
    }

    /**
     * 获取ABS警报灯的Payload
     * @param status 状态值
     * @return Payload
     */
    public static Payload getAbsWarningPayload(int status){
        String p = absWarningPayload(status);
        return new Payload(p,null);
    }

    /**
     * 获取ABS警报灯的Payload字符串
     * @param status 状态值
     * @return String
     */
    private static String absWarningPayload(int status){
        return "{\"abswarning\":" +
                status +
                "}";
    }

    /**
     * 获取安全气囊指示灯Payload
     * @param airbag 安全气囊状态
     * @return Payload
     */
    public static Payload getAirbagPayload(int airbag){
        String p = airbagPayload(airbag);
        return new Payload(p,null);
    }

    /**
     * 获取安全气囊指示灯Payload字符串
     * @param airbag 安全气囊状态
     * @return String
     */
    private static String airbagPayload(int airbag){
        return "{\"airbag\":" +
                airbag +
                "}";
    }

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
    private static String doorsPayload(int door){
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
