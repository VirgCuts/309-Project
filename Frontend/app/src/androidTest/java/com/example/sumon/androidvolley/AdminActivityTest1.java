package com.example.sumon.androidvolley;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
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

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AdminActivityTest1 {

    @Rule
    public ActivityScenarioRule<AdminActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AdminActivity.class);

    @Test
    public void testDisplayReportButtons() {
        onView(withId(R.id.btnGetAllReports)).check(matches(isDisplayed()));
        onView(withId(R.id.btnGetUsersReports)).check(matches(isDisplayed()));
        onView(withId(R.id.ban_count)).check(matches(isDisplayed()));
    }
    @Test
    public void testGetAllReportsFunctionality() throws InterruptedException {
        onView(withId(R.id.btnGetAllReports)).perform(click());
        Thread.sleep(50);
        onView(allOf(withId(R.id.containerReports), hasDescendant(withText(Matchers.not("")))))
                .check(matches(isDisplayed()));
    }
    @Test
    public void testGetUsersReportsFunctionality() throws InterruptedException {
        onView(withId(R.id.btnGetUsersReports)).perform(click());
        onView(withId(R.id.etUsernameForReport)).perform(replaceText("Keenan"), closeSoftKeyboard());
        onView(withId(R.id.btnGetUsersReports)).perform(click());
        Thread.sleep(50);
        onView(withId(R.id.containerReports)).check(matches(withNonEmptyText()));
    }

    @Test
    public void testBanUserFunctionality() {
        onView(withId(R.id.username_inputban)).perform(replaceText("Keenan"), closeSoftKeyboard());
        onView(withId(R.id.ban_count)).perform(click());
        onView(withId(R.id.tvBanCount)).check(matches(isDisplayed()));
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
