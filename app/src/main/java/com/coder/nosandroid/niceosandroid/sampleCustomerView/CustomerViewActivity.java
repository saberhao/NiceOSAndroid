package com.coder.nosandroid.niceosandroid.sampleCustomerView;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.coder.nosandroid.niceosandroid.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);
        setTitle("自定义View");


    }

}
