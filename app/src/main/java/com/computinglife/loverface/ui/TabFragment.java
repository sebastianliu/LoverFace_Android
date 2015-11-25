package com.computinglife.loverface.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.computinglife.loverface.R;
import com.computinglife.loverface.activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by youngliu on 11/25/15.
 */
public class TabFragment extends Fragment {
    private int index;
    private Activity context;
    private Resources resources;
    private LayoutInflater inflater;


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
                view = inflater.inflate(R.layout.main_fragment_page1, null);
                GridView gridview = (GridView) view.findViewById(R.id.GridView);
                gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
                ArrayList<HashMap<String, Object>> meumList = new ArrayList<>();
                int[] icons = {R.mipmap.test_face, R.mipmap.lover_test, R.mipmap.uglily};
                String[] strings = {resources.getString(R.string.page1_testface), resources.getString(R.string.page1_loversface), resources.getString(R.string.page1_uglily)};
                for (int i = 0; i < icons.length; i++) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("ItemImage", icons[i]);
                    map.put("ItemText", strings[i]);
                    meumList.add(map);
                }
                SimpleAdapter saItem = new SimpleAdapter(context, meumList, R.layout.page1_item, new String[]{"ItemImage", "ItemText"}, new int[]{R.id.ItemImage, R.id.ItemText});

                //添加Item到网格中
                gridview.setAdapter(saItem);
                //添加点击事件
                gridview.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                int index = arg2 + 1;//id是从0开始的，所以需要+1
                                Toast.makeText(context, "你按下了选项：" + index, Toast.LENGTH_SHORT).show();
                                //Toast用于向用户显示一些帮助/提示
                                ImageView itemImg = (ImageView) arg1.findViewById(R.id.ItemImage);
                                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
                                itemImg.startAnimation(animation);
                                //根据不同的index跳转到相应的页面
                                switch (arg2) {
                                    case 0:
                                        //选项一:测试颜值
//                                        Intent intent = new Intent(context, TestFaceActivity.class);
//                                        Bundle bundle = new Bundle();
//                                        bundle.putString("test", "fortest");
//                                        context.startActivity(intent);
                                        break;
                                    case 1:

                                        break;
                                    case 2:

                                        break;
                                    default:

                                        break;
                                }

                            }
                        }
                );

        }

        return view;
    }
}
