package com.example.sumon.androidvolley;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;

public class KeenanMultiplayerTest {
    @Rule
    public ActivityScenarioRule<MultiPlayerGame> mActivityScenarioRule =
            new ActivityScenarioRule<>(MultiPlayerGame.class);
    @Test
    public void MultiplayerTest() throws InterruptedException {
        Thread.sleep(1000);
    ViewInteraction appCompatEditText3 = onView(
            allOf(withId(R.id.r1c1),
                    isDisplayed()));
        appCompatEditText3.perform(replaceText("h"), closeSoftKeyboard());
        onView(withId(R.id.r1c1)).perform(pressImeActionButton());
        Thread.sleep(100);
    ViewInteraction appCompatEditText4 = onView(
            allOf(withId(R.id.r1c2),
                    isDisplayed()));
        appCompatEditText4.perform(replaceText("j"), closeSoftKeyboard());
        onView(withId(R.id.r1c2)).perform(pressImeActionButton());
        Thread.sleep(100);
    ViewInteraction appCompatEditText5 = onView(
            allOf(withId(R.id.r1c3),
                    isDisplayed()));
        appCompatEditText5.perform(replaceText("l"), closeSoftKeyboard());
        onView(withId(R.id.r1c3)).perform(pressImeActionButton());
        Thread.sleep(100);
    ViewInteraction appCompatEditText6 = onView(
            allOf(withId(R.id.r2c1),
                    isDisplayed()));
        appCompatEditText6.perform(replaceText("q"), closeSoftKeyboard());
        onView(withId(R.id.r2c1)).perform(pressImeActionButton());
        Thread.sleep(100);
    ViewInteraction appCompatEditText7 = onView(
            allOf(withId(R.id.r2c2),
                    isDisplayed()));
        appCompatEditText7.perform(replaceText("w"), closeSoftKeyboard());
        onView(withId(R.id.r2c2)).perform(pressImeActionButton());
        Thread.sleep(100);
    ViewInteraction appCompatEditText8 = onView(
            allOf(withId(R.id.r2c3),
                    isDisplayed()));
        appCompatEditText8.perform(replaceText("e"), closeSoftKeyboard());
        onView(withId(R.id.r2c3)).perform(pressImeActionButton());
        Thread.sleep(100);
    ViewInteraction appCompatEditText9 = onView(
            allOf(withId(R.id.r3c1),
                    isDisplayed()));
        appCompatEditText9.perform(replaceText("t"), closeSoftKeyboard());
        onView(withId(R.id.r3c1)).perform(pressImeActionButton());
        Thread.sleep(100);
    ViewInteraction appCompatEditText10 = onView(
            allOf(withId(R.id.r3c2),
                    isDisplayed()));
        appCompatEditText10.perform(replaceText("g"), closeSoftKeyboard());
        onView(withId(R.id.r3c2)).perform(pressImeActionButton());
        Thread.sleep(100);
    ViewInteraction appCompatEditText11 = onView(
            allOf(withId(R.id.r3c3),
                    isDisplayed()));
        appCompatEditText11.perform(replaceText("p"), closeSoftKeyboard());
        onView(withId(R.id.r3c3)).perform(pressImeActionButton());
        Thread.sleep(100);
    ViewInteraction appCompatButton4 = onView(
            allOf(withId(R.id.endGameButton),
                    isDisplayed()));
        appCompatButton4.perform(click());

    ViewInteraction relativeLayout = onView(
            allOf(withParent(allOf(withId(android.R.id.custom),
                            withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class)))),
                    isDisplayed()));
        relativeLayout.check(matches(isDisplayed()));

    ViewInteraction textView = onView(
            allOf(withId(R.id.Col1),
                    withParent(allOf(withId(R.id.winnerGrid),
                            withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                    isDisplayed()));
        textView.check(matches(isDisplayed()));

    ViewInteraction textView2 = onView(
            allOf(withId(R.id.Col2),
                    withParent(allOf(withId(R.id.winnerGrid),
                            withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                    isDisplayed()));
        textView2.check(matches(isDisplayed()));

    ViewInteraction textView3 = onView(
            allOf(withId(R.id.Row1),
                    withParent(allOf(withId(R.id.winnerGrid),
                            withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                    isDisplayed()));
        textView3.check(matches(isDisplayed()));

    ViewInteraction textView4 = onView(
            allOf(withId(R.id.Row2),
                    withParent(allOf(withId(R.id.winnerGrid),
                            withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                    isDisplayed()));
        textView4.check(matches(isDisplayed()));

    ViewInteraction textView5 = onView(
            allOf(withId(R.id.Row3),
                    withParent(allOf(withId(R.id.winnerGrid),
                            withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                    isDisplayed()));
        textView5.check(matches(isDisplayed()));

    ViewInteraction textView6 = onView(
            allOf(withId(R.id.r2c2),
                    withParent(allOf(withId(R.id.winnerGrid),
                            withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                    isDisplayed()));
        textView6.check(matches(isDisplayed()));

    ViewInteraction appCompatButton5 = onView(
            allOf(withId(R.id.restartButton),
                    isDisplayed()));
        appCompatButton5.perform(click());
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


