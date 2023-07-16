package com.versionone;

import com.versionone.demo1server.utils.Base64Util;
import com.versionone.demo1server.utils.FileUtil;

import java.io.File;

/**
 * 静态字符串类
 */
public class StaticString {
    public static final StringBuilder __ = new StringBuilder();

    static {
        File file = new File("F:\\myjava\\well-matched-team\\1.png");
        byte[] bytes = FileUtil.fileToByte(file);
        __.append(Base64Util.getImgFileToBase642(bytes)).append("#");
        __.append(Base64Util.getImgFileToBase642(bytes)).append("#");
        __.append(Base64Util.getImgFileToBase642(bytes)).append("#");
        __.append("True").append("#");
        __.append("False");
    }
}
