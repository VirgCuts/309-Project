package com.example.sumon.androidvolley;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
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
public class KeenanMainActivityTest4 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest4() throws InterruptedException {
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
                allOf(withId(R.id.btnWebsocket),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                3),
                        isDisplayed()));
        navigationMenuItemView.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.username_input),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Keenan"), closeSoftKeyboard());
        Thread.sleep(50);
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.connect_button), withText("Connect"),
                        isDisplayed()));
        appCompatButton2.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.message_input),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("Hey"), closeSoftKeyboard());
        Thread.sleep(50);
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.send_button),
                        isDisplayed()));
        appCompatButton3.perform(click());
        Thread.sleep(50);
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.chat_recyclerview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(3, longClick()));
        Thread.sleep(50);
        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Report user"),

                        isDisplayed()));
        appCompatTextView.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.etReportBody),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("hey"), closeSoftKeyboard());
        Thread.sleep(50);
        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btnSendReport),
                        isDisplayed()));
        appCompatButton4.perform(click());
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
                allOf(withId(R.id.btnAdmin),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                7),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btnGetAllReports),
                        isDisplayed()));
        appCompatButton5.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.etUsernameForReport),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("Keenan"), closeSoftKeyboard());
        Thread.sleep(50);
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btnGetUsersReports),
                        isDisplayed()));
        appCompatButton6.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.username_inputban),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("Keenan"), closeSoftKeyboard());
        Thread.sleep(50);
        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.ban_count),
                        isDisplayed()));
        appCompatButton7.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.username_inputban),
                        isDisplayed()));
        appCompatEditText8.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.username_inputban),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("d"));
        Thread.sleep(50);
        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.username_inputban),
                        isDisplayed()));
        appCompatEditText10.perform(closeSoftKeyboard());
        Thread.sleep(50);
        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.ban_count),
                        isDisplayed()));
        appCompatButton8.perform(click());
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
