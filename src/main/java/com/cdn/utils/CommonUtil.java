package com.cdn.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: zerongliu
 * @Date: 4/7/19 17:56
 * @Description:
 */
public class CommonUtil {
    /**
     * validate the addr whether it is a valid ip address
     *
     * @param addr
     * @return
     */
    public static boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        /**
         * regex to match ip address
         */
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(addr);

        boolean isValid = matcher.find();

        return isValid;
    }

    /**
     * validate the no. of cpu
     *
     * @param cpuID
     * @return
     */
    public static boolean isCPU(String cpuID) {
        Integer cpu = Integer.parseInt(cpuID);
        return cpu == 0 || cpu == 1;
    }
}
