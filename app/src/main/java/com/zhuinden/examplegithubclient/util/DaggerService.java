package com.zhuinden.examplegithubclient.util;

import android.content.Context;

import flowless.Flow;

/**
 * Created by Owner on 2016.12.10.
 */

public class DaggerService {
    public static final String TAG = "DaggerService";

    @SuppressWarnings("unchecked")
    public static <T> T getComponent(Context context) {
        //noinspection ResourceType
        return (T) Flow.services(context).getService(context, TAG);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getComponent(Context context, Object key) {
        //noinspection ResourceType
        return (T) Flow.services(context).getService(key, TAG);
    }
}