package com.zhuinden.examplegithubclient.presentation.activity.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.util.AnnotationCache;
import com.zhuinden.examplegithubclient.util.DaggerService;
import com.zhuinden.examplegithubclient.util.FlowlessActivity;

import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flowless.Flow;
import flowless.KeyManager;
import flowless.ServiceProvider;
import flowless.State;
import flowless.Traversal;
import flowless.TraversalCallback;
import flowless.preset.DispatcherUtils;

public class MainActivity
        extends FlowlessActivity
        implements MainPresenter.ViewContract {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.root)
    ViewGroup root;

    @BindView(R.id.hidden_toolbar)
    Toolbar hiddenToolbar;

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

    @Inject
    AnnotationCache annotationCache;

    @OnClick(R.id.toolbar_drawer_toggle)
    public void onClickDrawerToggle() {
        mainPresenter.toggleDrawer();
    }

    @OnClick(R.id.toolbar_go_previous)
    public void onClickGoPrevious() {
        onBackPressed();
    }

    private void disableLeftDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void enableLeftDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    ActionBarDrawerToggle actionBarDrawerToggle;

    private boolean didInject = false;

    private void injectServices() {
        if(didInject) {
            return;
        }
        didInject = true;
        Flow flow = Flow.get(getBaseContext());
        ServiceProvider serviceProvider = flow.getServices();
        MainKey mainKey = Flow.getKey(getBaseContext());
        MainComponent mainComponent;
        if(!serviceProvider.hasService(mainKey, DaggerService.TAG)) {
            mainComponent = DaggerMainComponent.create();
            serviceProvider.bindService(mainKey, DaggerService.TAG, mainComponent);
        } else {
            mainComponent = DaggerService.getComponent(getBaseContext(), mainKey);
        }
        mainComponent.inject(this);
        KeyManager keyManager = flow.getStates();
        State state = keyManager.getState(mainKey);
        mainPresenter.fromBundle(state.getBundle());
    }

    @Override
    protected Object getGlobalKey() {
        return MainKey.KEY;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(hiddenToolbar);
        transitionDispatcher.getRootHolder().setRoot(root);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, hiddenToolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mainPresenter.openDrawer();
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mainPresenter.closeDrawer();
                supportInvalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        //drawerLayout.setScrimColor(ContextCompat.getColor(this, android.R.color.transparent));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        injectServices();
        mainPresenter.attachView(this);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onDestroy() {
        mainPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(mainPresenter.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Flow flow = Flow.get(getBaseContext());
        KeyManager keyManager = flow.getStates();
        State state = keyManager.getState(Flow.getKey(getBaseContext()));
        state.setBundle(mainPresenter.toBundle());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void openDrawer() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void closeDrawer() {
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void setTitle(String title) {
        toolbarText.setText(title);
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback callback) {
        injectServices();

        Object newKey = DispatcherUtils.getNewKey(traversal);
        mainPresenter.setTitle(getString(annotationCache.getTitle(newKey)));
        boolean isLeftDrawerEnabled = annotationCache.getLeftDrawerEnabled(newKey);
        if(isLeftDrawerEnabled) {
            enableLeftDrawer();
        } else {
            disableLeftDrawer();
        }

        boolean isToolbarButtonVisible = annotationCache.getToolbarButtonVisibility(newKey);
        Set<Class<?>> parents = annotationCache.getChildOf(newKey);
        if(parents.size() > 0) {
            drawerToggle.setVisibility(View.GONE);
            toolbarGoPrevious.setVisibility(View.VISIBLE);
        } else {
            if(isToolbarButtonVisible) {
                drawerToggle.setVisibility(View.VISIBLE);
            } else {
                drawerToggle.setVisibility(View.GONE);
            }
            toolbarGoPrevious.setVisibility(View.GONE);
        }

        transitionDispatcher.dispatch(traversal, callback);
    }

    @Override
    public Object getSystemService(String name) {
        try {
            Flow flow = Flow.get(getBaseContext());
            MainKey mainKey = Flow.getKey(getBaseContext());
            if(flow.getServices().hasService(mainKey, name)) {
                return flow.getServices().getService(mainKey, name);
            }
        } catch(IllegalStateException e) { // Flow might not exist at this point
        }
        return super.getSystemService(name);
    }
}
