package com.zhuinden.examplegithubclient.domain.interactor.impl;

import com.zhuinden.examplegithubclient.application.BoltsExecutors;
import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
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
    private GithubService githubService;

    @Inject
    public GetRepositoriesInteractorImpl(GithubService githubService) {
        this.githubService = githubService;
    }

    @Override
    public Task<List<Repository>> getRepositories(final String user, int page) {
        return Task.call(() -> githubService.getRepositories(user, page), BoltsExecutors.BACKGROUND_THREAD);
    }
}
