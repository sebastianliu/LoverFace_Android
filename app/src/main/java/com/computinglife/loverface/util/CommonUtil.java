package com.computinglife.loverface.util;

import java.util.UUID;

/**
 * 通用工具类
 * Created by youngliu on 12/4/15.
 */
public class CommonUtil {

    /**
     * 获取uuid
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
