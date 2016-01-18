package com.coder.nosandroid.niceosandroid.hellochart;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.coder.nosandroid.niceosandroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by saberhao on 2016/1/18.
 */
public class HelloChartActivity extends AppCompatActivity {
    private int mCount = 0;
    private Handler mHandler;
    private LineChartView mChart;
    private TimerTask mTimerTask;
    private Timer timer = new Timer();
    List<PointValue> mPointValues = new ArrayList<PointValue>();
    List<AxisValue> mAxisValues = new ArrayList<AxisValue>();
    List<Line> lines = new ArrayList<Line>();
    LineChartData data = new LineChartData();
    Axis axisX = new Axis(); //X轴
    Axis axisY = new Axis();  //Y轴



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("HelloCharts");
        setContentView(R.layout.activity_hellochart);
        mChart = (LineChartView)findViewById(R.id.chart);
        ShowChart();



    }

    void ShowChart() {

        //For Chart Zoom
        mChart.setInteractive(true);
        mChart.setZoomType(ZoomType.HORIZONTAL);
        mChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        mChart.setVisibility(View.VISIBLE);

        //X-Y Axis Configure
        axisX.setMaxLabelChars(7);
        axisX.setHasTiltedLabels(true);
        axisX.setTextColor(Color.GRAY);
        axisX.setName("X-Axis");
        //Update Axis Values
        axisX.setValues(mAxisValues);
        // Set X-Axis in bottom & Y-Axis in Left
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                updateChart();
                super.handleMessage(msg);
            }
        };

        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(1);
            }
        };
        // set the timer to post the line chart
        timer.schedule(mTimerTask, 50, 100);

    }

    void updateChart() {

        if( mCount < 30) {
            mPointValues.add(new PointValue(mCount, new Random().nextInt(10)));
            mAxisValues.add(new AxisValue(mCount).setLabel(mCount+""));
            mCount++;
        } else {
            mHandler.removeMessages(1);
            timer.cancel();
            return;
        }
        Line line = new Line(mPointValues).setColor(Color.GREEN).setCubic(false);
        lines.add(line);
        axisX.setMaxLabelChars(7);
        data.setLines(lines);
        mChart.setLineChartData(data);


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
    protected void onDestroy() {
        timer.cancel();
        mHandler.removeMessages(1);
        super.onDestroy();

    }
}
