package com.coder.nosandroid.niceosandroid.Utilities;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

    public static Bitmap GetPurColorIV(int colorId){
        int w = 20;
        int h = 20;
        Bitmap distBmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(distBmp);
        canvas.drawColor(colorId);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Rect rect = new Rect(0,0,w,h);
        canvas.drawBitmap(distBmp, null, rect, paint);
        return distBmp;
    }

    @SuppressWarnings("deprecation")
    public static Drawable BitmapToDrawable(Bitmap bitmap) {
        Drawable BDrawable = new BitmapDrawable(bitmap);
        return BDrawable;
    }
}
