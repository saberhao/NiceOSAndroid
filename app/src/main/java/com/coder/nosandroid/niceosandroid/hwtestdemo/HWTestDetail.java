package com.coder.nosandroid.niceosandroid.hwtestdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.coder.nosandroid.niceosandroid.R;
import com.coder.nosandroid.niceosandroid.Utilities.LogUtils;

import java.util.ArrayList;

/**
 * Created by saberhao on 2016/1/27.
 */
public class HWTestDetail extends AppCompatActivity{

    private TextView mTestResultTv;
    private TextView mTestItemTv;
    private  CircularProgressBar mCirclePBar;
    private ArrayList<HWTestType> mItemTypeList;
    private ArrayList<HWTestType> mFailItemList;
    private ArrayList<HWTestType> mPassItemList;
    private int mTestItemMaxSize;
    private HWTestType mCurrentTest;
    private int mTestItemCnt;
    private boolean mTestFinish;
    private static final String TAG = "HWTestDetail";
    private SensorManager sm;
    private Boolean isSensorUpdate = false;
    private BroadcastReceiver mBroadcastReceiver;
    private Boolean isTestPass;
    private Boolean isBTAutoEnable = false;
    private Boolean isWLANAutoEnable;
    private Boolean mStateChange;
    private HWTestWorkerTask task;
    private AllSensorEventListener mSensorEventListener;
    private Boolean isAllCompleted = false;
    private MediaPlayer mMediaPlayer = null;
    private BluetoothAdapter mBTAdapter = BluetoothAdapter.getDefaultAdapter();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hwtestdetail);
        setTitle("HWTestDemo");
        mItemTypeList = new ArrayList<>();
        mFailItemList = new ArrayList<>();
        mPassItemList = new ArrayList<>();

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        mTestResultTv = (TextView) findViewById(R.id.result_item);
        mTestItemTv =(TextView)findViewById(R.id.test_item);
        initTestItem();
        mCirclePBar = (CircularProgressBar)findViewById(R.id.custom_progressBar);
        mCirclePBar.setColor(R.color.teal);
        mCirclePBar.setBackgroundColor(mCirclePBar.adjustAlpha(R.color.teal,0.3f));
        mTestItemMaxSize = mItemTypeList.size();
        mCirclePBar.setMax(mTestItemMaxSize);
        mCirclePBar.setProgress(0f);
        mCurrentTest = mItemTypeList.remove(0);
        task = new HWTestWorkerTask();
        task.execute(mCurrentTest);

    }

    public class HWTestWorkerTask extends AsyncTask<HWTestType,HWTestType,Boolean> {


        @Override
        protected Boolean doInBackground(HWTestType... params) {
            LogUtils.d(TAG,"doInBackground","SyncTask doInBackground : "+params[0]);
            Boolean mItemTestResult = false;
            if(mTestItemMaxSize  != (mItemTypeList.size() + 1)) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    LogUtils.e(TAG,"doInBackground",e.getMessage());
                }
            }
            switch(params[0]) {
                case SPEAKER:
                    publishProgress(HWTestType.SPEAKER);
                    mItemTestResult = SpeakerCheck();
                    break;
                case VIBRATOR:
                    publishProgress(HWTestType.VIBRATOR);
                    mItemTestResult = VibratorCheck();
                    break;
                case ACCELEROMETER:
                    publishProgress(HWTestType.ACCELEROMETER);
                    mItemTestResult = AccelerometerCheck();
                    break;
                case GYROSCOPE:
                    publishProgress(HWTestType.GYROSCOPE);
                    mItemTestResult = GyroscopeCheck();
                    break;
                case PROXIMITY:
                    publishProgress(HWTestType.PROXIMITY);
                    mItemTestResult = PoximityCheck();
                    break;
                case MAGNETIC:
                    publishProgress(HWTestType.MAGNETIC);
                    mItemTestResult = MagnaticCheck();
                    break;
                case BATTAERY:
                    publishProgress(HWTestType.BATTAERY);
                    mItemTestResult = BatteryCheck();
                    break;
                case BT:
                    publishProgress(HWTestType.BT);
                    mItemTestResult = BTCheck();
                    break;
                case WLAN:
                    publishProgress(HWTestType.WLAN);
                    mItemTestResult = CheckWlan();
            }
            return mItemTestResult;
        }

        @Override
        protected void onProgressUpdate(HWTestType... values) {
            super.onProgressUpdate(values);
            LogUtils.d(TAG,"onProgressUpdate","Update the TestItem UI");
            mTestResultTv.setText(R.string.ongoing_test);
            switch (values[0]) {
                case SPEAKER:
                    mTestItemTv.setText(R.string.item_speaker);
                    break;
                case VIBRATOR:
                    mTestItemTv.setText(R.string.item_vibration);
                    break;
                case ACCELEROMETER:
                    mTestItemTv.setText(R.string.item_accelerometer);
                    break;
                case GYROSCOPE:
                    mTestItemTv.setText(R.string.item_gyroscope);
                    break;
                case PROXIMITY:
                    mTestItemTv.setText(R.string.item_proximity);
                    break;
                case MAGNETIC:
                    mTestItemTv.setText(R.string.item_magnetic);
                    break;
                case BATTAERY:
                    mTestItemTv.setText(R.string.item_battary);
                    break;
                case BT:
                    mTestItemTv.setText(R.string.item_bt);
                    break;
                case WLAN:
                    mTestItemTv.setText(R.string.item_wlan);
            }
        }

        @Override
        protected void onPostExecute(Boolean isPass) {
            super.onPostExecute(isPass);
            LogUtils.d(TAG, "onPostExecute", "Update the test Result and next test start");
            mTestResultTv.setText((isPass ? R.string.test_pass : R.string.test_fail));
            mCirclePBar.setProgressWithAnimation(mTestItemMaxSize - mItemTypeList.size());
            if(isPass) {
                mPassItemList.add(mCurrentTest);
            } else {
                mFailItemList.add(mCurrentTest);
            }
            if(mItemTypeList.size() > 0) {
                mCurrentTest = mItemTypeList.remove(0);
                LogUtils.d(TAG,"onPostExecute","mCurrentTest = " + mCurrentTest);
                task = new HWTestWorkerTask();
                task.execute(mCurrentTest);
            } else {
                // Test Completed
                LogUtils.d(TAG,"onPostExecute","Test Completed and show the result list");
                isAllCompleted = true;
                Intent intent = new Intent(HWTestDetail.this,HWTestResultList.class);
                intent.putExtra("FailItemList",mFailItemList);
                intent.putExtra("PassItemList",mPassItemList);
                startActivity(intent);
            }
        }

    }

    Boolean SpeakerCheck() {
        LogUtils.d(TAG,"SpeakerCheck()","Start SpeakerCheck");
        mTestFinish = false;
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int tempVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        Uri mMuiscUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mMediaPlayer = MediaPlayer.create(this,mMuiscUri);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (maxVolume * 0.3), 0);
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            LogUtils.e(TAG,"SoundCheck",e.getMessage());
        }
        am.setStreamVolume(AudioManager.STREAM_MUSIC,(int) (maxVolume * 0.6),0);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            LogUtils.e(TAG,"SoundCheck",e.getMessage());
        }
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
        am.setStreamVolume(AudioManager.STREAM_MUSIC,tempVolume,0);
        return true;
    }

    Boolean VibratorCheck() {
        LogUtils.d(TAG,"VibratorCheck()","Start VibratorCheck");
        long[] mPattern = {400,600,400,600,400,600};
        int isRepeat = -1; // do not repeat
        Vibrator mVibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if(null == mVibrator) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LogUtils.e(TAG,"VibratorCheck",e.getMessage());
            }
            LogUtils.e(TAG,"VibratorCheck","Fail to init vibrator service");
            return false;
        } else {
            mVibrator.vibrate(mPattern,isRepeat);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                LogUtils.e(TAG,"VibratorCheck",e.getMessage());
            }
            return true;
        }
    }

    Boolean GyroscopeCheck() {
        LogUtils.d(TAG,"GyroscopeCheck","Start GyroscopeCheck");
        Boolean testResult = false;
        Sensor mGyrSensor = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorEventListener = new AllSensorEventListener();
        sm.registerListener(mSensorEventListener, mGyrSensor, SensorManager.SENSOR_DELAY_UI);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LogUtils.e(TAG,"VibratorCheck",e.getMessage());
        }
        if(null == mGyrSensor) {
            LogUtils.e(TAG,"GyroscopeCheck","Fail to init the Gyroscope Sensor");
        } else if (!isSensorUpdate) {
            LogUtils.e(TAG,"GyroscopeCheck","Fail to detect Gyroscope Sensor change");
        } else {
            testResult = true;
        }
        sm.unregisterListener(mSensorEventListener);
        return testResult;
    }

    Boolean AccelerometerCheck() {
        LogUtils.d(TAG, "AccelerometerCheck", "Accelerometer Check Start");
        Boolean testResult = false;
        isSensorUpdate = false;
        Sensor mAccSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorEventListener = new AllSensorEventListener();
        sm.registerListener(mSensorEventListener,mAccSensor,SensorManager.SENSOR_DELAY_UI);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LogUtils.e(TAG,"VibratorCheck",e.getMessage());
        }
        if(null == mAccSensor) {
            LogUtils.e(TAG,"AccelerometerCheck","Fail to init the Accelerometer Sensor");
        } else if (!isSensorUpdate) {
            LogUtils.e(TAG,"AccelerometerCheck","Fail to detect Accelerometer Sensor change");
        } else {
            testResult = true;
        }
        sm.unregisterListener(mSensorEventListener);
        return testResult;
    }

    Boolean PoximityCheck() {
        LogUtils.d(TAG, "PoximityCheck", "Poximity Sensor Check Start");
        Boolean testResult = false;
        isSensorUpdate = false;
        Sensor mProxSensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorEventListener = new AllSensorEventListener();
        sm.registerListener(mSensorEventListener,mProxSensor,SensorManager.SENSOR_DELAY_UI);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LogUtils.e(TAG,"PoximityCheck",e.getMessage());
        }
        if(null == mProxSensor) {
            LogUtils.e(TAG,"PoximityCheck","Fail to init the Proximity Sensor");
        } else if (!isSensorUpdate) {
            LogUtils.e(TAG,"PoximityCheck","Fail to detect Proximity Sensor change");
        } else {
            testResult = true;
        }
        sm.unregisterListener(mSensorEventListener);
        return testResult;
    }

    Boolean MagnaticCheck() {
        LogUtils.d(TAG, "MagnaticCheck", "Magnatic Sensor Check Start");
        Boolean testResult = false;
        isSensorUpdate = false;
        Sensor mMagnaticSensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorEventListener = new AllSensorEventListener();
        sm.registerListener(mSensorEventListener, mMagnaticSensor, SensorManager.SENSOR_DELAY_UI);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LogUtils.e(TAG,"MagnaticCheck",e.getMessage());
        }
        if(null == mMagnaticSensor) {
            LogUtils.e(TAG,"MagnaticCheck","Fail to init the Magnatic Sensor");
        } else if (!isSensorUpdate) {
            LogUtils.e(TAG,"MagnaticCheck","Fail to detect Magnatic Sensor change");
        } else {
            testResult = true;
        }
        sm.unregisterListener(mSensorEventListener);
        return testResult;
    }

    private class AllSensorEventListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER :
                case Sensor.TYPE_GYROSCOPE :
                case Sensor.TYPE_PROXIMITY :
                case Sensor.TYPE_MAGNETIC_FIELD:
                    LogUtils.d(TAG,"onSensorChanged","Receive the Sensor change");
                    isSensorUpdate = true;
                    break;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    private boolean BatteryCheck() {
        LogUtils.d(TAG,"BatteryCheck","Battery Check Start");
        isTestPass = false;
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LogUtils.d(TAG,"BatteryCheck","onReceiver : "+intent.getAction());
                String action = intent.getAction();
                if(action.equals(intent.ACTION_BATTERY_CHANGED)) {
                    isTestPass = true;
                }
            }
        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        this.registerReceiver(mBroadcastReceiver,filter);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LogUtils.e(TAG,"BatteryCheck",e.getMessage());
        }

        this.unregisterReceiver(mBroadcastReceiver);
        mBroadcastReceiver = null;

        if(isTestPass) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean BTCheck() {
        LogUtils.d(TAG,"BTCheck","Start BTCheck");
        isTestPass = false;
        isBTAutoEnable = false;
        mStateChange = false;
        int testcnt = 0;
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LogUtils.d(TAG,"BTCheck","onReceive");
                String action = intent.getAction();
                if(action.equals(BluetoothDevice.ACTION_FOUND)) {
                    LogUtils.d(TAG,"BTCheck","BluetoothDevice.ACTION_FOUND");
                    isTestPass = true;
                } else if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                    LogUtils.d(TAG,"BTCheck","BluetoothAdapter.ACTION_STATE_CHANGED");
                    mStateChange = true;
                }
            }
        };
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        this.registerReceiver(mBroadcastReceiver, filter);
        this.registerReceiver(mBroadcastReceiver, filter1);
        if(mBTAdapter == null) return false;
        else if(!mBTAdapter.isEnabled()) {
            LogUtils.d(TAG,"BTCheck","Enable BT");
            mBTAdapter.enable(); // enable the BT device
            isBTAutoEnable = true;
        }
        // To Avoid startDiscovery fail and find device fail
        while(true) {
            LogUtils.d(TAG,"BTCHECK","Checking the State Change");
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                LogUtils.e(TAG,"BTCheck",e.getMessage());
            }
            testcnt++;
            if(testcnt > 10) break;

        }

        // To find the BT Devices
        if(!mBTAdapter.isDiscovering()) {
            LogUtils.d(TAG, "BTCheck", "Start BT Discovering");
            if(mBTAdapter.startDiscovery() == false) {
                LogUtils.d(TAG,"BTCheck","Fail to BT Discovering");
                this.unregisterReceiver(mBroadcastReceiver);
                mBroadcastReceiver = null;
                return false;
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LogUtils.e(TAG,"BTCheck",e.getMessage());
        }

        unregisterReceiver(mBroadcastReceiver);
        mBroadcastReceiver = null;

        if(isTestPass) {
            return true;
        } else {
            return false;
        }
    }

    Boolean CheckWlan() {
        LogUtils.d(TAG,"CheckWlan()","CheckWlan Start");
        ConnectivityManager mConnectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Boolean isWifiConnected = mNetworkInfo.isConnected();
        isWLANAutoEnable = false;
        isTestPass = false;
        // check whether wifi connnect or not
        if(isWifiConnected) {
            LogUtils.d(TAG,"CheckWlan","Wifi Connected!");
            return true;
        }
        WifiManager mWifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
        if(null == mWifiManager) {
            LogUtils.e(TAG,"CheckWlan","init WifiManager Fail");
            return false;
        }
        if(!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
            isWLANAutoEnable = true;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                LogUtils.e(TAG,"CheckWlan",e.getMessage());
            }
        }
       mBroadcastReceiver = new BroadcastReceiver() {
           @Override
           public void onReceive(Context context, Intent intent) {
               String action = intent.getAction();
               if(action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                   LogUtils.d(TAG,"WLANCHECK","Receive the wifi scan avaible action");
                   isTestPass = true;
               }
           }
       };
        IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        this.registerReceiver(mBroadcastReceiver, filter);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LogUtils.e(TAG,"WLANheck",e.getMessage());
        }
        if(isWLANAutoEnable) {
            mWifiManager.setWifiEnabled(false);
        }

        unregisterReceiver(mBroadcastReceiver);
        mBroadcastReceiver = null;

        if(isTestPass) {
            return true;
        } else {
            return false;
        }
    }

    void initTestItem() {
        mItemTypeList.add(HWTestType.SPEAKER);
        mItemTypeList.add(HWTestType.VIBRATOR);
        mItemTypeList.add(HWTestType.ACCELEROMETER);
        mItemTypeList.add(HWTestType.GYROSCOPE);
        mItemTypeList.add(HWTestType.PROXIMITY);
        mItemTypeList.add(HWTestType.MAGNETIC);
        mItemTypeList.add(HWTestType.WLAN);
        mItemTypeList.add(HWTestType.BT);
        mItemTypeList.add(HWTestType.BATTAERY);
    }
    public enum HWTestType {
        SPEAKER,
        VIBRATOR,
        ACCELEROMETER,
        GYROSCOPE,
        PROXIMITY,
        MAGNETIC,
        WLAN,
        BT,
        BATTAERY
    }

    @Override
    protected void onDestroy() {

        if(!isAllCompleted) {
            Toast.makeText(this,R.string.cancel_test,Toast.LENGTH_SHORT).show();
        }
        if(task != null && task.getStatus() != AsyncTask.Status.FINISHED) {
            task.cancel(true);
        }

        //close the BT device
        if(isBTAutoEnable && (null != mBTAdapter)) {
            mBTAdapter.disable(); //关闭BT
        }

        //cancel Speaker Check
        if(null != mMediaPlayer) {
            if(mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        // cancel the all sensor test
        try{
            sm.unregisterListener(mSensorEventListener);
            if(null != mBroadcastReceiver) {
                this.unregisterReceiver(mBroadcastReceiver);
                mBroadcastReceiver = null;
            }
        }catch (Exception e) {
            LogUtils.e(TAG,"onDestroy",e.getMessage());
        }

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
