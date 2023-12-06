package com.example.sumon.androidvolley;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class KeenanMainActivityTest3 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest3() throws InterruptedException {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username_input),

                        isDisplayed()));
        appCompatEditText.perform(replaceText("Keenan"), closeSoftKeyboard());
        Thread.sleep(50);
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password_input),

                        isDisplayed()));
        appCompatEditText2.perform(replaceText("password1"), closeSoftKeyboard());
        Thread.sleep(50);
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.login),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.singlePlayer), withText("SinglePlayer"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());
        Thread.sleep(500);
        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.r1c1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Test"), closeSoftKeyboard());
        onView(withId(R.id.r1c1)).perform(pressImeActionButton());
        Thread.sleep(500);
        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.r1c2),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("Test"), closeSoftKeyboard());
        onView(withId(R.id.r1c2)).perform(pressImeActionButton());
        Thread.sleep(500);
        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.r1c3),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("Test"), closeSoftKeyboard());
        onView(withId(R.id.r1c3)).perform(pressImeActionButton());
        Thread.sleep(500);
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.endGameButton),
                        isDisplayed()));
        appCompatButton3.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.showScoreButton),
                        isDisplayed()));
        appCompatButton4.perform(click());
        Thread.sleep(50);
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
