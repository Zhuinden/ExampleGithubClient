package com.zhuinden.examplegithubclient.presentation.paths.login;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.util.Layout;
import com.zhuinden.examplegithubclient.util.Title;


/**
 * Created by Zhuinden on 2016.12.10..
 */

@AutoValue
@Title(R.string.title_login)
@Layout(R.layout.path_login)
public abstract class LoginKey
        implements Parcelable {
    public static LoginKey create() {
        return new AutoValue_LoginKey();
    }
}
