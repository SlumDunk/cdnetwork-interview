package com.cdn.utils;

/**
 * @Author: zerongliu
 * @Date: 4/7/19 16:41
 * @Description: a class for operating String
 */
public class StringUtil {
    /**
     * assert whether the input string is null or empty
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
