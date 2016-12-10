package com.zhuinden.examplegithubclient.application;

import android.app.Application;

/**
 * Created by Zhuinden on 2016.12.09..
 */

public class CustomApplication extends Application {
    private static CustomApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    public static CustomApplication get() {
        return INSTANCE;
    }
}
