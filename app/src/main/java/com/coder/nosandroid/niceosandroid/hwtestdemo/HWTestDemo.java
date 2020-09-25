package com.coder.nosandroid.niceosandroid.hwtestdemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.coder.nosandroid.niceosandroid.R;

/**
 * Created by saberhao on 2016/1/26.
 */
public class HWTestDemo extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hwtestdemo);
        setTitle("HWTestDemo");
        Button mStartButton = (Button) findViewById(R.id.test_start);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HWTestDemo.this,HWTestDetail.class);
                startActivity(intent);
            }
        });
    }

}
