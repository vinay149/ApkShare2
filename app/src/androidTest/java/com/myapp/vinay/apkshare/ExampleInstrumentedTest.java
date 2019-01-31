package com.myapp.vinay.apkshare;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.jetbrains.annotations.TestOnly;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith (AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    
    
    @Rule
    public ActivityTestRule<SplashActivity> activityRule
            = new ActivityTestRule<>(
            SplashActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False to customize the intent
    @Rule
    public ActivityTestRule<MainActivity> activityRule2 = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void intent() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
        // Continue with your test
    }
    @Test
    public void intent2() {
        Intent intent = new Intent();
        activityRule2.launchActivity(intent);
        // Continue with your test
    }
    @Test
    public void useAppContext () {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext ();
        assertEquals ("com.myapp.vinay.apkshare", appContext.getPackageName ());
    }
    
}
