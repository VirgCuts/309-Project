package com.example.sumon.androidvolley;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() throws InterruptedException {
        ViewInteraction editText = onView(
                allOf(childAtPosition(
                                allOf(withId(android.R.id.custom),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        editText.perform(replaceText("Test"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
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
                allOf(withId(R.id.btnLobby),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                5),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.singlePlayer), withText("SinglePlayer"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());
        Thread.sleep(1000);
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.r1c1),
                        childAtPosition(
                                allOf(withId(R.id.gridLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                4),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Test"), closeSoftKeyboard());
        onView(withId(R.id.r1c1)).perform(pressImeActionButton());
        Thread.sleep(1000);
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.r1c2),
                        childAtPosition(
                                allOf(withId(R.id.gridLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                5),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("Test"), closeSoftKeyboard());
        onView(withId(R.id.r1c2)).perform(pressImeActionButton());
        Thread.sleep(1000);
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.endGameButton), withText("Quit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.winnerText), withText("Player Test wins"),
                        withParent(withParent(withId(android.R.id.custom))),
                        isDisplayed()));
        textView.check(matches(withText("Player Test wins")));

        ViewInteraction button = onView(
                allOf(withId(R.id.showScoreButton), withText("SHOW SCORE"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.restartButton), withText("RESTART"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.pointsTextView), withText("Points: -10"),
                        withParent(withParent(withId(android.R.id.custom))),
                        isDisplayed()));
        textView2.check(matches(withText("Points: -10")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.pointsTextView), withText("Points: -10"),
                        withParent(withParent(withId(android.R.id.custom))),
                        isDisplayed()));
        textView3.check(matches(withText("Points: -10")));

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.showScoreButton), withText("Show Score"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        4),
                                1),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.leaderBoardTitle), withText("Leaderboard"),
                        withParent(allOf(withId(R.id.leaderBoardTop),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.leaderBoardTitle), withText("Leaderboard"),
                        withParent(allOf(withId(R.id.leaderBoardTop),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                        isDisplayed()));
        textView5.check(matches(isDisplayed()));

        ViewInteraction tabView = onView(
                allOf(withContentDescription("Monthly"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scoresTabLayout),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        ViewInteraction tabView2 = onView(
                allOf(withContentDescription("Weekly"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scoresTabLayout),
                                        0),
                                2),
                        isDisplayed()));
        tabView2.perform(click());
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
