package com.coder.nosandroid.niceosandroid.Utilities;

import android.util.Log;

/**
 * Created by saberhao on 2016/1/25.
 */
public class LogUtils {

    public static final int LEVEL = DebugType.NOTHING.ordinal();

    public static void v (String tag, String funcName, String msg) {
        if( LEVEL <= DebugType.VERBOSE.ordinal()) {
            Log.v(tag, getMsg(funcName, msg));
        }
    }

    public static void d (String tag, String funcName, String msg) {
        if( LEVEL <= DebugType.DEBUG.ordinal()) {
            Log.d(tag, getMsg(funcName, msg));
        }
    }

    public static void i (String tag, String funcName, String msg) {
        if( LEVEL <= DebugType.INFO.ordinal()) {
            Log.i(tag, getMsg(funcName, msg));
        }
    }

    public static void w (String tag, String funcName, String msg) {
        if( LEVEL <= DebugType.WARN.ordinal()) {
            Log.w(tag, getMsg(funcName, msg));
        }
    }

    public static void e (String tag, String funcName, String msg) {
        if( LEVEL <= DebugType.ERROR.ordinal()) {
            Log.e(tag, getMsg(funcName, msg));
        }
    }

    private static String getMsg(String funcName, String msg) {
        if (null == funcName) { funcName = " ";}
        if (null == msg) { msg = " ";}
        return ("[" + funcName + "]" + msg);
    }

    public enum DebugType {
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR,
        NOTHING
    }
}
