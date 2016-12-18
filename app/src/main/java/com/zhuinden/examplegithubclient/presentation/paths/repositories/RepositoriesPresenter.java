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
    }

    @Override
    protected void initializeView(ViewContract view) {
        if(repositories == null) {
            getRepositoriesInteractor.getRepositories("square").continueWith(task -> {
                repositories = task.getResult();
                updateRepositoriesInView(); // TODO: can View be null here?
                return null;
            }, Task.UI_THREAD_EXECUTOR);
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
}
