package com.zhuinden.examplegithubclient.presentation.paths.repositories;

import com.zhuinden.examplegithubclient.application.injection.KeyScope;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;
import com.zhuinden.examplegithubclient.domain.interactor.GetRepositoriesInteractor;
import com.zhuinden.examplegithubclient.util.BasePresenter;
import com.zhuinden.examplegithubclient.util.Presenter;

import java.util.List;

import javax.inject.Inject;

import bolts.Task;

/**
 * Created by Zhuinden on 2016.12.18..
 */

@KeyScope(RepositoriesKey.class)
public class RepositoriesPresenter
        extends BasePresenter<RepositoriesPresenter.ViewContract> {
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

    private int currentPage = 1;

    private boolean isDownloading;
    private boolean downloadedAll;

    public boolean didDownloadAll() {
        return downloadedAll;
    }

    private void downloadPage() {
        if(!downloadedAll) {
            isDownloading = true;
            getRepositoriesInteractor.getRepositories("square", currentPage).continueWith(task -> {
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
            }, Task.UI_THREAD_EXECUTOR);
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
