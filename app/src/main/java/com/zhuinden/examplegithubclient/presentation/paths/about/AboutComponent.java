package com.zhuinden.examplegithubclient.presentation.paths.about;

import com.zhuinden.examplegithubclient.application.injection.MainActivityComponent;
import com.zhuinden.examplegithubclient.application.injection.ViewScope;

import dagger.Component;

/**
 * Created by Owner on 2016.12.10.
 */
@ViewScope(AboutView.class)
@Component(dependencies = MainActivityComponent.class)
public interface AboutComponent {
}
