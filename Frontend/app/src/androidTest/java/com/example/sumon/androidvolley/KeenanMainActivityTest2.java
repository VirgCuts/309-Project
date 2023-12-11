package com.example.sumon.androidvolley;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

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
public class KeenanMainActivityTest2 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() throws InterruptedException {
        Thread.sleep(100);
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Keenan"), closeSoftKeyboard());
        Thread.sleep(100);
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("password1"), closeSoftKeyboard());
        Thread.sleep(100);
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.login), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());
        Thread.sleep(100);
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btnStudy),
                        isDisplayed()));
        appCompatButton2.perform(click());
        Thread.sleep(100);
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button),
                        isDisplayed()));
        appCompatButton3.perform(click());
        Thread.sleep(100);
        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.songGuess),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Travis Scott"), closeSoftKeyboard());
        Thread.sleep(100);
        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.showAllAnswers), withText("Show Artist songs"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                8),
                        isDisplayed()));
        appCompatButton4.perform(click());
        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("Open"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.action_bar),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction navigationMenuItemView4 = onView(
                allOf(withId(R.id.logout),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                6),
                        isDisplayed()));
        navigationMenuItemView4.perform(click());
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