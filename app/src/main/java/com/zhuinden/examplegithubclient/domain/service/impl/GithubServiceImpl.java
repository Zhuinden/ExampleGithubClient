package com.zhuinden.examplegithubclient.domain.service.impl;

import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.domain.data.response.organization.Organization;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;
import com.zhuinden.examplegithubclient.domain.service.GithubService;
import com.zhuinden.examplegithubclient.domain.service.retrofit.RetrofitGithubService;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Owner on 2016.12.10.
 */
@ActivityScope
public class GithubServiceImpl
        implements GithubService {
    private RetrofitGithubService retrofitGithubService;

    @Inject
    public GithubServiceImpl(RetrofitGithubService retrofitGithubService) {
        this.retrofitGithubService = retrofitGithubService;
    }

    @Override
    public List<Organization> getOrganizations(String user)
            throws IOException {
        return retrofitGithubService.getOrganizations(user).execute().body();
    }

    @Override
    public List<Repository> getRepositories(String user)
            throws IOException {
        return retrofitGithubService.getRepositories(user).execute().body();
    }
}
