package com.zhuinden.examplegithubclient;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
    }

    @Test
    public void addition_isCorrect()
            throws Exception {
        assertThat(2 + 2).isEqualTo(4);
        //assertEquals(4, 2 + 2);
    }

    @Test
    public void showsHelloWorld() {

        //assertThat(helloWorld.getText().toString()).isEqualTo("Hello World!");
        //assertThat(helloWorld.getText().toString()).is(new HamcrestCondition<>(Matchers.equalTo("Hello World!")));
        //assertThat("Did not display hello world", helloWorld.getText().toString(), Matchers.equalTo("Hello World!"));
    }
}