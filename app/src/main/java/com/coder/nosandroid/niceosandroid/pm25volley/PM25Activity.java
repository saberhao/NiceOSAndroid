package com.coder.nosandroid.niceosandroid.pm25volley;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import com.coder.nosandroid.niceosandroid.R;
import com.coder.nosandroid.niceosandroid.Utilities.LogUtils;
import com.google.gson.Gson;

/**
 * Created by saberhao on 2016/1/18.
 */
public class PM25Activity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "PM25Activity";
    private TextView mTv;
    private AutoCompleteTextView mEt;
    String httpUrl = "http://apis.baidu.com/apistore/aqiservice/aqi";
    String httpArg = "city=";
    String mCleanHistory = "Clear History,";
    RequestQueue mQueue;
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
        Button mClrButton = (Button)findViewById(R.id.clearhistory);
        mQueue = Volley.newRequestQueue(this);
        mButton.setOnClickListener(this);
        mClrButton.setOnClickListener(this);
        mClrButton.setVisibility(View.GONE); //just for debug
        mEt = (AutoCompleteTextView)findViewById(R.id.et);
        initAutoComplete("pm25_history", mEt);
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
                    mCityName = getString(R.string.cityname_guangzhou);
                }
                GetPM25Value(mCityName);
                saveHistory("pm25_history", mCityName);
                initAutoComplete("pm25_history", mEt);
            }
            break;
            case R.id.clearhistory: {
                SharedPreferences sp = getSharedPreferences("network_url", 0);
                sp.edit().putString("pm25_history", mCleanHistory).commit();
                initAutoComplete("pm25_history", mEt);
            }
            break;
            default:
                break;
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtils.d(TAG,"onConfigurationChanged","onConfigurationChanged");
        //change to orientation, dismiss the keyboard
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            }
            mEt.dismissDropDown();
        }
    }
    /**
     * 把指定AutoCompleteTextView中内容保存到sharedPreference中指定的字符段
     *
     * @param field
     *            保存在sharedPreference中的字段名
     * @param cityname
     *            要操作的城市名称
     */
    private void saveHistory(String field,
                             String cityname) {
        String text = cityname;
        SharedPreferences sp = getSharedPreferences("network_url", 0);
        String longhistory = sp.getString(field, mCleanHistory);
        if (!longhistory.contains(text + ",")) {
            StringBuilder sb = new StringBuilder(longhistory);
             sb.insert(mCleanHistory.length(), text + ",");
            sp.edit().putString("pm25_history", sb.toString()).commit();
        }
    }

    /**
     * 初始化AutoCompleteTextView,每次点击EditText均出现历史记录
     *
     * @param field
     *            保存在sharedPreference中的字段名
     * @param autoCompleteTextView
     *            要操作的AutoCompleteTextView
     */
    private void initAutoComplete(String field,
                                  AutoCompleteTextView autoCompleteTextView) {
        SharedPreferences sp = getSharedPreferences("network_url", 0);
        String longhistory = sp.getString("pm25_history", mCleanHistory);
        String[] histories = longhistory.split(",");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, histories);
        // 只保留最近的50条的记录
        if (histories.length > 50) {
            String[] newHistories = new String[50];
            System.arraycopy(histories, 0, newHistories, 0, 50);
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, newHistories);
        }
        autoCompleteTextView.setAdapter(adapter);
//        autoCompleteTextView
//                .setOnFocusChangeListener(new View.OnFocusChangeListener() {
        //on Focus Change and display the history item
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        AutoCompleteTextView view = (AutoCompleteTextView) v;
//                        if (hasFocus) {
//                            view.showDropDown();
//                        }
//                    }
//                });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //click the Clear Rencet history item
                if ((position == 0) && (mEt.getText().toString().equals("Clear History"))) {
                    mTv.setText("");
                    mEt.setText("");
                    //Chear the histroy
                    SharedPreferences sp = getSharedPreferences("network_url", 0);
                    sp.edit().putString("pm25_history", mCleanHistory).commit();
                    initAutoComplete("pm25_history", mEt);
                }
            }
        });
        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView view = (AutoCompleteTextView) v;
                view.showDropDown();
            }
        });
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
                mTv.setText(error.getMessage());
            }
        },mheader,AqiInfo.class);

        mQueue.add(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
