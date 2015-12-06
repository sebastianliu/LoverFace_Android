package com.computinglife.loverface.util;

/**
 * Created by youngliu on 12/6/15.
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "null".equals(str)) {
            return true;
        }
        return false;
    }
}
