package com.versionone.demo1server.utils;

import java.util.Map;

/**
 * 常用工具类
 */
public class CommonUtil {


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
}
