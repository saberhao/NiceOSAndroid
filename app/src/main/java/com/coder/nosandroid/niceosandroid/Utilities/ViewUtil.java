package com.coder.nosandroid.niceosandroid.Utilities;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

import com.coder.nosandroid.niceosandroid.R;

public class ViewUtil {
    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }


    public static Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(Utils.getApplicationContext().getResources(),
                R.drawable.avatar_saberhao, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(Utils.getApplicationContext().getResources(),
                R.drawable.avatar_saberhao, options);
    }

    public static float getZForCamera() {
        return -4 * Resources.getSystem().getDisplayMetrics().density;
    }
}
