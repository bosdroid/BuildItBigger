package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class JokesTest extends ApplicationTestCase<Application> implements EndpointsAsyncTask.GotJokeCallback {

    private CountDownLatch countDownLatch;
    private String joke;

    public JokesTest() {
        super(Application.class);
    }

    @SmallTest
    public void testJoke() throws InterruptedException {
        countDownLatch = new CountDownLatch(1);
        new EndpointsAsyncTask().execute(this);
        countDownLatch.await(10, TimeUnit.SECONDS);
        assertNotNull("joke is null", joke);
        assertFalse("joke is empty", joke.isEmpty());
    }

    @Override
    public void done(String result, boolean error) {
        this.joke = result;
        countDownLatch.countDown();
    }
}