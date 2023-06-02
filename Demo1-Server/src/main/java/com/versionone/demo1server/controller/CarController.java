package com.versionone.demo1server.controller;

import com.versionone.demo1server.object.entity.Car;
import com.versionone.demo1server.service.CarService;
import com.versionone.demo1server.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class CarController {

    @Autowired
    private CarService service;

    /**
     * 获取随机车辆信息接口
     * @return 车辆信息对象
     */
    @RequestMapping(value = "/getRandomCarInfo" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Car> getRandomCarInfo(){
        try {
//            Random r = new Random();
            return CommonResult.success(service.getRandomCarInfo());
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.failed();
        }
    }

    /**
     * 刹车接口
     * @param time 时间
     * @return 汽车信息
     */
    @RequestMapping(value = "/brake" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Car> braking(@RequestParam("id")Integer id ,@RequestParam("time")Double time,@RequestParam("strength")Double strength){
        Car car = service.brake(id, time, strength);
        return car == null ? CommonResult.failed("数据异常") : CommonResult.success(car);
    }

    /**
     * 加速接口
     * @param id 汽车id
     * @param time 加速时间
     * @param strength 油门力度
     * @return 汽车信息
     */
    @RequestMapping(value = "/accelerate" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Car> accelerate(@RequestParam("id")Integer id ,@RequestParam("time")Double time,@RequestParam("strength")Double strength){
        Car car = service.accelerate(id, time, strength);
        return car == null ? CommonResult.failed("数据异常") : CommonResult.success(car);
    }

    /**
     * 挡位切换接口
     * @param id 汽车id
     * @param gear 挡位
     * @return 结果
     */
    @RequestMapping(value = "/shiftG" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Car> shiftGear(@RequestParam("id")Integer id ,@RequestParam("gear")Integer gear){
        Car car = service.shiftSwitching(id, gear);
        return car == null ? CommonResult.failed("数据异常") : CommonResult.success(car);
    }

    /**
     * 挡位灯光接口
     * @param id 汽车id
     * @param light 灯光
     * @return 结果
     */
    @RequestMapping(value = "/shiftL" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Car> shiftLight(@RequestParam("id")Integer id ,@RequestParam("light")Integer light){
        Car car = service.shiftLight(id,light);
        return car == null ? CommonResult.failed("数据异常") : CommonResult.success(car);
    }
}
