package com.example.sumon.androidvolley;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest4 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest4() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button2), withText("Play as Guest"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.action_bar),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.btnleaderboard),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                2),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction textView = onView(
                allOf(withText("ALL-TIME"),
                        withParent(allOf(withContentDescription("All-time"),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("ALL-TIME")));

        onData(anything()).inAdapterView(withId(R.id.leaderboardListView)).atPosition(0).check(matches(isDisplayed()));

        DataInteraction linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.leaderboardListView),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                4)))
                .atPosition(0);
        linearLayout2.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.playerRankTextView), withText("1"),
                        withParent(withParent(withId(R.id.leaderBoardTop))),
                        isDisplayed()));
        textView2.check(matches(withText("1")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.playerScoreTextView), withText("1000"),
                        withParent(withParent(withId(R.id.leaderBoardTop))),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        ViewInteraction tabView = onView(
                allOf(withContentDescription("Monthly"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scoresTabLayout),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        onData(anything()).inAdapterView(withId(R.id.leaderboardListView)).atPosition(0).check(matches(isDisplayed()));

        ViewInteraction tabView2 = onView(
                allOf(withContentDescription("Weekly"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scoresTabLayout),
                                        0),
                                2),
                        isDisplayed()));
        tabView2.perform(click());

        onData(anything()).inAdapterView(withId(R.id.leaderboardListView)).atPosition(0).check(matches(isDisplayed()));
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
