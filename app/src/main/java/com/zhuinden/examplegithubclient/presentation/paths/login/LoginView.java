package com.zhuinden.examplegithubclient.presentation.paths.login;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.presentation.paths.repositories.RepositoriesKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flowless.Flow;

/**
 * Created by Zhuinden on 2016.12.10..
 */

public class LoginView
        extends RelativeLayout {
    public LoginView(Context context) {
        super(context);
        init();
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public LoginView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        if(!isInEditMode()) {
            // .
        }
    }

    @BindView(R.id.login_username)
    TextView username;

    @BindView(R.id.login_password)
    TextView password;

    @OnClick(R.id.login_login)
    public void login(View view) {
        // TODO: add actual logic
        Flow.get(this).set(RepositoriesKey.create()); // TODO: move to presenter/routing layer
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(!isInEditMode()) {
            ButterKnife.bind(this);
        }
    }
}
