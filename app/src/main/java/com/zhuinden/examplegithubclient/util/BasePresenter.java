package com.zhuinden.examplegithubclient.util;

/**
 * Created by Owner on 2016.12.10.
 */

public abstract class BasePresenter<V extends Presenter.ViewContract>
        implements Presenter<V> {
    protected V view;

    protected final boolean hasView() {
        return view != null;
    }

    public V getView() {
        return view;
    }

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
