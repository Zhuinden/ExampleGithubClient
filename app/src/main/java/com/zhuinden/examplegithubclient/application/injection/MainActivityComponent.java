package com.zhuinden.examplegithubclient.application.injection;

import com.zhuinden.examplegithubclient.application.injection.modules.InteractorModule;
import com.zhuinden.examplegithubclient.application.injection.modules.OkHttpModule;
import com.zhuinden.examplegithubclient.application.injection.modules.RetrofitModule;
import com.zhuinden.examplegithubclient.application.injection.modules.ServiceModule;
import com.zhuinden.examplegithubclient.domain.interactor.GetRepositoriesInteractor;
import com.zhuinden.examplegithubclient.domain.networking.HeaderInterceptor;
import com.zhuinden.examplegithubclient.domain.service.GithubService;
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
@Component(modules = {OkHttpModule.class, RetrofitModule.class, InteractorModule.class, ServiceModule.class})
public interface MainActivityComponent {
    HeaderInterceptor headerInterceptor();

    AnnotationCache annotationCache();

    MainPresenter mainPresenter();

    OkHttpClient okHttpClient();

    RetrofitGithubService retrofitGithubService();

    Retrofit retrofit();

    GithubService githubService();

    GetRepositoriesInteractor getRepositoriesInteractor();

    void inject(MainActivity mainActivity);
}
