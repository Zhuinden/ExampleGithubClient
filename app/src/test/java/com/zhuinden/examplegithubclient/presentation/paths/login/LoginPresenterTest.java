package com.zhuinden.examplegithubclient.presentation.paths.login;

import com.zhuinden.examplegithubclient.application.BoltsExecutors;
import com.zhuinden.examplegithubclient.domain.interactor.LoginInteractor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.concurrent.Executor;

import bolts.Task;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Zhuinden on 2016.12.19..
 */
public class LoginPresenterTest {
    LoginPresenter loginPresenter;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    LoginPresenter.ViewContract viewContract;

    @Mock
    LoginInteractor loginInteractor;

    @Before
    public void init() {
        loginPresenter = new LoginPresenter();
        Executor executor = Runnable::run;
        BoltsExecutors.UI_THREAD = executor;
        BoltsExecutors.BACKGROUND_THREAD = executor;
    }

    @Test
    public void initializeViewWhileLoading()
            throws Exception {
        // given
        loginPresenter.username = "Hello";
        loginPresenter.password = "World";
        LoginPresenter.isLoading = true;

        // when
        loginPresenter.attachView(viewContract);

        // then
        Mockito.verify(viewContract).setUsername("Hello");
        Mockito.verify(viewContract).setPassword("World");
        Mockito.verify(viewContract).showLoading();
    }

    @Test
    public void initializeViewWhileNotLoading()
            throws Exception {
        // given
        loginPresenter.username = "Hello";
        loginPresenter.password = "World";
        LoginPresenter.isLoading = false;

        // when
        loginPresenter.attachView(viewContract);

        // then
        Mockito.verify(viewContract).setUsername("Hello");
        Mockito.verify(viewContract).setPassword("World");
        Mockito.verify(viewContract).hideLoading();
    }

    @Test
    public void updateUsername()
            throws Exception {
        // given
        loginPresenter.username = "";

        // when
        loginPresenter.updateUsername("Hello");

        // then
        assertThat(loginPresenter.username).isEqualTo("Hello");
    }

    @Test
    public void updatePassword()
            throws Exception {
        // given
        loginPresenter.password = "";

        // when
        loginPresenter.updatePassword("World");

        // then
        assertThat(loginPresenter.password).isEqualTo("World");
    }

    @Test
    public void loginCallsInteractor()
            throws Exception {
        loginPresenter.attachView(viewContract);
        // given
        loginPresenter.username = "Hello";
        loginPresenter.password = "World";
        loginPresenter.loginInteractor = loginInteractor;

        Task<Boolean> task = Mockito.mock(Task.class);
        Mockito.when(loginInteractor.login("Hello", "World")).thenReturn(task);

        // when
        loginPresenter.login();

        // then
        Mockito.verify(loginInteractor).login("Hello", "World");
    }

    @Test
    public void loginSuccess()
            throws Exception {
        loginPresenter.attachView(viewContract);
        // given
        loginPresenter.username = "Hello";
        loginPresenter.password = "World";
        loginPresenter.loginInteractor = (username, password) -> Task.call(() -> true);

        // when
        loginPresenter.login();

        // then
        Mockito.verify(viewContract).handleLoginSuccess();
    }

    @Test
    public void loginFailure()
            throws Exception {
        loginPresenter.attachView(viewContract);
        // given
        loginPresenter.username = "Hello";
        loginPresenter.password = "World";
        loginPresenter.loginInteractor = (username, password) -> Task.call(() -> false);

        // when
        loginPresenter.login();

        // then
        Mockito.verify(viewContract).handleLoginError();
    }
}