package com.versionone.demo1server.service;

/**
 * VSOA协议控制汽车仿真中控平台
 */
public interface VSOACarService {

    /**
     * 获取汽车速度
     * @return 信息
     */
    String getSpeed();

    /**
     * 设置汽车速度
     * @param speed 速度值
     * @return 信息
     */
    String setSpeed(int speed);

    /**
     * 获取汽车电量
     * @return 信息
     */
    String getPower();

    /**
     * 设置汽车电量
     * @param power 电量值
     * @return 信息
     */
    String setPower(int power);

    /**
     * 设置汽车里程
     * @param mileage 里程值
     * @return 信息
     */
    String setMileage(int mileage);

    /**
     * 设置汽车灯光
     * @param light 汽车灯光
     * @return 信息
     */
    String setLight(int light);

    /**
     * 设置汽车挡位
     * @param gear 汽车挡位
     * @return 信息
     */
    String setGear(int gear);
}
