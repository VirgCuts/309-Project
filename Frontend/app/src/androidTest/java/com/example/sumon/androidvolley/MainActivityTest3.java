package com.example.sumon.androidvolley;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
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
public class MainActivityTest3 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest3() {
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
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.btnArtists),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                3),
                        isDisplayed()));
        navigationMenuItemView.perform(click());
        Thread.sleep(1000);
        ViewInteraction appCompatEditText = onView(withId(R.id.searchText));
        appCompatEditText.perform(replaceText("keenan"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.artistName),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Keenan"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.numPlat),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                6),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("0"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.numGrammies),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                7),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("0"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.numGrammies), withText("0"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                7),
                        isDisplayed()));
        appCompatEditText6.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.song),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                8),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("0"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.songGenre),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                9),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("hip hop"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.addArtist), withText("Add Artist"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btnBack), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(android.R.id.button2), withText("Play as Guest"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        appCompatButton5.perform(scrollTo(), click());

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

        ViewInteraction navigationMenuItemView2 = onView(
                allOf(withId(R.id.btnArtists),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                3),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());
        Thread.sleep(1000);
        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.deleteNum),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("396"), closeSoftKeyboard());
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
