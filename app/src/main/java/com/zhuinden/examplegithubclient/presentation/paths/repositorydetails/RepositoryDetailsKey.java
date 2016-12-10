package com.zhuinden.examplegithubclient.presentation.paths.repositorydetails;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.util.Layout;
import com.zhuinden.examplegithubclient.util.Title;

/**
 * Created by Zhuinden on 2016.12.10..
 */

@AutoValue
@Title(R.string.title_repository_details)
@Layout(R.layout.path_repositorydetails)
public abstract class RepositoryDetailsKey
        implements Parcelable {
    public static RepositoryDetailsKey create() {
        return new AutoValue_RepositoryDetailsKey();
    }
}
