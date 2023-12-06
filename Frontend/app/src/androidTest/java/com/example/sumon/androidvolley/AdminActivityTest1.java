package com.example.sumon.androidvolley;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.google.android.apps.common.testing.accessibility.framework.replacements.TextUtils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class AdminActivityTest1 {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void test1_mainActivityTest6() throws InterruptedException {
        // Enter username and click OK
        Thread.sleep(50);
        ViewInteraction editText = onView(
                allOf(childAtPosition(
                                allOf(withId(android.R.id.custom),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        editText.perform(replaceText("Keenan"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(scrollTo(), click());
        Thread.sleep(50);
        // Open navigation drawer and select WebSocket option
        onView(withContentDescription("Open")).perform(click());
        onView(withId(R.id.btnWebsocket)).perform(click());
        Thread.sleep(50);
        // Connect to WebSocket
        onView(withId(R.id.username_input)).perform(replaceText("Keenan"), closeSoftKeyboard());
        onView(withId(R.id.connect_button)).perform(click());
        Thread.sleep(50);
        // Long click on a specific item in the chat and report the user
        onView(withId(R.id.chat_recyclerview)).perform(actionOnItemAtPosition(10, longClick()));
        onView(withText("Report user")).perform(click());

        // Enter report text and send the report
        onView(withId(R.id.etReportBody)).perform(replaceText("He mean"), closeSoftKeyboard());
        onView(withId(R.id.btnSendReport)).perform(click());
        Thread.sleep(50);
    }
    @Test
    public void test2_testDisplayReportButtons() throws InterruptedException {
        Thread.sleep(50);
        ViewInteraction editText = onView(
                allOf(childAtPosition(
                                allOf(withId(android.R.id.custom),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        editText.perform(replaceText("Keenan"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());
        Thread.sleep(50);
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.action_bar),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.action_bar_container),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.btnAdmin),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                6),
                        isDisplayed()));
        navigationMenuItemView.perform(click());
        Thread.sleep(50);
        onView(withId(R.id.btnGetAllReports)).check(matches(isDisplayed()));
        onView(withId(R.id.btnGetUsersReports)).check(matches(isDisplayed()));
        onView(withId(R.id.ban_count)).check(matches(isDisplayed()));
    }
    @Test
    public void test3_testGetAllReportsFunctionality() throws InterruptedException {
        Thread.sleep(50);
        ViewInteraction editText = onView(
                allOf(childAtPosition(
                                allOf(withId(android.R.id.custom),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        editText.perform(replaceText("Keenan"), closeSoftKeyboard());

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
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.btnAdmin),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                6),
                        isDisplayed()));
        navigationMenuItemView.perform(click());
        Thread.sleep(50);
        onView(withId(R.id.btnGetAllReports)).perform(click());
        Thread.sleep(50);
        onView(allOf(withId(R.id.containerReports), hasDescendant(withText(Matchers.not("")))))
                .check(matches(isDisplayed()));
    }
    @Test
    public void test4_testGetUsersReportsFunctionality() throws InterruptedException {
        Thread.sleep(50);
        ViewInteraction editText = onView(
                allOf(childAtPosition(
                                allOf(withId(android.R.id.custom),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        editText.perform(replaceText("Keenan"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());
        Thread.sleep(50);
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.action_bar),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.action_bar_container),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.btnAdmin),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                6),
                        isDisplayed()));
        navigationMenuItemView.perform(click());
        Thread.sleep(50);
        onView(withId(R.id.btnGetUsersReports)).perform(click());
        onView(withId(R.id.etUsernameForReport)).perform(replaceText("Keenan"), closeSoftKeyboard());
        onView(withId(R.id.btnGetUsersReports)).perform(click());
        Thread.sleep(50);
        onView(withId(R.id.containerReports)).check(matches(withNonEmptyText()));
    }

    @Test
    public void test5_testBanUserFunctionality() throws InterruptedException {
        Thread.sleep(50);
        ViewInteraction editText = onView(
                allOf(childAtPosition(
                                allOf(withId(android.R.id.custom),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        editText.perform(replaceText("Keenan"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());
        Thread.sleep(50);
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.action_bar),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.action_bar_container),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());
        Thread.sleep(50);
        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.btnAdmin),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                6),
                        isDisplayed()));
        navigationMenuItemView.perform(click());
        Thread.sleep(50);
        onView(withId(R.id.username_inputban)).perform(replaceText("Keenan"), closeSoftKeyboard());
        onView(withId(R.id.ban_count)).perform(click());
        Thread.sleep(50);
        onView(withId(R.id.tvBanCount)).check(matches(isDisplayed()));
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

    // Custom ViewMatcher to check if any TextView within a ViewGroup has non-empty text
    private static Matcher<View> withNonEmptyText() {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                if (view instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    for (int i = 0; i < viewGroup.getChildCount(); i++) {
                        View child = viewGroup.getChildAt(i);
                        if (child instanceof TextView) {
                            TextView textView = (TextView) child;
                            if (!TextUtils.isEmpty(textView.getText().toString())) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has non-empty text");
            }
        };
    }
}
