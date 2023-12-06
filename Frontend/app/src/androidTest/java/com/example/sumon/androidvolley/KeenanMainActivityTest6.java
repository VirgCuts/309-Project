package com.example.sumon.androidvolley;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
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
public class KeenanMainActivityTest6 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void keenanMainActivityTest6() throws InterruptedException {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username_input),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Keenan"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password_input),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("password1"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.login),
                        isDisplayed()));
        appCompatButton.perform(click());
        Thread.sleep(100);
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.multiPlayer),
                        isDisplayed()));
        appCompatButton2.perform(click());
        Thread.sleep(100);
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rvLobbies),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        Thread.sleep(100);
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btnExit),
                        isDisplayed()));
        appCompatButton3.perform(click());
        Thread.sleep(100);
        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.rvLobbies),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)));
        recyclerView2.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btnExit),
                        isDisplayed()));
        appCompatButton4.perform(click());
        Thread.sleep(100);
        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.rvLobbies),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)));
        recyclerView3.perform(actionOnItemAtPosition(4, click()));

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btnExit),
                        isDisplayed()));
        appCompatButton5.perform(click());
        Thread.sleep(100);
        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.rvLobbies),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)));
        recyclerView4.perform(actionOnItemAtPosition(6, click()));

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btnExit),
                        isDisplayed()));
        appCompatButton6.perform(click());
        Thread.sleep(100);
        ViewInteraction recyclerView5 = onView(
                allOf(withId(R.id.rvLobbies),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)));
        recyclerView5.perform(actionOnItemAtPosition(8, click()));

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.btnExit),
                        isDisplayed()));
        appCompatButton7.perform(click());
        Thread.sleep(100);
        ViewInteraction recyclerView6 = onView(
                allOf(withId(R.id.rvLobbies),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)));
        recyclerView6.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.message_input),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Hey"), closeSoftKeyboard());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.send_button),
                        isDisplayed()));
        appCompatButton8.perform(click());
        Thread.sleep(100);
        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.btnReadyUp),
                        isDisplayed()));
        appCompatButton9.perform(click());
        Thread.sleep(100);
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btnExit),
                        isDisplayed()));
        appCompatButton10.perform(click());
        Thread.sleep(100);
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
        Thread.sleep(100);
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
