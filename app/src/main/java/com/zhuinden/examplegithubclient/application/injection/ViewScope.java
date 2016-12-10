package com.zhuinden.examplegithubclient.application.injection;

import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Owner on 2016.12.10.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewScope {
    Class<? extends View> value();
}
