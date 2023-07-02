package com.versionone.demo1server.service;

/**
 * VSOA协议控制汽车仿真中控平台
 */
public interface VSOACarService {




    /**
     * 设置汽车车门
     * @param doors 车门值
     * @return 信息
     */
    String setDoors(int doors);

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

    /**
     * 设置汽车转向灯
     * @param turnlight 转向灯值
     * @return 信息
     */
    String setTurnlight(int turnlight);

    /**
     * 设置汽车安全气囊
     * @param airbag 安全气囊值
     * @return 信息
     */
    String setAirbag(int airbag);

    /**
     * 设置汽车刹车状态灯
     * @param brake 刹车状态
     * @return 信息
     */
    String setBrake(int brake);

    /**
     * 设置汽车安全带灯
     * @param seatbelt 安全带状态
     * @return 信息
     */
    String setSeatbelt(int seatbelt);

    /**
     * 设置汽车ABS状态灯
     * @param abs ABS状态
     * @return 信息
     */
    String setAbsStatus(int abs);

    /**
     * 设置汽车左前胎压
     * @param lf 左前胎压值
     * @return 信息
     */
    String setLeftFrontTire(double lf);

    /**
     * 设置汽车右前胎压
     * @param rf 右前胎压值
     * @return 信息
     */
    String setRightFrontTire(double rf);

    /**
     * 设置汽车左后胎压
     * @param lr 左后胎压值
     * @return 信息
     */
    String setLeftRearTire(double lr);

    /**
     * 设置汽车右后胎压
     * @param rr 右后胎压值
     * @return 信息
     */
    String setRightRearTire(double rr);
}
