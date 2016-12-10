package com.zhuinden.examplegithubclient.application.injection;

import com.zhuinden.examplegithubclient.presentation.activity.main.MainActivity;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainPresenter;
import com.zhuinden.examplegithubclient.util.AnnotationCache;

import dagger.Component;

/**
 * Created by Owner on 2016.12.10.
 */
@Component
@ActivityScope(MainActivity.class)
public interface MainActivityComponent {
    AnnotationCache annotationCache();
    MainPresenter mainPresenter();

    void inject(MainActivity mainActivity);
}
