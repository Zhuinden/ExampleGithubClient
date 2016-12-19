package com.zhuinden.examplegithubclient.presentation.paths.repositorydetails;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;
import com.zhuinden.examplegithubclient.presentation.paths.repositories.RepositoriesComponent;
import com.zhuinden.examplegithubclient.presentation.paths.repositories.RepositoriesPresenter;
import com.zhuinden.examplegithubclient.util.DaggerService;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import flowless.Flow;
import flowless.ServiceProvider;
import flowless.preset.FlowLifecycles;

/**
 * Created by Zhuinden on 2016.12.10..
 */

public class RepositoryDetailsView
        extends RelativeLayout
        implements FlowLifecycles.ViewLifecycleListener, RepositoryDetailsPresenter.ViewContract {
    public RepositoryDetailsView(Context context) {
        super(context);
        init();
    }

    public RepositoryDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RepositoryDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public RepositoryDetailsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Inject
    RepositoryDetailsPresenter repositoryDetailsPresenter;

    Repository selectedRepository;

    public void init() {
        if(!isInEditMode()) {
            RepositoryDetailsComponent repositoryDetailsComponent = DaggerService.getComponent(getContext());
            repositoryDetailsComponent.inject(this);

            Flow flow = Flow.get(getContext());
            ServiceProvider serviceProvider = flow.getServices();
            RepositoryDetailsKey repositoryDetailsKey = Flow.getKey(this);
            RepositoriesComponent repositoriesComponent = DaggerService.getComponent(getContext(), repositoryDetailsKey.parent());
            RepositoriesPresenter repositoriesPresenter = repositoriesComponent.repositoriesPresenter();
            List<Repository> repositories = repositoriesPresenter.getRepositories();
            if(repositories != null) {
                for(Repository repository : repositories) {
                    if(repository.getUrl().equals(repositoryDetailsKey.url())) {
                        selectedRepository = repository;
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void onViewRestored() {
        repositoryDetailsPresenter.attachView(this);
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        repositoryDetailsPresenter.detachView();
    }

    @Override
    public Repository getSelectedRepository() {
        return selectedRepository;
    }


    @BindView(R.id.repository_details_id)
    TextView repositoryDetailsid;

    @BindView(R.id.repository_details_name)
    TextView repositoryDetailsname;

    @BindView(R.id.repository_details_fullName)
    TextView repositoryDetailsfullName;

    @BindView(R.id.repository_details_owner)
    TextView repositoryDetailsowner;

    @BindView(R.id.repository_details__private)
    TextView repositoryDetails_private;

    @BindView(R.id.repository_details_htmlUrl)
    TextView repositoryDetailshtmlUrl;

    @BindView(R.id.repository_details_description)
    TextView repositoryDetailsdescription;

    @BindView(R.id.repository_details_fork)
    TextView repositoryDetailsfork;

    @BindView(R.id.repository_details_url)
    TextView repositoryDetailsurl;

    @BindView(R.id.repository_details_forksUrl)
    TextView repositoryDetailsforksUrl;

    @BindView(R.id.repository_details_keysUrl)
    TextView repositoryDetailskeysUrl;

    @BindView(R.id.repository_details_collaboratorsUrl)
    TextView repositoryDetailscollaboratorsUrl;

    @BindView(R.id.repository_details_teamsUrl)
    TextView repositoryDetailsteamsUrl;

    @BindView(R.id.repository_details_hooksUrl)
    TextView repositoryDetailshooksUrl;

    @BindView(R.id.repository_details_issueEventsUrl)
    TextView repositoryDetailsissueEventsUrl;

    @BindView(R.id.repository_details_eventsUrl)
    TextView repositoryDetailseventsUrl;

    @BindView(R.id.repository_details_assigneesUrl)
    TextView repositoryDetailsassigneesUrl;

    @BindView(R.id.repository_details_branchesUrl)
    TextView repositoryDetailsbranchesUrl;

    @BindView(R.id.repository_details_tagsUrl)
    TextView repositoryDetailstagsUrl;

    @BindView(R.id.repository_details_blobsUrl)
    TextView repositoryDetailsblobsUrl;

    @BindView(R.id.repository_details_gitTagsUrl)
    TextView repositoryDetailsgitTagsUrl;

    @BindView(R.id.repository_details_gitRefsUrl)
    TextView repositoryDetailsgitRefsUrl;

    @BindView(R.id.repository_details_treesUrl)
    TextView repositoryDetailstreesUrl;

    @BindView(R.id.repository_details_statusesUrl)
    TextView repositoryDetailsstatusesUrl;

    @BindView(R.id.repository_details_languagesUrl)
    TextView repositoryDetailslanguagesUrl;

    @BindView(R.id.repository_details_stargazersUrl)
    TextView repositoryDetailsstargazersUrl;

    @BindView(R.id.repository_details_contributorsUrl)
    TextView repositoryDetailscontributorsUrl;

    @BindView(R.id.repository_details_subscribersUrl)
    TextView repositoryDetailssubscribersUrl;

    @BindView(R.id.repository_details_subscriptionUrl)
    TextView repositoryDetailssubscriptionUrl;

    @BindView(R.id.repository_details_commitsUrl)
    TextView repositoryDetailscommitsUrl;

    @BindView(R.id.repository_details_gitCommitsUrl)
    TextView repositoryDetailsgitCommitsUrl;

    @BindView(R.id.repository_details_commentsUrl)
    TextView repositoryDetailscommentsUrl;

    @BindView(R.id.repository_details_issueCommentUrl)
    TextView repositoryDetailsissueCommentUrl;

    @BindView(R.id.repository_details_contentsUrl)
    TextView repositoryDetailscontentsUrl;

    @BindView(R.id.repository_details_compareUrl)
    TextView repositoryDetailscompareUrl;

    @BindView(R.id.repository_details_mergesUrl)
    TextView repositoryDetailsmergesUrl;

    @BindView(R.id.repository_details_archiveUrl)
    TextView repositoryDetailsarchiveUrl;

    @BindView(R.id.repository_details_downloadsUrl)
    TextView repositoryDetailsdownloadsUrl;

    @BindView(R.id.repository_details_issuesUrl)
    TextView repositoryDetailsissuesUrl;

    @BindView(R.id.repository_details_pullsUrl)
    TextView repositoryDetailspullsUrl;

    @BindView(R.id.repository_details_milestonesUrl)
    TextView repositoryDetailsmilestonesUrl;

    @BindView(R.id.repository_details_notificationsUrl)
    TextView repositoryDetailsnotificationsUrl;

    @BindView(R.id.repository_details_labelsUrl)
    TextView repositoryDetailslabelsUrl;

    @BindView(R.id.repository_details_releasesUrl)
    TextView repositoryDetailsreleasesUrl;

    @BindView(R.id.repository_details_deploymentsUrl)
    TextView repositoryDetailsdeploymentsUrl;

    @BindView(R.id.repository_details_createdAt)
    TextView repositoryDetailscreatedAt;

    @BindView(R.id.repository_details_updatedAt)
    TextView repositoryDetailsupdatedAt;

    @BindView(R.id.repository_details_pushedAt)
    TextView repositoryDetailspushedAt;

    @BindView(R.id.repository_details_gitUrl)
    TextView repositoryDetailsgitUrl;

    @BindView(R.id.repository_details_sshUrl)
    TextView repositoryDetailssshUrl;

    @BindView(R.id.repository_details_cloneUrl)
    TextView repositoryDetailscloneUrl;

    @BindView(R.id.repository_details_svnUrl)
    TextView repositoryDetailssvnUrl;

    @BindView(R.id.repository_details_homepage)
    TextView repositoryDetailshomepage;

    @BindView(R.id.repository_details_size)
    TextView repositoryDetailssize;

    @BindView(R.id.repository_details_stargazersCount)
    TextView repositoryDetailsstargazersCount;

    @BindView(R.id.repository_details_watchersCount)
    TextView repositoryDetailswatchersCount;

    @BindView(R.id.repository_details_language)
    TextView repositoryDetailslanguage;

    @BindView(R.id.repository_details_hasIssues)
    TextView repositoryDetailshasIssues;

    @BindView(R.id.repository_details_hasDownloads)
    TextView repositoryDetailshasDownloads;

    @BindView(R.id.repository_details_hasWiki)
    TextView repositoryDetailshasWiki;

    @BindView(R.id.repository_details_hasPages)
    TextView repositoryDetailshasPages;

    @BindView(R.id.repository_details_forksCount)
    TextView repositoryDetailsforksCount;

    @BindView(R.id.repository_details_mirrorUrl)
    TextView repositoryDetailsmirrorUrl;

    @BindView(R.id.repository_details_openIssuesCount)
    TextView repositoryDetailsopenIssuesCount;

    @BindView(R.id.repository_details_forks)
    TextView repositoryDetailsforks;

    @BindView(R.id.repository_details_openIssues)
    TextView repositoryDetailsopenIssues;

    @BindView(R.id.repository_details_watchers)
    TextView repositoryDetailswatchers;

    @BindView(R.id.repository_details_defaultBranch)
    TextView repositoryDetailsdefaultBranch;

    @Override
    public void setupView(Repository repository) {
        repositoryDetailsid.setText("Id: " + String.valueOf(repository.getId()));
        repositoryDetailsname.setText("Name: " + String.valueOf(repository.getName()));
        repositoryDetailsfullName.setText("FullName: " + String.valueOf(repository.getFullName()));
        repositoryDetailsowner.setText("Owner: " + (repository.getOwner() == null ? "" : repository.getOwner().getLogin()));
        repositoryDetails_private.setText("_Private: " + String.valueOf(repository.get_Private()));
        repositoryDetailshtmlUrl.setText("HtmlUrl: " + String.valueOf(repository.getHtmlUrl()));
        repositoryDetailsdescription.setText("Description: " + String.valueOf(repository.getDescription()));
        repositoryDetailsfork.setText("Fork: " + String.valueOf(repository.getFork()));
        repositoryDetailsurl.setText("Url: " + String.valueOf(repository.getUrl()));
        repositoryDetailsforksUrl.setText("ForksUrl: " + String.valueOf(repository.getForksUrl()));
        repositoryDetailskeysUrl.setText("KeysUrl: " + String.valueOf(repository.getKeysUrl()));
        repositoryDetailscollaboratorsUrl.setText("CollaboratorsUrl: " + String.valueOf(repository.getCollaboratorsUrl()));
        repositoryDetailsteamsUrl.setText("TeamsUrl: " + String.valueOf(repository.getTeamsUrl()));
        repositoryDetailshooksUrl.setText("HooksUrl: " + String.valueOf(repository.getHooksUrl()));
        repositoryDetailsissueEventsUrl.setText("IssueEventsUrl: " + String.valueOf(repository.getIssueEventsUrl()));
        repositoryDetailseventsUrl.setText("EventsUrl: " + String.valueOf(repository.getEventsUrl()));
        repositoryDetailsassigneesUrl.setText("AssigneesUrl: " + String.valueOf(repository.getAssigneesUrl()));
        repositoryDetailsbranchesUrl.setText("BranchesUrl: " + String.valueOf(repository.getBranchesUrl()));
        repositoryDetailstagsUrl.setText("TagsUrl: " + String.valueOf(repository.getTagsUrl()));
        repositoryDetailsblobsUrl.setText("BlobsUrl: " + String.valueOf(repository.getBlobsUrl()));
        repositoryDetailsgitTagsUrl.setText("GitTagsUrl: " + String.valueOf(repository.getGitTagsUrl()));
        repositoryDetailsgitRefsUrl.setText("GitRefsUrl: " + String.valueOf(repository.getGitRefsUrl()));
        repositoryDetailstreesUrl.setText("TreesUrl: " + String.valueOf(repository.getTreesUrl()));
        repositoryDetailsstatusesUrl.setText("StatusesUrl: " + String.valueOf(repository.getStatusesUrl()));
        repositoryDetailslanguagesUrl.setText("LanguagesUrl: " + String.valueOf(repository.getLanguagesUrl()));
        repositoryDetailsstargazersUrl.setText("StargazersUrl: " + String.valueOf(repository.getStargazersUrl()));
        repositoryDetailscontributorsUrl.setText("ContributorsUrl: " + String.valueOf(repository.getContributorsUrl()));
        repositoryDetailssubscribersUrl.setText("SubscribersUrl: " + String.valueOf(repository.getSubscribersUrl()));
        repositoryDetailssubscriptionUrl.setText("SubscriptionUrl: " + String.valueOf(repository.getSubscriptionUrl()));
        repositoryDetailscommitsUrl.setText("CommitsUrl: " + String.valueOf(repository.getCommitsUrl()));
        repositoryDetailsgitCommitsUrl.setText("GitCommitsUrl: " + String.valueOf(repository.getGitCommitsUrl()));
        repositoryDetailscommentsUrl.setText("CommentsUrl: " + String.valueOf(repository.getCommentsUrl()));
        repositoryDetailsissueCommentUrl.setText("IssueCommentUrl: " + String.valueOf(repository.getIssueCommentUrl()));
        repositoryDetailscontentsUrl.setText("ContentsUrl: " + String.valueOf(repository.getContentsUrl()));
        repositoryDetailscompareUrl.setText("CompareUrl: " + String.valueOf(repository.getCompareUrl()));
        repositoryDetailsmergesUrl.setText("MergesUrl: " + String.valueOf(repository.getMergesUrl()));
        repositoryDetailsarchiveUrl.setText("ArchiveUrl: " + String.valueOf(repository.getArchiveUrl()));
        repositoryDetailsdownloadsUrl.setText("DownloadsUrl: " + String.valueOf(repository.getDownloadsUrl()));
        repositoryDetailsissuesUrl.setText("IssuesUrl: " + String.valueOf(repository.getIssuesUrl()));
        repositoryDetailspullsUrl.setText("PullsUrl: " + String.valueOf(repository.getPullsUrl()));
        repositoryDetailsmilestonesUrl.setText("MilestonesUrl: " + String.valueOf(repository.getMilestonesUrl()));
        repositoryDetailsnotificationsUrl.setText("NotificationsUrl: " + String.valueOf(repository.getNotificationsUrl()));
        repositoryDetailslabelsUrl.setText("LabelsUrl: " + String.valueOf(repository.getLabelsUrl()));
        repositoryDetailsreleasesUrl.setText("ReleasesUrl: " + String.valueOf(repository.getReleasesUrl()));
        repositoryDetailsdeploymentsUrl.setText("DeploymentsUrl: " + String.valueOf(repository.getDeploymentsUrl()));
        repositoryDetailscreatedAt.setText("CreatedAt: " + String.valueOf(repository.getCreatedAt()));
        repositoryDetailsupdatedAt.setText("UpdatedAt: " + String.valueOf(repository.getUpdatedAt()));
        repositoryDetailspushedAt.setText("PushedAt: " + String.valueOf(repository.getPushedAt()));
        repositoryDetailsgitUrl.setText("GitUrl: " + String.valueOf(repository.getGitUrl()));
        repositoryDetailssshUrl.setText("SshUrl: " + String.valueOf(repository.getSshUrl()));
        repositoryDetailscloneUrl.setText("CloneUrl: " + String.valueOf(repository.getCloneUrl()));
        repositoryDetailssvnUrl.setText("SvnUrl: " + String.valueOf(repository.getSvnUrl()));
        repositoryDetailshomepage.setText("Homepage: " + String.valueOf(repository.getHomepage()));
        repositoryDetailssize.setText("Size: " + String.valueOf(repository.getSize()));
        repositoryDetailsstargazersCount.setText("StargazersCount: " + String.valueOf(repository.getStargazersCount()));
        repositoryDetailswatchersCount.setText("WatchersCount: " + String.valueOf(repository.getWatchersCount()));
        repositoryDetailslanguage.setText("Language: " + String.valueOf(repository.getLanguage()));
        repositoryDetailshasIssues.setText("HasIssues: " + String.valueOf(repository.getHasIssues()));
        repositoryDetailshasDownloads.setText("HasDownloads: " + String.valueOf(repository.getHasDownloads()));
        repositoryDetailshasWiki.setText("HasWiki: " + String.valueOf(repository.getHasWiki()));
        repositoryDetailshasPages.setText("HasPages: " + String.valueOf(repository.getHasPages()));
        repositoryDetailsforksCount.setText("ForksCount: " + String.valueOf(repository.getForksCount()));
        repositoryDetailsmirrorUrl.setText("MirrorUrl: " + String.valueOf(repository.getMirrorUrl()));
        repositoryDetailsopenIssuesCount.setText("OpenIssuesCount: " + String.valueOf(repository.getOpenIssuesCount()));
        repositoryDetailsforks.setText("Forks: " + String.valueOf(repository.getForks()));
        repositoryDetailsopenIssues.setText("OpenIssues: " + String.valueOf(repository.getOpenIssues()));
        repositoryDetailswatchers.setText("Watchers: " + String.valueOf(repository.getWatchers()));
        repositoryDetailsdefaultBranch.setText("DefaultBranch: " + String.valueOf(repository.getDefaultBranch()));
    }   
}
