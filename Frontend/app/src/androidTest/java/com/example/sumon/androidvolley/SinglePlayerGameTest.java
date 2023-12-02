package com.example.sumon.androidvolley;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.matchesPattern;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class SinglePlayerGameTest {

    @Rule
    public ActivityTestRule<SinglePlayerGame> activityRule = new ActivityTestRule<>(SinglePlayerGame.class);

    @Test
    public void wrongAnswerTest() throws InterruptedException {
        Thread.sleep(500);
        // Type text into the first EditText and close the soft keyboard
        onView(withId(R.id.r1c1)).perform(typeText("Wrong Answer"), closeSoftKeyboard());

        // Check if the EditText contains the entered text
        onView(withId(R.id.r1c1)).check(matches(withText("Wrong Answer")));
        onView(withId(R.id.r1c1)).perform(pressImeActionButton());
        Thread.sleep(500);
        onView(withId(R.id.r1c1)).check(matches(withText("")));
        onView(withId(R.id.Points)).check(matches(withText("Points: -5")));
    }
    @Test
    public void timerCountsDown() throws InterruptedException {
        // Wait for a few seconds to let the timer update
        Thread.sleep(2000); // Not recommended, but used here for simplicity

        // Check if the timer TextView is displayed and contains text in the format "mm:ss"
        onView(withId(R.id.timer)).check(matches(isDisplayed()));
        onView(withId(R.id.timer)).check(matches(withText(matchesPattern("\\d{1,2}:\\d{2}"))));
    }


}

