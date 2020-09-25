package com.coder.nosandroid.niceosandroid.hwtestdemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;

import com.coder.nosandroid.niceosandroid.R;

import java.util.ArrayList;

/**
 * Created by saberhao on 2016/2/2.
 */
public class HWTestResultList extends AppCompatActivity{

    private ArrayList<HWTestDetail.HWTestType> mFailItemList;
    private ArrayList<HWTestDetail.HWTestType> mPassItemList;
    private ArrayList<HWTestDetail.HWTestType> mAllItemList;
    private int mFailItemSize;
    private int mPassItemSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("HWTestDemo");
        setContentView(R.layout.activity_hwtestresultlist);
        mAllItemList = new ArrayList<HWTestDetail.HWTestType>();
        mPassItemList = (ArrayList<HWTestDetail.HWTestType>)getIntent().getSerializableExtra("PassItemList");
        mAllItemList.addAll(mPassItemList);
        mFailItemList = (ArrayList<HWTestDetail.HWTestType>)getIntent().getSerializableExtra("FailItemList");
        mAllItemList.addAll(mFailItemList);
        mPassItemSize = mPassItemList.size();
        ResultListAdapter adapter = new ResultListAdapter(this,mAllItemList,mPassItemSize);
        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(adapter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
