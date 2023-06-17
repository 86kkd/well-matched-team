package com.versionone.demo1server.controller;

import com.versionone.demo1server.service.VSOACarService;
import com.versionone.demo1server.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VSOACarController {

    @Autowired
    private VSOACarService service;

    /**
     * 设置汽车速度接口
     * @param speed 速度值
     * @return 信息
     */
    @RequestMapping(value = "/v_setSpeed" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> setSpeed(@RequestParam("value")int speed){
        try {
            return CommonResult.success(service.setSpeed(speed));
        } catch (Exception e) {
            return CommonResult.failed("未知错误");
        }
    }

    /**
     * 获取汽车速度接口
     * @return 信息
     */
    @RequestMapping(value = "/v_getSpeed" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> getSpeed(){
        try {
            return CommonResult.success(service.getSpeed());
        } catch (Exception e) {
            return CommonResult.failed("未知错误");
        }
    }

    /**
     * 设置汽车电量接口
     * @param power 电量值
     * @return 信息
     */
    @RequestMapping(value = "/v_setPower" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> setPower(@RequestParam("value")int power){
        try {
            return CommonResult.success(service.setPower(power));
        } catch (Exception e) {
            return CommonResult.failed("未知错误");
        }
    }

    /**
     * 设置汽车里程接口
     * @param mileage 里程值
     * @return 信息
     */
    @RequestMapping(value = "/v_setMileage" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> setMileage(@RequestParam("value")int mileage){
        try {
            return CommonResult.success(service.setMileage(mileage));
        } catch (Exception e) {
            return CommonResult.failed("未知错误");
        }
    }



}
