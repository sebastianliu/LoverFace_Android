package com.computinglife.loverface;

import android.app.Activity;
import android.app.Application;
import android.content.res.Resources;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import com.computinglife.loverface.Global.Global;
import com.computinglife.loverface.util.DeviceInfoUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by youngliu on 12/1/15.
 */
public class LoverFaceApplication extends Application {

    private static LoverFaceApplication instance;
    public static boolean isDebug = true;// 设置打印日志 ，为false的时候为关闭

    /**
     * 用户设置的preference文件名：{@value}
     */
    public final static String SETTINGS = "settings";

    // sd卡根目录
    private String sdcard_base_path;

    public static LoverFaceApplication getInstance() {
        if (instance == null) {

            instance = new LoverFaceApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(">>>>>>>>>>>>>>", "panic");
        instance = this;
        initBaseDir();
        //设备宽高初始化
        Resources re = getResources();
        DisplayMetrics metrics = re.getDisplayMetrics();
        int widths = metrics.widthPixels;
        int heights = metrics.heightPixels;
        Global.SCREEN_WIDTH = widths;
        Global.SCREEN_HEIGHT = heights;
    }

    public void initBaseDir() {
        long availableSDCardSpace = DeviceInfoUtil.getExternalStorageSpace();// 获取SD卡可用空间
        if (availableSDCardSpace != -1L) {// 如果存在SD卡
            sdcard_base_path = Environment.getExternalStorageDirectory() + File.separator + Global.SHAKING_BASE_DIR;
        } else if (DeviceInfoUtil.getInternalStorageSpace() != -1L) {
            sdcard_base_path = getFilesDir().getPath() + File.separator + Global.SHAKING_BASE_DIR;
        } else {// sd卡不存在
            // 没有可写入位置
        }

        // 图片缓存目录
        Global.IMAGE_TEMP_DIR = sdcard_base_path + File.separator + "temp";

        // 照片上传缓存目录
        String photoUploadCacheDire = Global.CACHE_DIR = sdcard_base_path + File.separator + Global.CACHE_FILE_DIR;

        // 初始化根目录
        File basePath = new File(sdcard_base_path);
        if (!basePath.exists()) {
            basePath.mkdir();
        }

        // 初始化照片上传缓存目录
        File cachePath = new File(photoUploadCacheDire);
        if (!cachePath.exists()) {
            cachePath.mkdir();
        }

        Global.UPLOAD_USER_PHOTO_TEMP_FILE_PATH = cachePath + File.separator + "unhandled.jpg";
        Global.UPLOAD_USER_PHOTO_CLIPED_FILE_PATH = cachePath + File.separator + "handled.jpg";
        Global.UPLOAD_USER_PHOTO_LOCAL_PATH = cachePath + File.separator + "userphoto.jpg";
        Global.UPLOAD_USER_COVER_LOCAL_PATH = cachePath + File.separator + "usercover.jpg";
    }

    private ArrayList<Activity> activityList = new ArrayList<Activity>();

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void logOut() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    // 遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}
