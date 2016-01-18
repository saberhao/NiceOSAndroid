package com.coder.nosandroid.niceosandroid.pm25volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import com.coder.nosandroid.niceosandroid.R;
import com.google.gson.Gson;

/**
 * Created by saberhao on 2016/1/18.
 */
public class PM25Activity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTv;
    private EditText mEt;
    String httpUrl = "http://apis.baidu.com/apistore/aqiservice/aqi";
    String httpArg = "city=";
    RequestQueue mQueue;
    AqiInfo aqiInfo;
    Gson gson;
    Map<String,String> mheader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pm25);
        setTitle("PM25Demo");

        Button mButton = (Button)findViewById(R.id.btn);
        mQueue = Volley.newRequestQueue(this);
        mButton.setOnClickListener(this);
        mEt = (EditText)findViewById(R.id.et);
        mTv = (TextView)findViewById(R.id.tv2);
        gson = new Gson();
        mheader = new HashMap<>();
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
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn: {
                String mCityName = mEt.getText().toString();
                if ("".equals(mCityName)) {
                    mTv.setText("Plz Input The City Name");
                } else {
                    Log.d("PM25","City name :" + mCityName);
                    GetPM25Value(mCityName);
                }
            }
            break;
            default:
                break;
        }
    }

    void GetPM25Value(String mCityName) {
        String url = httpUrl + "?" + httpArg + mCityName; //make the url
        mheader.put("apikey","1ac2319c59fda1338973f1dd5213f621");

        GsonRequest request = new GsonRequest<AqiInfo>(Request.Method.GET,url,new Response.Listener<AqiInfo>() {
            @Override
            public void onResponse(AqiInfo aqiInfo) {
                if(aqiInfo.errNum != 0) {
                    mTv.setText("Error happen, Error Num : " + aqiInfo.errNum);
                } else if (aqiInfo.retData != null){
                    String mTime = aqiInfo.retData.time.substring(11,19);
                    mTv.setText(aqiInfo.retData.toString()+"time : "+ mTime + ", Today");
                } else {
                    mTv.setText("Sorry , I do not support this City T.T");
                }
            }
        } ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTv.setText("Plz Check Your NetWork Connect");
            }
        },mheader,AqiInfo.class);

        mQueue.add(request);
    }


}
