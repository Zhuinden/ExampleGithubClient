package com.zhuinden.examplegithubclient.presentation.views.leftdrawer;

import android.support.annotation.DrawableRes;

import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.presentation.paths.about.AboutKey;
import com.zhuinden.examplegithubclient.presentation.paths.login.LoginKey;
import com.zhuinden.examplegithubclient.presentation.paths.repositories.RepositoriesKey;
import com.zhuinden.examplegithubclient.util.Title;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zhuinden on 2016.12.10..
 */

public enum LeftDrawerItems {
    REPOSITORIES(R.drawable.icon_repositories, RepositoriesKey::create),
    ABOUT(R.drawable.icon_about, AboutKey::create),
    LOGOUT(R.drawable.icon_logout, LoginKey::create);

    private static Map<LeftDrawerItems, Integer> TITLES = new HashMap<>();

    private final int imageId;

    private final KeyCreator keyCreator;

    private LeftDrawerItems(@DrawableRes int imageId, KeyCreator keyCreator) {
        this.imageId = imageId;
        this.keyCreator = keyCreator;
    }

    public int getLabelId() {
        if(!TITLES.containsKey(this)) {
            Object key = keyCreator.createKey();
            Class pathType = key.getClass();
            Integer titleResId = TITLES.get(pathType); // TODO: remove duplication versus TransitionDispatcher
            if(titleResId == null) {                  // TODO: remove duplication in MainActivity
                Title title = (Title) pathType.getAnnotation(Title.class);
                if(title == null) {
                    throw new IllegalArgumentException(String.format("@%s annotation not found on class %s",
                            Title.class.getSimpleName(),
                            pathType.getName()));
                }
                titleResId = title.value();
                TITLES.put(this, titleResId);
            }
            return titleResId;
        } else {
            return TITLES.get(this);
        }
    }

    public int getImageId() {
        return imageId;
    }

    public KeyCreator getKeyCreator() {
        return keyCreator;
    }

    static interface KeyCreator {
        public Object createKey();
    }
}
