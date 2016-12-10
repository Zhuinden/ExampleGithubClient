package com.zhuinden.examplegithubclient.presentation.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.presentation.paths.login.LoginKey;
import com.zhuinden.examplegithubclient.util.BasePresenter;
import com.zhuinden.examplegithubclient.util.Presenter;

import javax.inject.Inject;

import flowless.Bundleable;
import flowless.Direction;
import flowless.Flow;
import flowless.History;

/**
 * Created by Owner on 2016.12.10.
 */
@ActivityScope
public class MainPresenter
        extends BasePresenter<MainPresenter.ViewContract>
        implements Bundleable {
    @Inject
    public MainPresenter() {
    }

    public void setTitle(String title) {
        this.title = title;
        if(hasView()) {
            getView().setTitle(title);
        }
    }

    public void goToKey(Flow flow, Object newKey) {
        if(newKey instanceof LoginKey) {
            flow.setHistory(History.single(newKey), Direction.FORWARD);
        } else {
            flow.set(newKey);
        }
    }

    public interface ViewContract
            extends Presenter.ViewContract {
        void openDrawer();

        void closeDrawer();

        void setTitle(String title);
    }

    private boolean isDrawerOpen;
    private String title;

    @Override
    protected void initializeView(ViewContract view) {
        view.setTitle(title);
        if(isDrawerOpen) {
            openDrawer();
        } else {
            closeDrawer();
        }
    }

    public void openDrawer() {
        isDrawerOpen = true;
        if(hasView()) {
            getView().openDrawer();
        }
    }

    public void closeDrawer() {
        isDrawerOpen = false;
        if(hasView()) {
            getView().closeDrawer();
        }
    }

    public void toggleDrawer() {
        if(isDrawerOpen) {
            closeDrawer();
        } else {
            openDrawer();
        }
    }

    @Override
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isDrawerOpen", isDrawerOpen);
        bundle.putString("title", title);
        return bundle;
    }

    @Override
    public void fromBundle(@Nullable Bundle bundle) {
        if(bundle != null) {
            isDrawerOpen = bundle.getBoolean("isDrawerOpen");
            title = bundle.getString("title");
        }
    }
}
