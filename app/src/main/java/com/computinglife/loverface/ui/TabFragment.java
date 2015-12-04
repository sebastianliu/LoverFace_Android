package com.computinglife.loverface.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
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
import com.computinglife.loverface.LoverFaceApplication;
import com.computinglife.loverface.R;
import com.computinglife.loverface.activity.MainActivity;
import com.computinglife.loverface.util.DataTools;

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
    private ImageView imageViewPage1;
    private static final int REQUEST_CODE_PICK_PHOTO_FROM_CAMERA = 0;
    private static final int REQUEST_CODE_PICK_PICTURE = 1;
    private String mPicturePath;

    public TabFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        resources = getResources();
        inflater = LayoutInflater.from(context);
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
                imageViewPage1 = (ImageView)view.findViewById(R.id.imageView_fortestface);
                //浏览或拍照按钮
                buttonUpload = (Button) view.findViewById(R.id.button_testface);
                buttonUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopupWindow();
                    }
                });

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
        if (requestCode == REQUEST_CODE_PICK_PICTURE && resultCode == Activity.RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumns = new String[] { MediaStore.Images.Media.DATA };
            Cursor c = context.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            if (c == null) {
                mPicturePath = selectedImage.getPath(); // 有些手机会为空 需要判断下
            }

            else {
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                mPicturePath = c.getString(columnIndex);
                c.close();
            }
            Log.i("回传的照片路径", mPicturePath);


            return;
        } else if (requestCode == REQUEST_CODE_PICK_PHOTO_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            Intent i = new Intent();
            i.putExtra("mPicturePath", Global.UPLOAD_USER_PHOTO_TEMP_FILE_PATH);
            Log.i("回传照片路径",Global.UPLOAD_USER_PHOTO_TEMP_FILE_PATH);

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
}
