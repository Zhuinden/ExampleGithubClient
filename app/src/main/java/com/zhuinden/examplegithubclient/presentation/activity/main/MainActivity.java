package com.zhuinden.examplegithubclient.presentation.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.application.injection.DaggerMainActivityComponent;
import com.zhuinden.examplegithubclient.application.injection.MainActivityComponent;
import com.zhuinden.examplegithubclient.presentation.paths.login.LoginKey;
import com.zhuinden.examplegithubclient.util.AnnotationCache;
import com.zhuinden.examplegithubclient.util.DaggerService;
import com.zhuinden.examplegithubclient.util.TransitionDispatcher;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flowless.Dispatcher;
import flowless.Flow;
import flowless.KeyManager;
import flowless.ServiceProvider;
import flowless.State;
import flowless.Traversal;
import flowless.TraversalCallback;
import flowless.preset.DispatcherUtils;

public class MainActivity
        extends AppCompatActivity
        implements MainPresenter.ViewContract, Dispatcher {
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

    @Inject
    MainPresenter mainPresenter;

    @Inject
    AnnotationCache annotationCache;

    @OnClick(R.id.toolbar_drawer_toggle)
    public void onClickDrawerToggle() {
        mainPresenter.toggleDrawer();
    }

    // drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    // drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

    ActionBarDrawerToggle actionBarDrawerToggle;

    TransitionDispatcher transitionDispatcher;

    @Override
    protected void attachBaseContext(Context newBase) {
        transitionDispatcher = new TransitionDispatcher(this);
        newBase = Flow.configure(newBase, this) //
                .defaultKey(LoginKey.create()) //
                .globalKey(MainKey.KEY)
                .dispatcher(this) //
                .install(); //
        transitionDispatcher.setBaseContext(newBase);
        super.attachBaseContext(newBase);
    }

    private boolean didInject = false;

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
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                supportInvalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        //drawerLayout.setScrimColor(ContextCompat.getColor(this, android.R.color.transparent));
    }

    private void injectServices() {
        if(didInject) {
            return;
        }
        didInject = true;
        Flow flow = Flow.get(getBaseContext());
        ServiceProvider serviceProvider = flow.getServices();
        MainKey mainKey = Flow.getKey(getBaseContext());
        MainActivityComponent mainActivityComponent;
        if(!serviceProvider.hasService(mainKey, DaggerService.TAG)) {
            mainActivityComponent = DaggerMainActivityComponent.create();
            serviceProvider.bindService(mainKey, DaggerService.TAG, mainActivityComponent);
        } else {
            mainActivityComponent = DaggerService.getComponent(getBaseContext(), mainKey);
        }
        mainActivityComponent.inject(this);
        KeyManager keyManager = flow.getStates();
        State state = keyManager.getState(mainKey);
        mainPresenter.fromBundle(state.getBundle());
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
        if(!transitionDispatcher.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        transitionDispatcher.preSaveViewState();
        Flow flow = Flow.get(getBaseContext());
        KeyManager keyManager = flow.getStates();
        State state = keyManager.getState(Flow.getKey(getBaseContext()));
        state.setBundle(mainPresenter.toBundle());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        transitionDispatcher.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        transitionDispatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
