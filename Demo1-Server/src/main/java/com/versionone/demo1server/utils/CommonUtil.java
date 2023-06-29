package com.versionone.demo1server.utils;

import com.versionone.demo1server.statics.Redis;

import java.util.HashMap;
import java.util.Map;

/**
 * 常用工具类
 */
public class CommonUtil {

    private static final Map<Integer,String> D_T = new HashMap<>();

    /**
     * 初始化车门信息
     * @param doors 车门对象
     */
    public static void initCarDoors(Map<String,Boolean> doors){
        doors.put("左前门",false);
        doors.put("右前门",false);
        doors.put("左后门",false);
        doors.put("右后门",false);
    }

    /**
     * 打印车门信息
     * @param door 车门值
     */
    public static void printDoors(Integer door){
        int[] doors = decToBinArr(door);
        Map<String,Boolean> D = Redis.CAR_DOORS;
        for (int i = 0; i < doors.length; i++) {
            D.put(D_T.get(i), doors[i] == 1 );
        }
        System.out.println(D);
    }

    /**
     * 十进制数转二进制数组(四位)
     * @param dec 十进制
     * @return 二进制数组
     */
    public static int[] decToBinArr(Integer dec){
        int[] bin = new int[4];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 4; i++) {
            stack.push( dec % 2 );
            dec = dec / 2;
        }
        for (int i = bin.length - 1; i >= 0; i--) {
            bin[i] = stack.pop();
        }
        return bin;
    }

    public static void initDT(){
        D_T.put(3,"左前门");
        D_T.put(2,"右前门");
        D_T.put(1,"左后门");
        D_T.put(0,"右后门");
    }

    static {
        initDT();
    }
}
