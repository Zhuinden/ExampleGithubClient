package com.zhuinden.examplegithubclient.presentation.activity.main;

import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.google.auto.value.AutoValue;

/**
 * Created by Zhuinden on 2017.01.18..
 */

@AutoValue
public abstract class MainState
        implements Parcelable {
    @StringRes
    public abstract int title();

    public abstract boolean isDrawerOpen();

    public abstract boolean toolbarGoPreviousVisible();

    public abstract boolean drawerToggleVisible();

    public abstract boolean leftDrawerEnabled();

    public Builder toBuilder() {
        return new AutoValue_MainState.Builder(this);
    }

    public static MainState create() {
        return new AutoValue_MainState(0, false, false, false, false);
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setTitle(@StringRes int value);

        abstract Builder setIsDrawerOpen(boolean value);

        abstract Builder setToolbarGoPreviousVisible(boolean value);

        abstract Builder setDrawerToggleVisible(boolean value);

        abstract Builder setLeftDrawerEnabled(boolean value);

        abstract MainState build();
    }
}
