package com.versionone.demo1server.utils;

/**
 * 全局规则匹配公共工具类
 */
public class RuleMatch {

    /**
     * 挡位规则匹配
     * @param gear 挡位值
     * @return 是否合法
     */
    public static boolean gearMatching(Integer gear){
        switch (gear){
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return false;
            default:
                return true;
        }
    }

    /**
     * 速度匹配规则 0-240
     * @param speed 速度值
     * @return 是否合法
     */
    public static boolean speedMatching(int speed){
        return speed>=0 && speed <=240;
    }

    /**
     * 电量匹配规则
     * @param power 电量值
     * @return 是否合法
     */
    public static boolean powerMatching(int power){
        return power>=0 && power <=100;
    }

    /**
     * 灯光规则匹配
     * @param light 灯光值
     * @return 是否合法
     */
    public static boolean lightMatching(Integer light){
        switch (light){
            case 0:
            case 1:
            case 2:
            case 4:
            case 8:
                return false;
            default:
                return true;
        }
    }

    /**
     * 转向灯规则匹配
     * @param turnLight 转向灯值
     * @return 是否合法
     */
    public static boolean turnLightMatching(Integer turnLight){
        return turnLight < 0 || turnLight > 3;
    }


    /**
     * 指示灯规则匹配
     * @param status 指示灯值
     * @return 是否合法
     */
    public static boolean statusLightMatching(Integer status){
        return status != 0 && status != 1;
    }

    /**
     * 车门状态规则匹配
     * @param door 车门状态值
     * @return 是否合法
     */
    public static boolean doorMatching(Integer door){
        return door < 0 || door > 15;
        /*
        一共16种车门状态
        0000
        0001
        0010
        0011
        0100
        0101
        0110
        0111
        1000
        1001
        1010
        1011
        1100
        1101
        1110
        1111
         */
    }

    /**
     * 汽车胎压规则匹配
     * @param value 胎压值
     * @return 是否合法
     */
    public static boolean tirePressureMatching(double value){
        return !(value > 0) || !(value <= 2.5);
    }

}
