package com.computinglife.loverface.util;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * 数据格式转换工具类
 * Created by youngliu on 12/1/15.
 */
public class DataTools {
    /**
     * dip转为 px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale);
    }

    /**
     * px 转为 dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static float toPx(Context context, float pxValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue, context.getResources().getDisplayMetrics());
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 数字转换，超过4位数的,就用万,小数点后留1位，9位数以后用亿
     */
    public static String getNum(int value) {
        if (value < 10000) {
            return String.valueOf(value);
        } else if (value < 100000000) {
            int v = value / 10000;
            return v + "." + (value - v * 10000) / 1000 + "万";
        } else {
            int v = value / 100000000;
            return v + "." + (value - v * 100000000) / 10000000 + "亿";
        }
    }

    public static void setColorForStirngId(TextView view, int color, String str, int start, int end, int textSize) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        if (0 != textSize) {
            style.setSpan(new AbsoluteSizeSpan(textSize), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        view.setText(style);
    }

}
