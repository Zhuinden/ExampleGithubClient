package com.zhuinden.examplegithubclient.presentation.paths.repositories;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;
import com.zhuinden.examplegithubclient.domain.interactor.GetRepositoriesInteractor;
import com.zhuinden.examplegithubclient.util.DaggerService;

import java.util.List;

import javax.inject.Inject;

import bolts.Task;
import butterknife.ButterKnife;

/**
 * Created by Zhuinden on 2016.12.10..
 */

public class RepositoriesView
        extends RelativeLayout {
    public RepositoriesView(Context context) {
        super(context);
        init();
    }

    public RepositoriesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RepositoriesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public RepositoriesView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        if(!isInEditMode()) {
            RepositoriesComponent repositoriesComponent = DaggerService.getComponent(getContext());
            repositoriesComponent.inject(this);
        }
    }

    @Inject
    GetRepositoriesInteractor getRepositoriesInteractor;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(!isInEditMode()) {
            ButterKnife.bind(this);
            getRepositoriesInteractor.getRepositories("Zhuinden").continueWith(task -> {
                List<Repository> repositories = task.getResult();
                for(Repository repository : repositories) {
                    Log.i("RepositoriesView", repository.getName());
                }
                return null;
            }, Task.UI_THREAD_EXECUTOR);
        }
    }
}
