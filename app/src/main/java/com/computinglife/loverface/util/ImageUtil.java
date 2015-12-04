package com.computinglife.loverface.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

/**
 * 图片工具类
 * Created by youngliu on 12/3/15.
 */
public class ImageUtil {
    public static Bitmap decodeSampledBitmapFromResource(String picturePath, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(picturePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

//    private static Bitmap drawFaceRectanglesOnBitmap(Bitmap originalBitmap, List<Object> objects) {
//        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
//        Canvas canvas = new Canvas(bitmap);
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(Color.RED);
//        int stokeWidth = 2;
//        paint.setStrokeWidth(stokeWidth);
//        if (objects != null) {
//            for (Object face : objects) {
//
//                canvas.drawRect(
//                        face.left,
//                        face.top,
//                        face.left + face.width,
//                        face.top + face.height,
//                        paint);
//            }
//        }
//        return bitmap;
//    }

}
