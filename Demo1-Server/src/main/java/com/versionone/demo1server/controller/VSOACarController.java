package com.versionone.demo1server.controller;

import com.versionone.demo1server.service.VSOACarService;
import com.versionone.demo1server.threads.VSOARandomDataThread;
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

    /**
     * 设置汽车挡位接口
     * @param gear 挡位值
     * @return 信息
     */
    @RequestMapping(value = "/v_setGear" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> setGear(@RequestParam("value")int gear){
        try {
            return CommonResult.success(service.setGear(gear));
        } catch (Exception e) {
            return CommonResult.failed("未知错误");
        }
    }

    /**
     * 设置汽车灯光接口
     * @param light 灯光值
     * @return 信息
     */
    @RequestMapping(value = "/v_setLight" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> setLight(@RequestParam("value")int light){
        try {
            return CommonResult.success(service.setLight(light));
        } catch (Exception e) {
            return CommonResult.failed("未知错误");
        }
    }

    /**
     * 设置汽车车门接口
     * @param doors 车门值
     * @return 信息
     */
    @RequestMapping(value = "/v_setDoors" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> setDoors(@RequestParam("value")int doors){
        try {
            return CommonResult.success(service.setDoors(doors));
        } catch (Exception e) {
            return CommonResult.failed("未知错误");
        }
    }

    /**
     * 平台随机数据设置接口
     * @return 结果信息
     */
    @RequestMapping(value = "/v_start" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> randomDate(){
        Thread thread = new VSOARandomDataThread(service);
        thread.start();
        return CommonResult.success("111");
    }

    /**
     * 设置汽车转向灯
     * @param light 转向灯值
     * @return 信息
     */
    @RequestMapping(value = "/v_setTurnlight" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> setTurnlight(@RequestParam("value")int light){
        try {
            return CommonResult.success(service.setTurnlight(light));
        } catch (Exception e) {
            return CommonResult.failed("未知错误");
        }
    }

    /**
     * 设置汽车安全气囊
     * @param airbag 安全气囊值
     * @return 信息
     */
    @RequestMapping(value = "/v_setAirbag" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> setAirbag(@RequestParam("value") int airbag){
        try {
            return CommonResult.success(service.setAirbag(airbag));
        } catch (Exception e) {
            return CommonResult.failed("未知错误");
        }
    }

    /**
     * 设置汽车刹车（紧急制动）
     * @param brake 刹车状态值
     * @return 信息
     */
    @RequestMapping(value = "/v_setBrake" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> setBrake(@RequestParam("value") int brake){
        try {
            return CommonResult.success(service.setBrake(brake));
        } catch (Exception e) {
            return CommonResult.failed("未知错误");
        }
    }

    /**
     * 设置汽车安全带指示灯接口
     * @param seatbelt 汽车安全带状态
     * @return 信息
     */
    @RequestMapping(value = "/v_setSeatbelt",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> setSeatbelt(@RequestParam("value") int seatbelt){
        try {
            return CommonResult.success(service.setSeatbelt(seatbelt));
        } catch (Exception e) {
            return CommonResult.failed("未知错误");
        }
    }

    /**
     * 设置汽车ABS指示灯接口
     * @param status ABS状态
     * @return 信息
     */
    @RequestMapping(value = "v_setAbsStatus" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> setAbsStatus(@RequestParam("value") int status){
        try {
            return CommonResult.success(service.setAbsStatus(status));
        } catch (Exception e) {
            return CommonResult.failed("未知错误");
        }
    }

    /**
     * 设置左前汽车胎压
     * @param lf 左前轮胎胎压值
     * @return 信息
     */
    public CommonResult<String> setLeftFrontTire(@RequestParam("value") double lf){
        try {
            return CommonResult.success(service.setLeftFrontTire(lf));
        } catch (Exception e) {
            return CommonResult.failed("未知错误");
        }
    }

}
