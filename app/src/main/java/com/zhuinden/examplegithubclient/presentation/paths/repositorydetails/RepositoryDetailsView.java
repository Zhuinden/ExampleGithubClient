package com.zhuinden.examplegithubclient.presentation.paths.repositorydetails;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Zhuinden on 2016.12.10..
 */

public class RepositoryDetailsView
        extends RelativeLayout {
    public RepositoryDetailsView(Context context) {
        super(context);
        init();
    }

    public RepositoryDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RepositoryDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public RepositoryDetailsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        if(!isInEditMode()) {
            // .
        }
    }
}
