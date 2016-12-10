package com.zhuinden.examplegithubclient.application.injection;

import com.zhuinden.examplegithubclient.application.injection.modules.OkHttpModule;
import com.zhuinden.examplegithubclient.application.injection.modules.RetrofitModule;
import com.zhuinden.examplegithubclient.domain.networking.HeaderInterceptor;
import com.zhuinden.examplegithubclient.domain.service.retrofit.RetrofitGithubService;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainActivity;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainPresenter;
import com.zhuinden.examplegithubclient.util.AnnotationCache;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Owner on 2016.12.10.
 */
@ActivityScope
@Component(modules = {OkHttpModule.class, RetrofitModule.class})
public interface MainActivityComponent {
    HeaderInterceptor headerInterceptor();
    AnnotationCache annotationCache();
    MainPresenter mainPresenter();

    OkHttpClient okHttpClient();

    RetrofitGithubService retrofitGithubService();

    Retrofit retrofit();

    void inject(MainActivity mainActivity);
}
