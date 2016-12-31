package com.zhuinden.examplegithubclient.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.transitionseverywhere.TransitionManager;

import flowless.ServiceProvider;
import flowless.Traversal;
import flowless.TraversalCallback;
import flowless.preset.DispatcherUtils;
import flowless.preset.SingleRootDispatcher;

/**
 * Created by Zhuinden on 2016.12.02..
 */

public class TransitionDispatcher extends SingleRootDispatcher {
    public TransitionDispatcher(Activity activity) {
        super(activity);
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback callback) {
        if(DispatcherUtils.isPreviousKeySameAsNewKey(traversal.origin, traversal.destination)) { //short circuit on same key
            callback.onTraversalCompleted();
            return;
        }
        final Object newKey = DispatcherUtils.getNewKey(traversal);
        AnnotationCache annotationCache = AnnotationCache.getCache(activity.getBaseContext());
        int newKeyLayout = annotationCache.getLayout(newKey);

        final ViewGroup root = rootHolder.getRoot();
        final View previousView = root.getChildAt(0);
        DispatcherUtils.persistViewToStateAndNotifyRemoval(traversal, previousView);

        ServiceProvider serviceProvider = ServiceProvider.get(baseContext);
        if(traversal.origin != null) {
            for(Object key : traversal.origin) { // retain only current key's services
                if(!newKey.equals(key)) {
                    serviceProvider.unbindServices(key);
                }
            }
        }

        for(Object key : traversal.destination) { // retain only current key's services
            if(!newKey.equals(key)) {
                serviceProvider.unbindServices(key);
            } else {
                if(!serviceProvider.hasService(key, DaggerService.TAG)) {
                    ComponentFactory.FactoryMethod<?> componentFactory = annotationCache.getComponentFactory(key);
                    if(componentFactory != null) {
                        serviceProvider.bindService(key, DaggerService.TAG, componentFactory.createComponent(baseContext));
                    }
                }
            }
        }
        Context internalContext = traversal.createContext(newKey, baseContext);

        LayoutInflater layoutInflater = LayoutInflater.from(internalContext);
        final View newView = layoutInflater.inflate(newKeyLayout, root, false);

        DispatcherUtils.restoreViewFromState(traversal, newView);

        TransitionManager.beginDelayedTransition(root);
        if(previousView != null) {
            root.removeView(previousView);
        }
        root.addView(newView);
        callback.onTraversalCompleted();
    }
}