package com.example.sumon.androidvolley;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class LeaderboardActivityTest {

    @Rule
    public ActivityTestRule<LeaderboardActivity> activityRule = new ActivityTestRule<>(LeaderboardActivity.class);

    @Before
    public void setUp() throws InterruptedException {
        // Wait for 2 seconds before each test
        Thread.sleep(500);
    }

    @Test
    public void leaderboardData_IsDisplayed() throws InterruptedException {
        // Check if the ListView is displayed
        onView(withId(R.id.leaderboardListView)).check(matches(isDisplayed()));
        // Scroll to a position and verify the data is displayed
        onData(anything()).inAdapterView(withId(R.id.leaderboardListView)).atPosition(0).check(matches(isDisplayed()));
    }
    @Test
    public void clickingListViewItem_UpdatesSelectedItem() throws InterruptedException {
        // Click on the first item in the ListView
        onData(anything()).inAdapterView(withId(R.id.leaderboardListView)).atPosition(0).perform(click());

        // Verify the TextViews for rank and score are updated
        onView(withId(R.id.playerRankTextView)).check(matches(withText("1")));
        onView(withId(R.id.playerScoreTextView)).check(matches(not(withText(""))));
    }

}

