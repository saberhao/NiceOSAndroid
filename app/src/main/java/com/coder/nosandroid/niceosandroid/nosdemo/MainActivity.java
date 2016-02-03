package com.coder.nosandroid.niceosandroid.nosdemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.coder.nosandroid.niceosandroid.R;
import com.coder.nosandroid.niceosandroid.drawableLayoutdemo.DrawableActivity;
import com.coder.nosandroid.niceosandroid.hellochart.HelloChartActivity;
import com.coder.nosandroid.niceosandroid.hwtestdemo.HWTestDemo;
import com.coder.nosandroid.niceosandroid.mpandroidchart.MPChartActivity;
import com.coder.nosandroid.niceosandroid.mpandroidchart.MPChartMoveXActivity;
import com.coder.nosandroid.niceosandroid.observiewdemo.ObserViewActivity;
import com.coder.nosandroid.niceosandroid.picturewall.PictureWallActivity;
import com.coder.nosandroid.niceosandroid.pm25volley.PM25Activity;
import com.coder.nosandroid.niceosandroid.slipindexdemo.SlipIndicatorActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("NiceOSAndroidDemo");

        ArrayList<ContentItem> objects = new ArrayList<ContentItem>();

        objects.add(new ContentItem("HWTestDemo","A demostration of HW test with Sensor Manager，MediaPlayer,AsyncTask"));
        objects.add(new ContentItem("PM25Demo","A demostration using Baidu API with Gson and volly."));
        objects.add(new ContentItem("ObserviewDemo","A demostration of Observable Scrollview."));
        objects.add(new ContentItem("HelloChartsDemo","A demostration of Dynamic line chart with helloCharts."));
        objects.add(new ContentItem("MPChartsDemo","A demostration of Dynamic line chart with MPAndroidCharts."));
        objects.add(new ContentItem("MPChartsMoveXDemo","A demostration of Dynamic line and Move-X chart with MPAndroidCharts."));
        objects.add(new ContentItem("DrawableDemo","A demostration of Material Design of ViewPager with Materialmenu and Palette."));
        objects.add(new ContentItem("PictureWallDemo","A demostration of WallPicture with LruCache and AsyncTask."));
        objects.add(new ContentItem("SlipIndicatorDemo","A demostration of slip Indicator with CircleIndicator."));

        MainAdapter adapter = new MainAdapter(this,objects);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = null;

        switch (item.getItemId()) {
            case R.id.viewGithub:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/saberhao/NiceOSAndroid"));
                startActivity(i);
                break;
            case R.id.blog:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://blog.csdn.net/saberhao"));
                startActivity(i);
                break;
        }


        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent;

        switch (position) {
            case 0 :
                intent = new Intent(this,HWTestDemo.class);
                startActivity(intent);
                break;
            case 1 :
                intent = new Intent(this,PM25Activity.class);
                startActivity(intent);
                break;
            case 2 :
                intent = new Intent(this,ObserViewActivity.class);
                startActivity(intent);
                break;
            case 3 :
                intent = new Intent(this,HelloChartActivity.class);
                startActivity(intent);
                break;
            case 4 :
                intent = new Intent(this,MPChartActivity.class);
                startActivity(intent);
                break;
            case 5 :
                intent = new Intent(this, MPChartMoveXActivity.class);
                startActivity(intent);
                break;
            case 6 :
                intent = new Intent(this, DrawableActivity.class);
                startActivity(intent);
                break;
            case 7 :
                intent = new Intent(this,PictureWallActivity.class);
                startActivity(intent);
                break;
            case 8 :
                intent = new Intent(this,SlipIndicatorActivity.class);
                startActivity(intent);
                break;

        }

        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }
}
