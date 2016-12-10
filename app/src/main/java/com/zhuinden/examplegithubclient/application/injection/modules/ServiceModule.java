package com.zhuinden.examplegithubclient.application.injection.modules;

import com.zhuinden.examplegithubclient.domain.service.GithubService;
import com.zhuinden.examplegithubclient.domain.service.impl.GithubServiceImpl;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Owner on 2016.12.10.
 */

@Module
public abstract class ServiceModule {
    @Binds
    abstract GithubService githubService(GithubServiceImpl githubService);
}
