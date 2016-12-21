package com.zhuinden.examplegithubclient.presentation.paths.repositories;

import com.zhuinden.examplegithubclient.application.BoltsExecutors;
import com.zhuinden.examplegithubclient.application.injection.KeyScope;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;
import com.zhuinden.examplegithubclient.domain.interactor.GetRepositoriesInteractor;
import com.zhuinden.examplegithubclient.util.BasePresenter;
import com.zhuinden.examplegithubclient.util.Presenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Zhuinden on 2016.12.18..
 */

@KeyScope(RepositoriesKey.class)
public class RepositoriesPresenter
        extends BasePresenter<RepositoriesPresenter.ViewContract> {
    static final String REPO_NAME = "square";

    @Inject
    GetRepositoriesInteractor getRepositoriesInteractor;

    @Inject
    public RepositoriesPresenter() {
    }

    List<Repository> repositories;

    public interface ViewContract
            extends Presenter.ViewContract {
        void updateRepositories(List<Repository> repositories);

        void openRepository(String url);
    }

    int currentPage = 1;

    boolean isDownloading;
    boolean downloadedAll;

    public boolean didDownloadAll() {
        return downloadedAll;
    }

    private void downloadPage() {
        if(!downloadedAll) {
            isDownloading = true;
            getRepositoriesInteractor.getRepositories(REPO_NAME, currentPage).continueWith(task -> {
                isDownloading = false;
                List<Repository> repositories = task.getResult();
                if(this.repositories == null) { // should be in data layer
                    this.repositories = repositories;
                } else {
                    this.repositories.addAll(repositories);
                }
                if(repositories.size() <= 0) {
                    downloadedAll = true;
                } else {
                    currentPage++;
                }
                updateRepositoriesInView(); // TODO: can View be null here?
                return null;
            }, BoltsExecutors.UI_THREAD);
        }
    }

    @Override
    protected void initializeView(ViewContract view) {
        if(repositories == null) {
            downloadPage();
        } else {
            updateRepositoriesInView();
        }
    }

    private void updateRepositoriesInView() {
        view.updateRepositories(repositories);
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void openRepository(Repository repository) {
        view.openRepository(repository.getUrl());
    }

    public void downloadMore() {
        if(!isDownloading) {
            downloadPage();
        }
    }
}
