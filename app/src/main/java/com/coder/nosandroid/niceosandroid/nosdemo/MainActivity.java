package com.coder.nosandroid.niceosandroid.nosdemo;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.coder.nosandroid.niceosandroid.sampleCustomerView.CustomerViewActivity;
import com.coder.nosandroid.niceosandroid.slipindexdemo.SlipIndicatorActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<ContentItem> objects = new ArrayList<ContentItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("NiceOSAndroidDemo");

        int id = 0;
        objects.add(new ContentItem(id++, CustomerViewActivity.class, "自定义View","仪表盘，圆形进度条，富文本, 贝塞尔曲线等自定义图形."));
        objects.add(new ContentItem(id++, HWTestDemo.class,"硬件检查","检查Sensor，MediaPlayer,Speaker, 蓝牙等硬件设备"));
        objects.add(new ContentItem(id++, ObserViewActivity.class, "ObserviewDemo","A demo of Observable Scrollview."));
        objects.add(new ContentItem(id++, HelloChartActivity.class, "HelloChartsDemo","A demo of Dynamic line chart with helloCharts."));
        objects.add(new ContentItem(id++, MPChartActivity.class, "MPChartsDemo","A demo of Dynamic line chart with MPAndroidCharts."));
        objects.add(new ContentItem(id++, MPChartMoveXActivity.class, "MPChartsMoveXDemo","A demo of Dynamic line and Move-X chart with MPAndroidCharts."));
        objects.add(new ContentItem(id++, DrawableActivity.class, "DrawableDemo","A demo of Material Design of ViewPager with Materialmenu and Palette."));
        //objects.add(new ContentItem(id++, PictureWallActivity.class, "PictureWallDemo","A demo of WallPicture with LruCache and AsyncTask."));
        objects.add(new ContentItem(id++, SlipIndicatorActivity.class, "SlipIndicatorDemo","A demo of slip Indicator with CircleIndicator."));
        //objects.add(new ContentItem(id++, PM25Activity.class, "PM25Demo","A demo using Baidu API with Gson and volly."));

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

        if (position >= objects.size()) {
            return;
        }
        Intent intent = new Intent(this, objects.get(position).cls);
        startActivity(intent);
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }
}
