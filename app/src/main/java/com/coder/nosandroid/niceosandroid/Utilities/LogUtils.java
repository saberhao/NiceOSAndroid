package com.coder.nosandroid.niceosandroid.Utilities;

import android.util.Log;

/**
 * Created by saberhao on 2016/1/25.
 */
public class LogUtils {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN =4;
    public static final int ERROR = 5;
    public static final int NOTHING =6;
    public static final int LEVEL = VERBOSE;

    public static void v (String tag, String funcName, String msg) {
        if( LEVEL <= VERBOSE) {
            Log.v(tag, getMsg(funcName, msg));
        }
    }

    public static void d (String tag, String funcName, String msg) {
        if( LEVEL <= DEBUG) {
            Log.d(tag, getMsg(funcName, msg));
        }
    }

    public static void i (String tag, String funcName, String msg) {
        if( LEVEL <= INFO) {
            Log.i(tag, getMsg(funcName, msg));
        }
    }

    public static void w (String tag, String funcName, String msg) {
        if( LEVEL <= WARN) {
            Log.w(tag, getMsg(funcName, msg));
        }
    }

    public static void e (String tag, String funcName, String msg) {
        if( LEVEL <= ERROR) {
            Log.e(tag, getMsg(funcName, msg));
        }
    }

    private static String getMsg(String funcName, String msg) {
        if (null == funcName) { funcName = " ";}
        if (null == msg) { msg = " ";}
        return ("[" + funcName + "]" + msg);
    }

}
