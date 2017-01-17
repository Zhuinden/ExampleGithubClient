package com.zhuinden.examplegithubclient.domain.interactor.impl;

import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.data.repository.RepositoryRepository;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;
import com.zhuinden.examplegithubclient.domain.interactor.GetRepositoriesInteractor;
import com.zhuinden.examplegithubclient.domain.service.GithubService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Owner on 2016.12.10.
 */
@ActivityScope
public class GetRepositoriesInteractorImpl
        implements GetRepositoriesInteractor {
    @Inject
    GithubService githubService;

    @Inject
    RepositoryRepository repositoryRepository;

    @Inject
    public GetRepositoriesInteractorImpl() {
    }

    @Override
    public Single<List<Repository>> getRepositories(final String user, int page) {
        return githubService.getRepositories(user, page).map(repositories -> repositoryRepository.saveOrUpdate(repositories));
    }
}
