package com.example.sumon.androidvolley;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
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
public class KeenanMainActivityTest5 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void keenanMainActivityTest5() throws InterruptedException {
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
        Thread.sleep(50);
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
        Thread.sleep(50);
        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.btnSettings),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                5),
                        isDisplayed()));
        navigationMenuItemView.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.orange),
                        isDisplayed()));
        appCompatButton2.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.purple),
                        isDisplayed()));
        appCompatButton3.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.lightblue),
                        isDisplayed()));
        appCompatButton4.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.yellow),
                        isDisplayed()));
        appCompatButton5.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.magenta),
                        isDisplayed()));
        appCompatButton6.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.green),
                        isDisplayed()));
        appCompatButton7.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.white),
                        isDisplayed()));
        appCompatButton8.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.action_bar),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());
        Thread.sleep(50);
        ViewInteraction navigationMenuItemView2 = onView(
                allOf(withId(R.id.curUser),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                1),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Open"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.action_bar),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());
        Thread.sleep(50);
        ViewInteraction navigationMenuItemView3 = onView(
                allOf(withId(R.id.btnLobby),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                4),
                        isDisplayed()));
        navigationMenuItemView3.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.singlePlayer),
                        isDisplayed()));
        appCompatButton9.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.endGameButton),
                        isDisplayed()));
        appCompatButton10.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.restartButton),
                        isDisplayed()));
        appCompatButton11.perform(click());
        Thread.sleep(50);
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
        Thread.sleep(50);
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
