package com.example.sumon.androidvolley;import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import org.hamcrest.Matcher;




@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShopActivityTest {

    @Rule
    public ActivityTestRule<ShopActivity> activityRule = new ActivityTestRule<>(ShopActivity.class);

    private ShopActivity activity;

    @Before
    public void setUp() {
        activity = activityRule.getActivity();
    }

    @Test
    public void testOnCreate() throws InterruptedException {

        // Initialize views
        onView(withId(R.id.orange)).check(matches(isDisplayed()));
        onView(withId(R.id.purple)).check(matches(isDisplayed()));
        onView(withId(R.id.lightblue)).check(matches(isDisplayed()));
        onView(withId(R.id.yellow)).check(matches(isDisplayed()));
        onView(withId(R.id.magenta)).check(matches(isDisplayed()));
        onView(withId(R.id.green)).check(matches(isDisplayed()));
        onView(withId(R.id.white)).check(matches(isDisplayed()));
        onView(withId(R.id.balance)).check(matches(isDisplayed()));

        // Set balance
        onView(withId(R.id.balance)).check(matches(withText("Balance: 10000")));

        // Set button click listeners
        onView(withId(R.id.orange)).perform(click());
        // Add similar clicks for other buttons

        // Get select color
        // getPurchased(); // Uncomment this line if getPurchased() needs testing

        // Delayed update
        onView(isRoot()).perform(waitFor(500)); // Wait for 500 milliseconds

        // Additional testing for updateButtons() and toastSelected()
        // You may need to add custom matchers and assertions for these methods
    }

    /**
     * Custom wait action to wait for a specified duration in milliseconds.
     *
     * @param milliseconds The duration to wait in milliseconds.
     * @return Action to perform the wait.
     */
    public static ViewAction waitFor(final long milliseconds) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + milliseconds + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(milliseconds);
            }
        };
    }
}

