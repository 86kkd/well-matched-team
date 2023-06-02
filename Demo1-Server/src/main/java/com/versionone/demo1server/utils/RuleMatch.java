package com.versionone.demo1server.utils;

/**
 * 全局规则匹配公共工具类
 */
public class RuleMatch {

    /**
     * 挡位规则匹配
     * @param gear 挡位值
     * @return 是否合法
     */
    public static boolean gearMatching(Integer gear){
        switch (gear){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }

}
