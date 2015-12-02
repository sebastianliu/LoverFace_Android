package com.computinglife.loverface.Global;

/**
 * Created by youngliu on 11/25/15.
 * note:记录全局信息的常量
 */
public class Global {
    // 网络连接超时时长
    public static final int TIMEOUT_CONNECTION = 10 * 1000;
    public static final int TIMEOUT_SOCKET = 10 * 1000;

    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String ENCODING_GZIP = "gzip";

    // SD卡文件根目录
    public static final String SHAKING_BASE_DIR = "LoverFace";

    // 缓存目录
    public static final String CACHE_FILE_DIR = "caches";

    // 照片上传临时路径
    public static String UPLOAD_USER_PHOTO_TEMP_FILE_PATH;// 未处理的jpg
    public static String UPLOAD_USER_PHOTO_CLIPED_FILE_PATH;// 已处理待上传的jpg

    public static String UPLOAD_USER_PHOTO_LOCAL_PATH;// 用户本地头像
    public static String UPLOAD_USER_COVER_LOCAL_PATH;// 用户本地封面

    public static int DEFAULT_MEM_CACHE_SIZE = 1024 * 1024 * 1;
    public static String IMAGE_TEMP_DIR;
    public static String CACHE_DIR;

    public static int SCREEN_WIDTH = 480;
    public static int SCREEN_HEIGHT = 800;

}
