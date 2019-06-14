package com.sxkl.fastrigger.commoner.utils;

import java.util.UUID;

/**
 * 系统公用模块 uuid工具类
 * @author wy
 * @date 2019-06-10 11:14:52
 */
public class UUIDUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
