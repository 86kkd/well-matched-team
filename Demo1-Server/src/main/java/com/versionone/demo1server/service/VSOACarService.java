package com.versionone.demo1server.service;

/**
 * VSOA协议控制汽车仿真中控平台
 */
public interface VSOACarService {

    String getSpeed();

    String setSpeed(int speed);

    String getPower();

    String setPower(int power);
}
