package com.zhuinden.examplegithubclient.data.model;

import com.zhuinden.examplegithubclient.application.BoltsExecutors;
import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by Owner on 2016.12.26.
 */
@ActivityScope
public class RepositoryDataSource {
    @Inject
    public RepositoryDataSource() {
    }

    private List<Repository> repositories = Collections.synchronizedList(new ArrayList<>());

    public interface ChangeListener {
        void onChange(List<Repository> repositories);
    }

    public interface Unbinder {
        void unbind();
    }

    private Set<ChangeListener> changeListenerSet = new HashSet<>();

    public Unbinder registerChangeListener(final ChangeListener changeListener) {
        changeListenerSet.add(changeListener);
        changeListener.onChange(Collections.unmodifiableList(repositories));
        return () -> changeListenerSet.remove(changeListener);
    }

    private void notifyChangeListeners() {
        List<Repository> unmodifiable = Collections.unmodifiableList(repositories);
        for(ChangeListener changeListener : changeListenerSet) {
            changeListener.onChange(unmodifiable);
        }
    }

    public void addRepository(Repository repository) {
        repositories.add(repository);
        BoltsExecutors.UI_THREAD.execute(this::notifyChangeListeners);
    }

    public void addRepositories(List<Repository> repositories) {
        this.repositories.addAll(repositories);
        BoltsExecutors.UI_THREAD.execute(this::notifyChangeListeners);
    }

    public Repository findByUrl(String url) { // TODO: should be in Repository layer
        if(repositories != null) {
            for(Repository repository : repositories) {
                if(repository.getUrl().equals(url)) {
                    return repository;
                }
            }
        }
        return null;
    }
}
