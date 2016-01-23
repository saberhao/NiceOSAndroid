package com.coder.nosandroid.niceosandroid.Utilities;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;

/**
 * Created by saberhao on 2016/1/23.
 */
public class BitmapUtil {
    private Bitmap getBitmapFromMemoryCache(Uri uri,Context context) {
        // TODO Auto-generated method stub
        ContentResolver cr = context.getContentResolver();
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);
            options.inSampleSize = 4;//图片宽高都为原来的二分之一，即图片为原来的四分之一
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);
            return bitmap;

        }catch (FileNotFoundException exp)
        {
            exp.printStackTrace();
            return null;
        }
    }


}
