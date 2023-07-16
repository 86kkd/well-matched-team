package com.versionone.demo1server.statics;

import com.acoinfo.vsoa.Client;
import com.acoinfo.vsoa.ClientOption;
import com.versionone.demo1server.mapper.CarMapper;
import com.versionone.demo1server.object.entity.Car;
import com.versionone.demo1server.object.vo.ResultObject;
import com.versionone.demo1server.service.VSOACarService;
import com.versionone.demo1server.utils.Queue;

import java.util.*;

import static com.versionone.demo1server.utils.CommonUtil.initCarDoors;

/**
 * 简易的内存数据库
 */
public class Redis {
    public  static String  SERVER_NAME   = "automobile_dash_board";
    public  static String  PASSWORD      = "123456";
    public  static String  POS_ADDRESS   = "192.168.116.130";
    public  static int     POS_PORT      = 3000;


    /**
     * 随机数变量
     */
    public static final Random RANDOM ;

    public static int currentSpeed = 0 ;

    public static final Queue<ResultObject> RESULT_OBJECT_QUEUE = new Queue<>();

    public static final Queue<String> STRING_QUEUE = new Queue<>();

    public static final Map<String,Boolean> CAR_DOORS;

    public static final Client CAR_CLIENT = new Client(new ClientOption(PASSWORD, 6000, 4000, 3, false));

    public static String FILE_NAME;

    public static  Boolean IS_CONNECTED = Boolean.FALSE;

    public static VSOACarService vsoaCarService;

    public static String MSG;

    private static  List<Car> cars;

    /**
     * 汽车信息表数据库读写接口对象
     */
    public static CarMapper carMapper;

    /**
     * 更新汽车信息方法
     * @param id 汽车id
     * @param car 汽车对象
     */
    public static void updateCarInfoById(Integer id,Car car){
        cars.set(id,car);
    }

    /**
     * 获取随机缓存汽车数据
     * @return 汽车对象
     */
    public static Car getRandomCar(){
        int i = RANDOM.nextInt(cars.size());
        return cars.get(i);
    }

    /**
     * 通过id获取汽车对象
     * @return 汽车对象
     */
    public static Car getCarById(Integer id){
        return cars.get(id - 1);
    }

    /**
     * 设置汽车信息表
     * @param cars_ 汽车信息表
     */
    public static void setCars(List<Car> cars_){
        cars = cars_;
    }

    /*
     *汽车信息表初始化
     */
    static {
        cars = new LinkedList<>();
        CAR_DOORS = new TreeMap<>();
        RANDOM = new Random();
        initCarDoors(CAR_DOORS);
    }
}
