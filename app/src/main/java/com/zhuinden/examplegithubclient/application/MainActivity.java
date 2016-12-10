package com.zhuinden.examplegithubclient.application;

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
import com.zhuinden.examplegithubclient.presentation.paths.login.LoginKey;
import com.zhuinden.examplegithubclient.util.Title;
import com.zhuinden.examplegithubclient.util.TransitionDispatcher;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flowless.Dispatcher;
import flowless.Flow;
import flowless.Traversal;
import flowless.TraversalCallback;
import flowless.preset.DispatcherUtils;

public class MainActivity
        extends AppCompatActivity
        implements Dispatcher {
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

    @OnClick(R.id.toolbar_drawer_toggle)
    public void onClickDrawerToggle() {
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    //            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    //            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
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
    public void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback callback) { // TODO: move this aspect of dispatcher to Presenter layer, Activity must not have state
        drawerLayout.closeDrawers();
        Object newKey = DispatcherUtils.getNewKey(traversal); // TODO: remove duplication in LeftDrawerItems
        toolbarText.setText(getTitle(newKey));
        transitionDispatcher.dispatch(traversal, callback);
    }

    // from flow-sample: https://github.com/Zhuinden/flow-sample/blob/master/src/main/java/com/example/flow/pathview/SimplePathContainer.java#L100-L114
    private static final Map<Class, Integer> PATH_TITLE_CACHE = new LinkedHashMap<>();

    protected int getTitle(Object path) { // TODO: remove duplication in LeftDrawerItems
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
