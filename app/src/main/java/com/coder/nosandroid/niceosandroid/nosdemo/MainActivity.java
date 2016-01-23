package com.coder.nosandroid.niceosandroid.nosdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.coder.nosandroid.niceosandroid.R;
import com.coder.nosandroid.niceosandroid.hellochart.HelloChartActivity;
import com.coder.nosandroid.niceosandroid.mpandroidchart.MPChartActivity;
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

        objects.add(new ContentItem("PM25Demo","A demostration using Baidu API with Gson and volly."));
        objects.add(new ContentItem("SlipIndicatorDemo","A demostration of slip Indicator with CircleIndicator."));
        objects.add(new ContentItem("ObserviewDemo","A demostration of Observable Scrollview."));
        objects.add(new ContentItem("HelloChartsDemo","A demostration of Dynamic line chart with helloCharts."));
        objects.add(new ContentItem("MpChartsDemo","A demostration of Dynamic line chart with MPAndroidCharts."));
        objects.add(new ContentItem("PictureWallDemo","A demostration of WallPicture with LruCache and AsyncTask."));

        MainAdapter adapter = new MainAdapter(this,objects);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent;

        switch (position) {
            case 0 :
                intent = new Intent(this,PM25Activity.class);
                startActivity(intent);
                break;
            case 1 :
                intent = new Intent(this,SlipIndicatorActivity.class);
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
                intent = new Intent(this,PictureWallActivity.class);
                startActivity(intent);
                break;

        }

        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }
}
