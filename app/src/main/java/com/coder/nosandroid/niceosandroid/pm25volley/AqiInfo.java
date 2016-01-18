package com.coder.nosandroid.niceosandroid.pm25volley;

/**
 * Created by saberhao on 2016/1/18.
 */
public class AqiInfo {

    int errNum;
    String retMsg;
    public Aqi retData;
    static public class Aqi {
        public String city;
        public String time;
        public int aqi;
        public String level;
        public String core;

        @Override
        public String toString() {
            return "aqi : " + aqi + "\n" +
                    "city : " + city + "\n" +
                    "level : " + level + "\n" +
                    "core : " + core + '\n';
        }
    }

}
