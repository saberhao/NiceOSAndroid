package com.coder.nosandroid.niceosandroid.nosdemo;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by saberhao on 2016/1/18.
 */
public class ContentItem {
    int id;
    Class<?> cls;
    String itemname;
    String desc;
    boolean isNew = false;

    public ContentItem(int id, Class<?> cls, String itemname, String desc) {
        this.id = id;
        this.cls = cls;
        this.itemname = itemname;
        this.desc = desc;
    }
}
