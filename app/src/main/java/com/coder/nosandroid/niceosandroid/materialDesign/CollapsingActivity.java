package com.coder.nosandroid.niceosandroid.materialDesign;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.nosandroid.niceosandroid.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

/**
 * 可展开的Activity
 */
public class CollapsingActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing);

        String url = getIntent().getStringExtra("URL");
        String name = getIntent().getStringExtra("NAME");

        final Toolbar toolbar = findViewById(R.id.tb_amd_toolbar);
        if (toolbar != null) {
            toolbar.setTitle(name);
            setSupportActionBar(toolbar);
        }

        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle("折叠布局");
        //通过CollapsingToolbarLayout修改字体颜色
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);//设置收缩后Toolbar上字体的颜色

        AppBarLayout appBarLayout = findViewById(R.id.appBar);

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeEvent() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeEvent.State state, int verticalOffset) {
                if (toolbar == null) return;
                if (state == AppBarStateChangeEvent.State.COLLAPSED){
                    toolbar.setBackgroundColor(getResources().getColor(R.color.pink));
                }else{
                    toolbar.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });

        ImageView iv = findViewById(R.id.iv_movie_icon);
        iv.setImageResource(R.drawable.jdnian);

        TextView tv = findViewById(R.id.tv_content);
        tv.setText(R.string.jdbaike);
    }
}
