package com.zhuinden.examplegithubclient.presentation.paths.login;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.zhuinden.examplegithubclient.presentation.activity.main.MainActivity;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainPage;
import com.zhuinden.examplegithubclient.presentation.paths.repositories.RepositoriesView;
import com.zhuinden.examplegithubclient.util.DaggerService;
import com.zhuinden.examplegithubclient.util.FlowViewAssertions;
import com.zhuinden.examplegithubclient.util.conditionwatcher.ConditionWatcher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import flowless.Direction;
import flowless.Flow;
import flowless.History;
import flowless.ServiceProvider;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class,
            true,
            false);

    MainPage mainPage = new MainPage();
    LoginPage loginPage = new LoginPage();

    LoginPresenter loginPresenter;

    Flow flow;

    @Before
    public void setup() {
        mainActivityActivityTestRule.launchActivity(null);
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MainActivity mainActivity = mainActivityActivityTestRule.getActivity();
        instrumentation.runOnMainSync(() -> {
            Flow.get(mainActivity.getBaseContext()).setHistory(History.single(LoginKey.create()), Direction.REPLACE);
        });
        flow = Flow.get(mainActivity.getBaseContext());
        ServiceProvider serviceProvider = flow.getServices();
        LoginComponent loginComponent = serviceProvider.getService(LoginKey.create(), DaggerService.TAG);
        loginPresenter = loginComponent.loginPresenter();
    }

    @Test
    public void assertLoginViewIsActive() {
        mainPage.root().check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.isAssignableFrom(LoginView.class))));
    }

    @Test
    public void assertLoginViewHasLoginKey() {
        loginPage.loginView().check(FlowViewAssertions.hasKey(LoginKey.create()));
    }

    @Test
    public void assertGoesToRepositoryAfterSuccessfulLogin()
            throws Exception {
        loginPage.username().perform(ViewActions.typeText("hello"));
        loginPage.password().perform(ViewActions.typeText("world"));
        loginPage.loginButton().perform(ViewActions.click());
        ConditionWatcher.waitForCondition(new LoginWaitForDialogInstruction(loginPresenter));
        mainPage.root().check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.isAssignableFrom(RepositoriesView.class))));
    }
}
