package com.versionone.demo1server.utils;

import com.acoinfo.vsoa.Payload;

public class PayloadUtil {

    public static Payload getGearPayload(int gear){
        String p = gearPayload(gear);
        return new Payload(p,null);
    }

    private static String gearPayload(int gear){
        return "{\"gear\":" +
                gear +
                "}";
    }

    public static Payload getLightPayload(int light){
        String p = lightPayload(light);
        return new Payload(p,null);
    }

    private static String lightPayload(int light){
        return "{\"light\":" +
                light +
                "}";
    }

    public static Payload getMileagePayload(int mileage){
        String p = mileagePayload(mileage);
        return new Payload(p,null);
    }

    private static String mileagePayload(int mileage){
        return "{\"mileage\":" +
                mileage +
                "}";
    }

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
}
