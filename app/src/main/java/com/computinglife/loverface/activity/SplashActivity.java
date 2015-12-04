package com.computinglife.loverface.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.computinglife.loverface.Global.Global;
import com.computinglife.loverface.R;
import com.computinglife.loverface.util.AsyncTaskUtil;
import com.computinglife.loverface.util.DeviceInfoUtil;

import java.io.File;

/**
 * 应用启动闪屏界面
 *
 * @author youngliu
 */
public class SplashActivity extends Activity {

    private Handler mHandler;
    /**
     * 启动完成
     */
    private final static int LUANCH_DONE = 0;

    /**
     * 用户设置的preference文件名：{@value}
     */
    public final static String SETTINGS = "settings";

    // sd卡根目录
    private String sdcard_base_path;

    private Resources resources;
    private Activity context;
    private String index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getIntent().getStringExtra("index");
        context = this;
        resources = getResources();
        setContentView(R.layout.activity_splash);
        mHandler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case LUANCH_DONE: {
                        Intent intent = null;
                        intent = new Intent(context, MainActivity.class);
                        intent.putExtra("index", index);
                        startActivity(intent);
                        finish();

                    }
                    break;
                }
            }
        };
        init();
    }

    public void init() {

        initBaseDir();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int widths = metrics.widthPixels;
        int heights = metrics.heightPixels;
        Global.SCREEN_WIDTH = widths;
        Global.SCREEN_HEIGHT = heights;
        findViewAndSetListener();
        launch();
    }

    public void findViewAndSetListener(){
        ImageView logo = (ImageView) findViewById(R.id.logo);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, Global.SCREEN_HEIGHT / 3, 0, 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        logo.setLayoutParams(layoutParams);
        logo.setBackgroundResource(R.mipmap.test_face);
    }

    public void initBaseDir() {
        long availableSDCardSpace = DeviceInfoUtil.getExternalStorageSpace();// 获取SD卡可用空间
        if (availableSDCardSpace != -1L) {// 如果存在SD卡
            sdcard_base_path = Environment.getExternalStorageDirectory() + File.separator + Global.SHAKING_BASE_DIR;
        } else if (DeviceInfoUtil.getInternalStorageSpace() != -1L) {
            sdcard_base_path = getFilesDir().getPath() + File.separator + Global.SHAKING_BASE_DIR;
        } else {
            // sd卡不存在
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

    public void launch() {
        AsyncTaskUtil.addTask(new Runnable() {

            @Override
            public void run() {
                long time = System.currentTimeMillis();

                //闪屏３秒
                time = 3000 - (System.currentTimeMillis() - time);
                if (time > 0) {
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                    }
                }

                mHandler.sendEmptyMessage(LUANCH_DONE);
            }
        });
    }
}
