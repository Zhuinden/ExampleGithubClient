package com.zhuinden.examplegithubclient.application.injection;

import com.zhuinden.examplegithubclient.presentation.activity.main.MainActivity;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainPresenter;

import dagger.Component;

/**
 * Created by Owner on 2016.12.10.
 */
@Component
@ActivityScope(MainActivity.class)
public interface MainActivityComponent {
    MainPresenter mainPresenter();

    void inject(MainActivity mainActivity);
}
