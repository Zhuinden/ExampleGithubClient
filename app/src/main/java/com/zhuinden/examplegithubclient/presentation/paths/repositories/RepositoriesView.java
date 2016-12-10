package com.zhuinden.examplegithubclient.presentation.paths.repositories;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Zhuinden on 2016.12.10..
 */

public class RepositoriesView
        extends RelativeLayout {
    public RepositoriesView(Context context) {
        super(context);
        init();
    }

    public RepositoriesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RepositoriesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public RepositoriesView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        if(!isInEditMode()) {
            // .
        }
    }
}
