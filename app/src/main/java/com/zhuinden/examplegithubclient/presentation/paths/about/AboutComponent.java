package com.zhuinden.examplegithubclient.presentation.paths.about;

import com.zhuinden.examplegithubclient.application.injection.KeyScope;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainComponent;

import dagger.Component;

/**
 * Created by Owner on 2016.12.10.
 */
@KeyScope(AboutKey.class)
@Component(dependencies = MainComponent.class)
public interface AboutComponent {
}
