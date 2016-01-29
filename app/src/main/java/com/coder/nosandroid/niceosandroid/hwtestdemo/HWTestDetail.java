package com.coder.nosandroid.niceosandroid.hwtestdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.coder.nosandroid.niceosandroid.R;
import com.coder.nosandroid.niceosandroid.Utilities.LogUtils;

import java.util.ArrayList;

/**
 * Created by saberhao on 2016/1/27.
 */
public class HWTestDetail extends AppCompatActivity{

    private TextView mTestResultTv;
    private TextView mTestItemTv;
    private CircleProgressBar mCirclePBar;
    private ArrayList<HWTestType> mItemTypeList;
    private ArrayList<HWTestType> mFailItemList;
    private ArrayList<HWTestType> mPassItemList;
    private int mTestItemMaxSize;
    private HWTestType mCurrentTest;
    private HWTestWorkerTask task;
    private int mTestItemCnt;
    private static final String TAG = "HWTestDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hwtestdetail);
        setTitle("HWTestDemo");
        mItemTypeList = new ArrayList<>();
        mFailItemList = new ArrayList<>();
        mPassItemList = new ArrayList<>();

        mTestResultTv = (TextView)findViewById(R.id.result_item);
        mTestItemTv =(TextView)findViewById(R.id.test_item);
        initTestItem();
        mCirclePBar = (CircleProgressBar)findViewById(R.id.custom_progressBar);
        mCirclePBar.setColor(R.color.teal);
        mTestItemMaxSize = mItemTypeList.size();
        mCirclePBar.setMax(mTestItemMaxSize);
        mCurrentTest = mItemTypeList.remove(0);
        task = new HWTestWorkerTask();
        task.execute(mCurrentTest);

    }

    public class HWTestWorkerTask extends AsyncTask<HWTestType,HWTestType,Boolean> {


        @Override
        protected Boolean doInBackground(HWTestType... params) {
            switch(params[0]) {
                case SOUND:
                    publishProgress(HWTestType.SOUND);
                    SoundCheck();
                    break;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(HWTestType... values) {
            super.onProgressUpdate(values);
            mTestResultTv.setText(R.string.ongoing_test);
            switch (values[0]) {
                case SOUND:
                    mTestItemTv.setText(R.string.item_sound);
            }
        }

        @Override
        protected void onPostExecute(Boolean isPass) {
            super.onPostExecute(isPass);
            mTestResultTv.setText((isPass ? R.string.test_pass : R.string.test_fail));
            if(isPass) {
                mPassItemList.add(mCurrentTest);
            } else {
                mFailItemList.add(mCurrentTest);
            }
            if(mItemTypeList.size() > 0) {
                mCurrentTest = mItemTypeList.remove(0);
//            HWTestWorkerTask task = new HWTestWorkerTask();
                task.execute(mCurrentTest);
            }
        }

    }

    Boolean SoundCheck() {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            LogUtils.e(TAG,"SoundCheck",e.getMessage());
        }
        return true;
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
