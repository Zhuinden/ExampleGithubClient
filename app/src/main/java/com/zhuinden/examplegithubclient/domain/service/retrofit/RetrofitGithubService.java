package com.zhuinden.examplegithubclient.domain.service.retrofit;

import com.zhuinden.examplegithubclient.domain.data.response.organization.Organization;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Owner on 2016.12.10.
 */

public interface RetrofitGithubService {
    @GET("orgs/{user}/repos")
    Call<List<Organization>> getOrganizations(@Path("user") String user);

    @GET("users/{user}/repos")
    Call<List<Repository>> getRepositories(@Path("user") String user);
}
