package com.coder.nosandroid.niceosandroid.picturewall;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.coder.nosandroid.niceosandroid.R;
import com.coder.nosandroid.niceosandroid.Utilities.ImageDataSet;
import com.coder.nosandroid.niceosandroid.Utilities.ImgInfo;
import com.coder.nosandroid.niceosandroid.Utilities.LocalImgSet;

import java.io.FileNotFoundException;

/**
 * Created by saberhao on 2016/1/23.
 */
public class PictureDetailActivity extends AppCompatActivity{
    private int position;
    private ImageDataSet<ImgInfo> imginfos;
    private ImgInfo imginfo;
    private ZoomImageView zoomImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoompicturedetail);
        zoomImageView = (ZoomImageView)findViewById(R.id.picture_detail);
        imginfos = LocalImgSet.getImageInfos(this);
        Intent intent = getIntent();
        position = intent.getIntExtra("Position",-1);
        if( position != -1) {
            imginfo = imginfos.getItemAt(position);
            Uri uri = imginfo.getImageUri();
            Bitmap bitmap = getBitmapFromMemoryCache(uri,this);
            zoomImageView.setImageBitmap(bitmap);
        }
//        else {
//            zoomImageView.setImageResource(R.drawable.empty_photo);
//        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Bitmap getBitmapFromMemoryCache(Uri uri,Context context) {
        // TODO Auto-generated method stub
        ContentResolver cr = context.getContentResolver();
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);
            options.inSampleSize = 2;//图片宽高都为原来的二分之一，即图片为原来的四分之一
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
