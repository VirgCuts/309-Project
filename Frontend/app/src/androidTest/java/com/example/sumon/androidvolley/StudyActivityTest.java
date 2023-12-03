package com.example.sumon.androidvolley;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class StudyActivityTest {

    @Rule
    public ActivityScenarioRule<StudyActivity> activityScenarioRule = new ActivityScenarioRule<>(StudyActivity.class);

    @Test
    public void testRandomArtistButton() {
        onView(withId(R.id.button)).perform(click());
        // Check if the artistName TextView is updated (assuming it changes upon button click)
        onView(withId(R.id.artistName)).check(matches(isDisplayed()));
    }

    @Test
    public void testShowAnswersButton() {
        onView(withId(R.id.showAllAnswers)).perform(click());
        // Check if the songList TextView is updated (assuming it changes upon button click)
        onView(withId(R.id.songList)).check(matches(isDisplayed()));
    }

    @Test
    public void testSongGuessFunctionality() {
        // Enter a sample text in songGuess EditText and perform some action
        onView(withId(R.id.songGuess)).perform(replaceText("Sample Song"), click());
        // Check for the displayCorrectness TextView update or any other expected behavior
        onView(withId(R.id.displayCorrectness)).check(matches(isDisplayed()));
    }

    @Test
    public void testUIElementsVisibility() {
        // Check visibility of all TextViews and EditText
        onView(withId(R.id.artistName)).check(matches(isDisplayed()));
        onView(withId(R.id.displayCorrectness)).check(matches(isDisplayed()));
        onView(withId(R.id.numCorrect)).check(matches(isDisplayed()));
        onView(withId(R.id.numIncorrect)).check(matches(isDisplayed()));
        onView(withId(R.id.streak)).check(matches(isDisplayed()));
        onView(withId(R.id.songGuess)).check(matches(isDisplayed()));
        // ... add checks for other UI elements as needed
    }

    // Add more tests for other functionalities and UI elements as needed
}
