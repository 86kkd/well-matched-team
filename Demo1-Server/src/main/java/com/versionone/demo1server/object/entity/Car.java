package com.versionone.demo1server.object.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 车辆实体类
 */
@TableName("car")
public class Car implements Serializable {

    /**
     * 车辆id
     */
    @TableId
    private Integer id;

    /**
     * 速度（0-240）
     */
    @TableField("speed")
    private Double speed;

    /**
     * 电量（0-100）
     */
    @TableField("power")
    private Float power;

    /**
     * 挡位（ P = 0,R = 1,N = 2,D = 3,S = 4,L = 5)
     */
    @TableField("gear")
    private Integer gear;

    /**
     *     FOGLIGHT = 0x01,        // 雾灯
     *     HIGH_BEAM = 0x02,       // 远光灯
     *     DIPPED_LIGHTS = 0x04,   // 近光灯
     *     CLEARANCE_LAMP = 0x08   // 示廓灯
     */
    @TableField("light")
    private Integer light;

    /**
     *     FRONT   = 1,            // 1
     *     REAR    = 1 << 1,       // 2
     *     LEFT    = 1 << 2,       // 4
     *     RIGHT   = 1 << 3        // 8
     */
    @TableField("turn_light")
    private Integer turnLight;

    /**
     * 0000，0为关，1为开
     * 分别表示左前，右前，左后，右后的开关门状态的二进制值，
     * 例如0001是左前右前左后都关，右后开，十进制值是1
     */
    @TableField("door")
    private Integer door;

    /**
     * 0或1，表示刹车与否
     */
    @TableField("braking")
    private Integer braking;

    public Car() {
    }

    public Car(Integer id, Double speed, Float power, Integer gear, Integer light, Integer turn_light, Integer door, Integer braking) {
        this.id = id;
        this.speed = speed;
        this.power = power;
        this.gear = gear;
        this.light = light;
        this.turnLight = turn_light;
        this.door = door;
        this.braking = braking;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Float getPower() {
        return power;
    }

    public void setPower(Float power) {
        this.power = power;
    }

    public Integer getGear() {
        return gear;
    }

    public void setGear(Integer gear) {
        this.gear = gear;
    }

    public Integer getLight() {
        return light;
    }

    public void setLight(Integer light) {
        this.light = light;
    }

    public Integer getTurnLight() {
        return turnLight;
    }

    public void setTurnLight(Integer turnLight) {
        this.turnLight = turnLight;
    }

    public Integer getDoor() {
        return door;
    }

    public void setDoor(Integer door) {
        this.door = door;
    }

    public Integer getBraking() {
        return braking;
    }

    public void setBraking(Integer braking) {
        this.braking = braking;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                '}';
    }
}
