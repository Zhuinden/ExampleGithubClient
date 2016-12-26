package com.zhuinden.examplegithubclient.domain.interactor.impl;

import com.zhuinden.examplegithubclient.application.BoltsExecutors;
import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.data.model.RepositoryDataSource;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;
import com.zhuinden.examplegithubclient.domain.interactor.GetRepositoriesInteractor;
import com.zhuinden.examplegithubclient.domain.service.GithubService;

import java.util.List;

import javax.inject.Inject;

import bolts.Task;

/**
 * Created by Owner on 2016.12.10.
 */
@ActivityScope
public class GetRepositoriesInteractorImpl
        implements GetRepositoriesInteractor {
    @Inject
    GithubService githubService;

    @Inject
    RepositoryDataSource repositoryDataSource;

    @Inject
    public GetRepositoriesInteractorImpl() {
    }

    @Override
    public Task<List<Repository>> getRepositories(final String user, int page) {
        return githubService.getRepositories(user, page).continueWith(task -> {
            if(task.isFaulted()) {
                throw task.getError();
            }
            repositoryDataSource.addRepositories(task.getResult());
            return task.getResult();
        }, BoltsExecutors.UI_THREAD);
    }
}
