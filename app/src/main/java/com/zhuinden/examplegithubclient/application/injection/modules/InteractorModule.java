package com.zhuinden.examplegithubclient.application.injection.modules;

import com.zhuinden.examplegithubclient.domain.interactor.GetRepositoriesInteractor;
import com.zhuinden.examplegithubclient.domain.interactor.impl.GetRepositoriesInteractorImpl;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Owner on 2016.12.10.
 */

@Module
public abstract class InteractorModule {
    @Binds
    abstract GetRepositoriesInteractor getRepositoriesInteractor(GetRepositoriesInteractorImpl getRepositoriesInteractor);
}
