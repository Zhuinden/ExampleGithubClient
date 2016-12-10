package com.zhuinden.examplegithubclient.presentation.paths.repositories;

import android.content.Context;

import com.zhuinden.examplegithubclient.application.injection.MainActivityComponent;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainKey;
import com.zhuinden.examplegithubclient.util.ComponentFactory;
import com.zhuinden.examplegithubclient.util.DaggerService;

/**
 * Created by Owner on 2016.12.10.
 */

public class RepositoriesComponentFactory
        implements ComponentFactory.FactoryMethod<RepositoriesComponent> {
    @Override
    public RepositoriesComponent createComponent(Context context) {
        MainActivityComponent mainActivityComponent = DaggerService.getComponent(context, MainKey.KEY);
        return DaggerRepositoriesComponent.builder().mainActivityComponent(mainActivityComponent).build();
    }
}
