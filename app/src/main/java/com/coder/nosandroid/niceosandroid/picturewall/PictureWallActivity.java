package com.coder.nosandroid.niceosandroid.picturewall;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.coder.nosandroid.niceosandroid.R;
import com.coder.nosandroid.niceosandroid.Utilities.ImageDataSet;
import com.coder.nosandroid.niceosandroid.Utilities.ImgInfo;
import com.coder.nosandroid.niceosandroid.Utilities.LocalImgSet;

/**
 * Created by saberhao on 2016/1/23.
 */
public class PictureWallActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

        private GridView mImgWall;
        private ImgWallAdapter adapter;
        private ImageDataSet<ImgInfo> imginfos = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_picturewall);
            setTitle("PictureWallDemo");
            imginfos = LocalImgSet.getImageInfos(this);
            mImgWall = (GridView) findViewById(R.id.photo_wall);
            adapter = new ImgWallAdapter(this,imginfos,mImgWall);
            mImgWall.setAdapter(adapter);
            mImgWall.setOnItemClickListener(this);
        }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(PictureWallActivity.this,PictureDetailActivity.class);
        intent.putExtra("Position",position);
        startActivity(intent);
    }
}
