package com.zhuinden.examplegithubclient.domain.service;

import com.zhuinden.examplegithubclient.domain.data.response.organization.Organization;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;

import java.io.IOException;
import java.util.List;

/**
 * Created by Owner on 2016.12.10.
 */

public interface GithubService {
    List<Organization> getOrganizations(String user)
            throws IOException;

    List<Repository> getRepositories(String user)
            throws IOException;
}
