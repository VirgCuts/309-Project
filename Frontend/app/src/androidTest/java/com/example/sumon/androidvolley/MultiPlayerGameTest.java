package com.example.sumon.androidvolley;

import static androidx.core.content.ContextCompat.startActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.allOf;

import android.content.Intent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;

public class MultiPlayerGameTest {

    @Rule
    public ActivityTestRule<MultiPlayerGame> activityRule = new ActivityTestRule<>(MultiPlayerGame.class);

    @Test
    public void testMultiplayerGame() throws InterruptedException {
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
                allOf(withId(R.id.restartButton),
                        isDisplayed()));
        appCompatButton4.perform(click());
        Thread.sleep(100);

    }
}


