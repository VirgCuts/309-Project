package com.example.sumon.androidvolley;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
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
public class SinglePlayerTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void singlePlayerTest() throws InterruptedException {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username_input),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Carter"), closeSoftKeyboard());
        Thread.sleep(50);
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password_input),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("password1"), closeSoftKeyboard());
        Thread.sleep(300);
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.login),
                        isDisplayed()));
        appCompatButton.perform(click());
        Thread.sleep(100);
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
        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Close"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.action_bar),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.singlePlayer),
                        isDisplayed()));
        appCompatButton2.perform(click());
        Thread.sleep(50);
        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.r1c1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Cart''"), closeSoftKeyboard());
        Thread.sleep(50);
        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.r1c1),
                        isDisplayed()));
        appCompatEditText4.perform(pressImeActionButton());
        Thread.sleep(50);
        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.r2c2),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("Banger"), closeSoftKeyboard());
        Thread.sleep(50);
        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.r2c2),
                        isDisplayed()));
        appCompatEditText6.perform(pressImeActionButton());
        Thread.sleep(50);
        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.r1c2),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("wallls"), closeSoftKeyboard());
        Thread.sleep(50);
        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.r1c2),
                        isDisplayed()));
        appCompatEditText8.perform(pressImeActionButton());
        Thread.sleep(50);
        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.r1c3),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("carkes"), closeSoftKeyboard());
        Thread.sleep(50);
        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.r1c3),
                        isDisplayed()));
        appCompatEditText10.perform(pressImeActionButton());
        Thread.sleep(50);
        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.r2c3),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText("wallp"), closeSoftKeyboard());
        Thread.sleep(50);
        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.r2c3),
                        isDisplayed()));
        appCompatEditText12.perform(pressImeActionButton());
        Thread.sleep(50);
        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.r2c1),
                        isDisplayed()));
        appCompatEditText13.perform(replaceText("wallny"), closeSoftKeyboard());
        Thread.sleep(50);
        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.r2c1),
                        isDisplayed()));
        appCompatEditText14.perform(pressImeActionButton());
        Thread.sleep(50);
        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.r3c1),
                        isDisplayed()));
        appCompatEditText15.perform(replaceText("cary\n"), closeSoftKeyboard());
        Thread.sleep(50);
        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.r3c2),
                        isDisplayed()));
        appCompatEditText16.perform(replaceText("barry"), closeSoftKeyboard());
        Thread.sleep(100);
        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.r3c3),
                        isDisplayed()));
        appCompatEditText17.perform(replaceText("wally"), closeSoftKeyboard());
        Thread.sleep(100);
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.endGameButton),
                        isDisplayed()));
        appCompatButton3.perform(click());
        Thread.sleep(100);
        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.showScoreButton),
                        isDisplayed()));
        appCompatButton4.perform(click());
        Thread.sleep(100);
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
        Thread.sleep(100);
        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.btnLobby),
                        isDisplayed()));
        navigationMenuItemView.perform(click());
        Thread.sleep(100);
        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.singlePlayer),
                        isDisplayed()));
        appCompatButton5.perform(click());
        Thread.sleep(100);
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.endGameButton),
                        isDisplayed()));
        appCompatButton6.perform(click());
        Thread.sleep(100);
        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.restartButton),
                        isDisplayed()));
        appCompatButton7.perform(click());
        Thread.sleep(100);
        ViewInteraction appCompatImageButton10 = onView(
                allOf(withContentDescription("Open"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.action_bar),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton10.perform(click());
        Thread.sleep(100);
        ViewInteraction navigationMenuItemView10 = onView(
                allOf(withId(R.id.logout),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                6),
                        isDisplayed()));
        navigationMenuItemView10.perform(click());
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
