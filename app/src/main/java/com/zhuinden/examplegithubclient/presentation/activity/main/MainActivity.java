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
import com.zhuinden.examplegithubclient.util.DaggerService;
import com.zhuinden.examplegithubclient.util.Title;
import com.zhuinden.examplegithubclient.util.TransitionDispatcher;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flowless.Dispatcher;
import flowless.Flow;
import flowless.ServiceProvider;
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
                .dispatcher(this) //
                .install(); //
        transitionDispatcher.setBaseContext(newBase);
        super.attachBaseContext(newBase);
    }

    private Bundle tempSavedInstanceState;
    private boolean didInject = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tempSavedInstanceState = savedInstanceState;
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

    private void injectServices(Bundle savedInstanceState) {
        if(didInject) {
            return;
        }
        didInject = true;
        if(savedInstanceState == null) { // need to get bundle between onPostCreate and after onCreate
            savedInstanceState = tempSavedInstanceState;
        }
        ServiceProvider serviceProvider = Flow.services(getBaseContext());
        MainActivityComponent mainActivityComponent;
        if(!serviceProvider.hasService(MainKey.KEY, DaggerService.TAG)) {
            mainActivityComponent = DaggerMainActivityComponent.create();
            serviceProvider.bindService(MainKey.KEY, DaggerService.TAG, mainActivityComponent);
        } else {
            mainActivityComponent = DaggerService.getComponent(getBaseContext(), MainKey.KEY);
        }
        mainActivityComponent.inject(this);
        if(savedInstanceState != null) {
            mainPresenter.fromBundle(savedInstanceState.getBundle("PRESENTER_STATE"));
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        tempSavedInstanceState = null;
        injectServices(savedInstanceState);
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
        super.onSaveInstanceState(outState);
        outState.putBundle("PRESENTER_STATE", mainPresenter.toBundle());
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
        injectServices(null);
        mainPresenter.closeDrawer();
        Object newKey = DispatcherUtils.getNewKey(traversal);
        mainPresenter.setTitle(getString(getTitle(newKey)));

        transitionDispatcher.dispatch(traversal, callback);
    }

    // TODO: merge with dispatcher's Layout logic
    // from flow-sample: https://github.com/Zhuinden/flow-sample/blob/master/src/main/java/com/example/flow/pathview/SimplePathContainer.java#L100-L114
    private static final Map<Class, Integer> PATH_TITLE_CACHE = new LinkedHashMap<>();

    protected int getTitle(Object path) {
        Class pathType = path.getClass();
        Integer titleResId = PATH_TITLE_CACHE.get(pathType);
        if(titleResId == null) {
            Title title = (Title) pathType.getAnnotation(Title.class);
            if(title == null) {
                throw new IllegalArgumentException(String.format("@%s annotation not found on class %s",
                        Title.class.getSimpleName(),
                        pathType.getName()));
            }
            titleResId = title.value();
            PATH_TITLE_CACHE.put(pathType, titleResId);
        }
        return titleResId;
    }
}
