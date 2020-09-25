package com.coder.nosandroid.niceosandroid;

import android.app.Application;

import com.coder.nosandroid.niceosandroid.Utilities.Utils;

public class NiceOSAndroidApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.setApplicationContext(this.getApplicationContext());
    }
}
