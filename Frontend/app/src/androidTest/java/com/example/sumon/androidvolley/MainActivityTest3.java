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
                allOf(withId(R.id.btnArtists),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                3),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction viewGroup = onView(
                allOf(withParent(allOf(withId(R.id.my_drawer_layout),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.btnBack), withText("BACK"),
                        withParent(withParent(withId(R.id.my_drawer_layout))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.deleteArtist), withText("DELETE ARTIST"),
                        withParent(withParent(withId(R.id.my_drawer_layout))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.addArtist), withText("ADD ARTIST"),
                        withParent(withParent(withId(R.id.my_drawer_layout))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.msgResponse), withText("[{\"id\":1,\"name\":\"Travis Scott\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":2,\"name\":\"Drake\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":3,\"name\":\"Tyler The Creator\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":4,\"name\":\"Metro Boomin\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":5,\"name\":\"Playboi Carti\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":6,\"name\":\"Lil Uzi Vert\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":7,\"name\":\"Yeat\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":8,\"name\":\"21 Savage\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":9,\"name\":\"Kanye West\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":10,\"name\":\"Lil Wayne\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":11,\"name\":\"Mike WiLL Made-It\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":12,\"name\":\"A$AP Rocky\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":13,\"name\":\"Pharrell Williams\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":14,\"name\":\"Keenan\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]}]"),
                        withParent(withParent(withId(R.id.my_drawer_layout))),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.msgResponse), withText("[{\"id\":1,\"name\":\"Travis Scott\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":2,\"name\":\"Drake\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":3,\"name\":\"Tyler The Creator\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":4,\"name\":\"Metro Boomin\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":5,\"name\":\"Playboi Carti\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":6,\"name\":\"Lil Uzi Vert\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":7,\"name\":\"Yeat\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":8,\"name\":\"21 Savage\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":9,\"name\":\"Kanye West\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":10,\"name\":\"Lil Wayne\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":11,\"name\":\"Mike WiLL Made-It\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":12,\"name\":\"A$AP Rocky\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":13,\"name\":\"Pharrell Williams\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]},{\"id\":14,\"name\":\"Keenan\",\"numPlatinums\":0,\"numGrammys\":0,\"songs\":[]}]"),
                        withParent(withParent(withId(R.id.my_drawer_layout))),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.searchText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Artist"), closeSoftKeyboard());
        onView(withId(R.id.searchText)).perform(pressImeActionButton());
        ViewInteraction textView3 = onView(
                allOf(withId(R.id.boolText), withText("Song not found for Artist"),
                        withParent(withParent(withId(R.id.my_drawer_layout))),
                        isDisplayed()));
        textView3.check(matches(withText("Song not found for Artist")));
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
