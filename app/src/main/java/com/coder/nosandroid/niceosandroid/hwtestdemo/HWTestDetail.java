package com.coder.nosandroid.niceosandroid.hwtestdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.coder.nosandroid.niceosandroid.R;

import java.util.ArrayList;

/**
 * Created by saberhao on 2016/1/27.
 */
public class HWTestDetail extends AppCompatActivity{

    private TextView mItemTv;
    private CircleProgressBar mCirclePBar;
    private ArrayList<HWTestType> mItemTypeList;
    private ArrayList<HWTestType> mFailItemList;
    private ArrayList<HWTestType> mPassItemList;
    private int mTestItemMaxSize;
    private int mTestItemCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hwtestdetail);
        setTitle("HWTestDemo");
        mItemTv = (TextView)findViewById(R.id.result_item);
        initTestItem();
        mCirclePBar = (CircleProgressBar)findViewById(R.id.custom_progressBar);
        mCirclePBar.setColor(R.color.teal);
        mTestItemMaxSize = mItemTypeList.size();
        mCirclePBar.setMax(mTestItemMaxSize);

    }

    void initTestItem() {
        mItemTypeList.add(HWTestType.SOUND);
        mItemTypeList.add(HWTestType.VIBRATION);
        mItemTypeList.add(HWTestType.ACCELEROMETER);
        mItemTypeList.add(HWTestType.GYROSCOPE);
        mItemTypeList.add(HWTestType.PROXIMITY);
        mItemTypeList.add(HWTestType.MAGNETIC);
        mItemTypeList.add(HWTestType.WLAN);
        mItemTypeList.add(HWTestType.BT);
    }
    private enum HWTestType {
        SOUND,
        VIBRATION,
        ACCELEROMETER,
        GYROSCOPE,
        PROXIMITY,
        MAGNETIC,
        WLAN,
        BT,
        BATTAERY
    }

}
