package com.zhuinden.examplegithubclient.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.transitionseverywhere.TransitionManager;

import flowless.Flow;
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

        Flow flow = Flow.get(baseContext);
        for(Object key : traversal.destination) { // retain only current key's services
            if(!newKey.equals(key)) {
                flow.getServices().unbindServices(key);
            }
        }
        Context internalContext = traversal.createContext(newKey, baseContext);
        if(!flow.getServices().hasService(newKey, DaggerService.TAG)) {
            ComponentFactory.FactoryMethod<?> componentFactory = annotationCache.getComponentFactory(newKey);
            if(componentFactory != null) {
                flow.getServices().bindService(newKey, DaggerService.TAG, componentFactory.createComponent(baseContext));
            }
        }
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