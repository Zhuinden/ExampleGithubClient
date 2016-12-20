package com.zhuinden.examplegithubclient;

import com.zhuinden.examplegithubclient.presentation.paths.login.LoginKeyTest;
import com.zhuinden.examplegithubclient.presentation.paths.login.LoginPresenterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Zhuinden on 2016.12.19..
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({LoginPresenterTest.class, LoginKeyTest.class})
public class UnitTestSuite {
}
