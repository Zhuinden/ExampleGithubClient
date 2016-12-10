package com.zhuinden.examplegithubclient.util;

import android.content.Context;

import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.application.injection.MainActivityComponent;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainKey;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Owner on 2016.12.10.
 */

@ActivityScope
public class AnnotationCache {
    public static final String TAG = "AnnotationCache";

    public static AnnotationCache getCache(Context context) {
        MainActivityComponent mainActivityComponent = DaggerService.getComponent(context, MainKey.KEY);
        return mainActivityComponent.annotationCache();
    }

    @Inject
    public AnnotationCache() {
    }

    // from flow-sample: https://github.com/Zhuinden/flow-sample/blob/master/src/main/java/com/example/flow/pathview/SimplePathContainer.java#L100-L114
    private final Map<Class, Integer> PATH_LAYOUT_CACHE = new LinkedHashMap<>();
    private final Map<Class, Integer> PATH_TITLE_CACHE = new LinkedHashMap<>();
    private final Map<Class, Class<? extends ComponentFactory.FactoryMethod<?>>> PATH_COMPONENT_FACTORY_CACHE = new LinkedHashMap<>();

    private <T, A> T getValueFromAnnotation(Map<Class, T> cache, Object path, Class<A> annotationClass) {
        Class pathType = path.getClass();
        T value = cache.get(pathType);
        if(value == null) {
            A annotation = (A) pathType.getAnnotation(annotationClass);
            if(annotation == null) {
                throw new IllegalArgumentException(String.format("@%s annotation not found on class %s",
                        annotationClass.getSimpleName(),
                        pathType.getName()));
            }
            try {
                value = (T) annotationClass.getMethod("value").invoke(annotation);
            } catch(IllegalAccessException e) {
                // shouldn't happen
                e.printStackTrace();
            } catch(InvocationTargetException e) {
                // shouldn't happen
                e.printStackTrace();
            } catch(NoSuchMethodException e) {
                // shouldn't happen
                e.printStackTrace();
            }
            cache.put(pathType, value);
        }
        return value;
    }

    // from flow-sample: https://github.com/Zhuinden/flow-sample/blob/master/src/main/java/com/example/flow/pathview/SimplePathContainer.java#L100-L114
    public int getLayout(Object path) {
        return getValueFromAnnotation(PATH_LAYOUT_CACHE, path, Layout.class);
    }

    public int getTitle(Object path) {
        return getValueFromAnnotation(PATH_TITLE_CACHE, path, Title.class);
    }

    public ComponentFactory.FactoryMethod<?> getComponentFactory(Object path) {
        Class pathType = path.getClass();
        Class<? extends ComponentFactory.FactoryMethod<?>> value = PATH_COMPONENT_FACTORY_CACHE.get(pathType);
        if(value == null) {
            ComponentFactory annotation = (ComponentFactory) pathType.getAnnotation(ComponentFactory.class);
            if(annotation == null) {
                return null;
            }
            try {
                value = (Class<ComponentFactory.FactoryMethod<?>>) annotation.getClass().getMethod("value").invoke(annotation);
                PATH_COMPONENT_FACTORY_CACHE.put(pathType, value);
            } catch(IllegalAccessException e) {
                // shouldn't happen
                e.printStackTrace();
            } catch(InvocationTargetException e) {
                // shouldn't happen
                e.printStackTrace();
            } catch(NoSuchMethodException e) {
                // shouldn't happen
                e.printStackTrace();
            }
        }
        try {
            if(value != null) {
                return value.newInstance();
            }
        } catch(InstantiationException e) {
            // shouldn't happen
            e.printStackTrace();
        } catch(IllegalAccessException e) {
            // shouldn't happen
            e.printStackTrace();
        }
        return null;
    }
}
