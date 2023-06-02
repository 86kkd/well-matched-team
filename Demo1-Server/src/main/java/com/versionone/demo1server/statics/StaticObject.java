package com.versionone.demo1server.statics;

import java.util.Random;

/**
 * 静态对象类
 */
public class StaticObject {

    static {
        RANDOM = new Random();
    }

    /**
     * 随机数变量
     */
    public static final Random RANDOM ;

}
