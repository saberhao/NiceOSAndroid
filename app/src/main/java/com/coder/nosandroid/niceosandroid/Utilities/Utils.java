package com.coder.nosandroid.niceosandroid.Utilities;

import android.content.Context;

public class Utils {
    private static Context ApplicationContext = null;

    public static Context getApplicationContext() {
        return ApplicationContext;
    }

    public static void setApplicationContext(Context applicationContext) {
        ApplicationContext = applicationContext;
    }

}
