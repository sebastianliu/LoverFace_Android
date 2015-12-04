package com.computinglife.loverface.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.computinglife.loverface.Global.Global;
import com.computinglife.loverface.Global.InterfaceUrlDefine;
import com.computinglife.loverface.R;
import com.computinglife.loverface.activity.MainActivity;
import com.computinglife.loverface.base.net.RequestClient;
import com.computinglife.loverface.util.CommonUtil;
import com.computinglife.loverface.util.DataTools;
import com.computinglife.loverface.util.ImageUtil;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by youngliu on 11/25/15.
 */
public class TabFragment extends Fragment {
    private int index;
    private Activity context;
    private Resources resources;
    private LayoutInflater inflater;
    private Button buttonUpload;
    private ImageView imageViewPage0;
    private static final int REQUEST_CODE_PICK_PHOTO_FROM_CAMERA = 0;
    private static final int REQUEST_CODE_PICK_PICTURE = 1;
    private String mPicturePathPage0;
    private String token;
    private ProgressDialog progress;
    private String keyPage0;
    private Double upload_percent_text;
    private MyHandler handler = new MyHandler();
    private Message message = null;

    public TabFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        resources = getResources();
        inflater = LayoutInflater.from(context);
        getUploadToken();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            index = getArguments().getInt("index");
        }
        View view = null;
        Log.i("pageindex>>>>>", index + "");
        switch (index) {
            case MainActivity.PAGE0:
                //测颜界面
                view = inflater.inflate(R.layout.main_fragment_page1, null);
                imageViewPage0 = (ImageView) view.findViewById(R.id.imageView_fortestface);
                //浏览或拍照按钮
                buttonUpload = (Button) view.findViewById(R.id.button_testface);
                progress = new ProgressDialog(context);
                buttonUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopupWindow();
                    }
                });

                break;
            case MainActivity.PAGE1:
                //夫妻相界面

                break;
            case MainActivity.PAGE2:
                //排行榜界面

                break;
            case MainActivity.PAGE3:
                //我

                break;
            default:

                break;
        }

        return view;
    }


    public void showPopupWindow() {
        View popupwindow = inflater.inflate(R.layout.screen_layout_camera_layout_contents, null);
        final PopupWindow mPop = new PopupWindow(popupwindow, Global.SCREEN_WIDTH * 7 / 8, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button screen_layout_camera_layout_button_gallery = (Button) popupwindow
                .findViewById(R.id.screen_layout_camera_layout_button_gallery);
        Button screen_layout_camera_layout_button_camera = (Button) popupwindow
                .findViewById(R.id.screen_layout_camera_layout_button_camera);
        mPop.setFocusable(true);
        mPop.setOutsideTouchable(true);
        mPop.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.5f);
        mPop.setAnimationStyle(R.style.mypopwindow_anim_style);
        mPop.showAtLocation(buttonUpload, Gravity.BOTTOM, 0, buttonUpload.getHeight() + DataTools.px2dip(context, 100));
        mPop.setOnDismissListener(new poponDismissListener());

        screen_layout_camera_layout_button_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPop.dismiss();
                File newfile = new File(Global.UPLOAD_USER_PHOTO_TEMP_FILE_PATH);// tmp file
                try {
                    newfile.createNewFile();
                } catch (IOException e) {

                }

                Uri outputFileUri = Uri.fromFile(newfile);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                startActivityForResult(cameraIntent, REQUEST_CODE_PICK_PHOTO_FROM_CAMERA);

            }
        });

        screen_layout_camera_layout_button_gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPop.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_PICK_PICTURE);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (index) {
            case MainActivity.PAGE0:
                //界面一回调
                if (requestCode == REQUEST_CODE_PICK_PICTURE && resultCode == Activity.RESULT_OK && null != data) {
                    //相册
                    Uri selectedImage = data.getData();
                    String[] filePathColumns = new String[]{MediaStore.Images.Media.DATA};
                    Cursor c = context.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                    if (c == null) {
                        mPicturePathPage0 = selectedImage.getPath(); // 有些手机会为空 需要判断下
                    } else {
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePathColumns[0]);
                        mPicturePathPage0 = c.getString(columnIndex);
                        c.close();
                    }
                    Log.i("Page0回传的照片路径", mPicturePathPage0);
                    Bitmap bitmap = ImageUtil.decodeSampledBitmapFromResource(mPicturePathPage0, 1000, 1000);
                    imageViewPage0.setImageBitmap(bitmap);
                    showProgress(progress, "正在检测，请稍后……");
                    //upload photo


                    String key = CommonUtil.getUUID();
                    uploadPictrue(mPicturePathPage0, key);
                    //把图片路径传给后台，让后台经过计算后返回给移动端


                } else if (requestCode == REQUEST_CODE_PICK_PHOTO_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
                    //拍照
                    mPicturePathPage0 = Global.UPLOAD_USER_PHOTO_TEMP_FILE_PATH;
                    Log.i("Page0回传照片路径", Global.UPLOAD_USER_PHOTO_TEMP_FILE_PATH);
                    Bitmap bitmap = ImageUtil.decodeSampledBitmapFromResource(mPicturePathPage0, 700, 700);
                    imageViewPage0.setImageBitmap(bitmap);
                    showProgress(progress, "正在检测，请稍后……");
                    //upload photo


                }
                break;
            case MainActivity.PAGE1:
                //夫妻相

                break;
            default:
                break;
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        context.getWindow().setAttributes(lp);
    }

    /**
     * 添加点击拍照按钮时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    /**
     * 请求服务器上传照片token
     */
    private void getUploadToken() {
        Log.e(">>>>>", "token");
        RequestParams params = new RequestParams();
        RequestClient.get(context, InterfaceUrlDefine.GETUPLOADPICTOKEN, params,
                new TextHttpResponseHandler() {

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,
                                          Throwable throwable) {
                        Log.e(">>>>", "faile");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {

                        Log.e("上传的token", responseString);

                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            token = jsonObject.getString("token");
                            Log.e("uploadtoken", token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 显示进度提示
     *
     * @param progress
     */
    public void showProgress(ProgressDialog progress, String message) {
        progress.setMessage(message);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(false);
        progress.setProgress(100);
        progress.show();
    }

    /**
     * 上传图片
     *
     * @param filePath
     * @param key
     */
    private void uploadPictrue(String filePath, String key) {
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(filePath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {

                Log.i("qiniu", info.toString());
                if (info.isOK()) {
                    //上传结束，发消息
                    message = new Message();
                    message.what = 2;
                    keyPage0 = key;
                    handler.sendMessage(message);

                }
            }

        }, new UploadOptions(null, null, false, new UpProgressHandler() {
            public void progress(String key, double percent) {
                Log.i("qiniu", percent + "");
                if (percent <= 0.9 && percent > 0) {
                    upload_percent_text = percent;
                    message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }

            }
        }, null));

    }

    class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("attention", upload_percent_text.toString());
                    upload_percent_text *= 100;
                    progress.setProgress(upload_percent_text.intValue());
                    break;
                case 2:
                    Log.e(">>>>>>>>>>>>", "可以进行下一步请求");
                    progress.dismiss();
                    switch (index) {
                        case MainActivity.PAGE0:
                            //测颜界面,回传key，请求其测试结果


                            break;
                        default:
                            break;
                    }


                    break;
                default:

                    break;
            }

            super.handleMessage(msg);
        }

    }


}
