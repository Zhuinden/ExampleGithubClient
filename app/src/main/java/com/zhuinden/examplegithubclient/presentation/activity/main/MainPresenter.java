package com.zhuinden.examplegithubclient.presentation.activity.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.presentation.paths.login.LoginKey;
import com.zhuinden.examplegithubclient.util.AnnotationCache;
import com.zhuinden.examplegithubclient.util.BundleFactory;

import java.util.Set;

import javax.inject.Inject;

import flowless.Bundleable;
import flowless.Direction;
import flowless.Dispatcher;
import flowless.Flow;
import flowless.History;
import flowless.Traversal;
import flowless.TraversalCallback;
import flowless.preset.DispatcherUtils;
import io.reactivex.Observable;

/**
 * Created by Owner on 2016.12.10.
 */
@ActivityScope
public class MainPresenter
        //extends BasePresenter<MainPresenter.ViewContract>
        implements Bundleable, Dispatcher {
    //public interface ViewContract
    //        extends Presenter.ViewContract {
    //}

    //@Override
    //protected void initializeView(ViewContract view) {
    //    // doesn't need to do a thing
    //}

    @Inject
    AnnotationCache annotationCache;

    @Inject
    Flow flow;

    @Inject
    public MainPresenter() {
    }

    private BehaviorRelay<MainState> state = BehaviorRelay.createDefault(MainState.create());

    public Observable<MainState> state() {
        return state.distinctUntilChanged();
    }

    public void setTitle(int title) {
        state.accept(state.getValue().toBuilder().setTitle(title).build());
    }

    public void goToKey(Object newKey) {
        closeDrawer();
        if(newKey instanceof LoginKey) {
            flow.setHistory(History.single(newKey), Direction.FORWARD);
        } else {
            flow.set(newKey);
        }
    }

    public boolean onBackPressed() {
        if(state.getValue().isDrawerOpen()) {
            closeDrawer();
            return true;
        }
        return false;
    }

    public void openDrawer() {
        state.accept(state.getValue().toBuilder().setIsDrawerOpen(true).build());
    }

    public void closeDrawer() {
        state.accept(state.getValue().toBuilder().setIsDrawerOpen(false).build());
    }

    public void toggleDrawer() {
        if(state.getValue().isDrawerOpen()) {
            closeDrawer();
        } else {
            openDrawer();
        }
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback callback) {
        Object newKey = DispatcherUtils.getNewKey(traversal);
        setTitle(annotationCache.getTitle(newKey));
        boolean isLeftDrawerEnabled = annotationCache.getLeftDrawerEnabled(newKey);
        if(isLeftDrawerEnabled) {
            enableLeftDrawer();
        } else {
            disableLeftDrawer();
        }

        boolean isToolbarButtonVisible = annotationCache.getToolbarButtonVisibility(newKey);
        Set<Class<?>> parents = annotationCache.getChildOf(newKey);
        if(parents.size() > 0) {
            hideDrawerToggle();
            showToolbarGoPrevious();
        } else {
            if(isToolbarButtonVisible) {
                showDrawerToggle();
            } else {
                hideDrawerToggle();
            }
            hideToolbarGoPrevious();
        }
    }

    private void enableLeftDrawer() {
        state.accept(state.getValue().toBuilder().setLeftDrawerEnabled(true).build());
    }

    private void disableLeftDrawer() {
        state.accept(state.getValue().toBuilder().setLeftDrawerEnabled(false).build());
    }

    private void hideToolbarGoPrevious() {
        state.accept(state.getValue().toBuilder().setToolbarGoPreviousVisible(false).build());
    }

    private void showToolbarGoPrevious() {
        state.accept(state.getValue().toBuilder().setToolbarGoPreviousVisible(true).build());
    }

    private void showDrawerToggle() {
        state.accept(state.getValue().toBuilder().setDrawerToggleVisible(true).build());
    }

    private void hideDrawerToggle() {
        state.accept(state.getValue().toBuilder().setDrawerToggleVisible(false).build());
    }

    @Override
    public Bundle toBundle() {
        Bundle bundle = BundleFactory.create();
        bundle.putParcelable("state", state.getValue());
        return bundle;
    }

    @Override
    public void fromBundle(@Nullable Bundle bundle) {
        if(bundle != null) {
            state.accept(bundle.getParcelable("state"));
        }
    }
}
