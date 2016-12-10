package com.zhuinden.examplegithubclient.util;

import android.content.Context;

import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.application.injection.MainActivityComponent;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainActivity;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainKey;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Owner on 2016.12.10.
 */

@ActivityScope(MainActivity.class)
public class AnnotationCache {
    public static final String TAG = "AnnotationCache";

    @SuppressWarnings("unchecked")
    public static AnnotationCache getCache(Context context) {
        //noinspection ResourceType
        MainActivityComponent mainActivityComponent = DaggerService.getComponent(context, MainKey.KEY);
        return mainActivityComponent.annotationCache();
    }

    @Inject
    public AnnotationCache() {
    }

    // from flow-sample: https://github.com/Zhuinden/flow-sample/blob/master/src/main/java/com/example/flow/pathview/SimplePathContainer.java#L100-L114
    private final Map<Class, Integer> PATH_LAYOUT_CACHE = new LinkedHashMap<>();

    public int getLayout(Object path) {
        Class pathType = path.getClass();
        Integer layoutResId = PATH_LAYOUT_CACHE.get(pathType);
        if(layoutResId == null) {
            Layout layout = (Layout) pathType.getAnnotation(Layout.class);
            if(layout == null) {
                throw new IllegalArgumentException(String.format("@%s annotation not found on class %s",
                        Layout.class.getSimpleName(),
                        pathType.getName()));
            }
            layoutResId = layout.value();
            PATH_LAYOUT_CACHE.put(pathType, layoutResId);
        }
        return layoutResId;
    }

    // from flow-sample: https://github.com/Zhuinden/flow-sample/blob/master/src/main/java/com/example/flow/pathview/SimplePathContainer.java#L100-L114
    private static final Map<Class, Integer> PATH_TITLE_CACHE = new LinkedHashMap<>();

    public int getTitle(Object path) {
        Class pathType = path.getClass();
        Integer titleResId = PATH_TITLE_CACHE.get(pathType);
        if(titleResId == null) {
            Title title = (Title) pathType.getAnnotation(Title.class);
            if(title == null) {
                throw new IllegalArgumentException(String.format("@%s annotation not found on class %s",
                        Title.class.getSimpleName(),
                        pathType.getName()));
            }
            titleResId = title.value();
            PATH_TITLE_CACHE.put(pathType, titleResId);
        }
        return titleResId;
    }
}
