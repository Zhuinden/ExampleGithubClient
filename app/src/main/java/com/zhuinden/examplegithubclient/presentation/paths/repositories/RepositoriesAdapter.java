package com.zhuinden.examplegithubclient.presentation.paths.repositories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;
import com.zhuinden.examplegithubclient.util.DaggerService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Zhuinden on 2016.12.18..
 */

public class RepositoriesAdapter
        extends RecyclerView.Adapter<RepositoriesAdapter.ViewHolder> {
    @Inject
    RepositoriesPresenter repositoriesPresenter;

    public RepositoriesAdapter(Context context) {
        RepositoriesComponent repositoriesComponent = DaggerService.getComponent(context);
        repositoriesComponent.inject(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_repositories_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(repositoriesPresenter.getRepositories().get(position));
    }

    @Override
    public int getItemCount() {
        return repositoriesPresenter.getRepositories() == null ? 0 : repositoriesPresenter.getRepositories().size();
    }

    public void updateRepositories() {
        notifyDataSetChanged();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {
        @Inject
        RepositoriesPresenter repositoriesPresenter;

        @BindView(R.id.repositories_row_text)
        TextView row;

        private Repository repository;

        @OnClick(R.id.repositories_row)
        public void rowClicked() {
            repositoriesPresenter.openRepository(repository);
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            RepositoriesComponent repositoriesComponent = DaggerService.getComponent(itemView.getContext());
            repositoriesComponent.inject(this);
        }

        public void bind(Repository repository) {
            this.repository = repository;
            row.setText(repository.getName());
        }
    }
}
