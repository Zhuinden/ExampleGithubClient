package com.zhuinden.examplegithubclient.presentation.paths.login;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;

/**
 * Created by Zhuinden on 2016.12.19..
 */

public class LoginPage {
    public ViewInteraction loginView() {
        return Espresso.onView(ViewMatchers.isAssignableFrom(LoginView.class));
    }
}
