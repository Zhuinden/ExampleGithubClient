package com.zhuinden.examplegithubclient.presentation.activity.main;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuinden.examplegithubclient.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flowless.ActivityUtils;
import flowless.preset.FlowLifecycles;
import io.reactivex.disposables.Disposable;

/**
 * Created by Zhuinden on 2016.12.21..
 */

public class MainView
        extends DrawerLayout
        implements /*MainPresenter.ViewContract,*/ FlowLifecycles.ViewLifecycleListener {
    public MainView(Context context) {
        super(context);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @BindView(R.id.root)
    ViewGroup root;

    @BindView(R.id.toolbar_text)
    TextView toolbarText;

    @BindView(R.id.toolbar)
    ViewGroup toolbar;

    @BindView(R.id.toolbar_drawer_toggle)
    View drawerToggle;

    @BindView(R.id.toolbar_go_previous)
    View toolbarGoPrevious;

    @Inject
    MainPresenter mainPresenter;

    @OnClick(R.id.toolbar_drawer_toggle)
    public void onClickDrawerToggle() {
        mainPresenter.toggleDrawer();
    }

    @OnClick(R.id.toolbar_go_previous)
    public void onClickGoPrevious() {
        ActivityUtils.getActivity(getContext()).onBackPressed();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(!isInEditMode()) {
            ButterKnife.bind(this);
        }
    }

    public void disableLeftDrawer() {
        setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void enableLeftDrawer() {
        setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    public void hideDrawerToggle() {
        drawerToggle.setVisibility(View.GONE);
    }

    public void showDrawerToggle() {
        drawerToggle.setVisibility(View.VISIBLE);
    }

    public void hideToolbarGoPrevious() {
        toolbarGoPrevious.setVisibility(View.GONE);
    }

    public void showToolbarGoPrevious() {
        toolbarGoPrevious.setVisibility(View.VISIBLE);
    }

    public void openDrawer() {
        openDrawer(Gravity.LEFT);
    }

    public void closeDrawer() {
        closeDrawer(Gravity.LEFT);
    }

    public void setTitle(@StringRes int title) {
        toolbarText.setText(title);
    }

    public ViewGroup getRoot() {
        return root;
    }

    Disposable subscription;

    @Override
    public void onViewRestored() {
        //mainPresenter.attachView(this);
        subscription = mainPresenter.state().subscribe(mainState -> {
            setTitle(mainState.title());
            if(mainState.isDrawerOpen()) {
                openDrawer();
            } else {
                closeDrawer();
            }
            if(mainState.toolbarGoPreviousVisible()) {
                showToolbarGoPrevious();
            } else {
                hideToolbarGoPrevious();
            }
            if(mainState.drawerToggleVisible()) {
                showDrawerToggle();
            } else {
                hideDrawerToggle();
            }
            if(mainState.leftDrawerEnabled()) {
                enableLeftDrawer();
            } else {
                disableLeftDrawer();
            }
        });
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        if(!subscription.isDisposed()) {
            subscription.dispose();
        }
        //mainPresenter.detachView();
    }
}
