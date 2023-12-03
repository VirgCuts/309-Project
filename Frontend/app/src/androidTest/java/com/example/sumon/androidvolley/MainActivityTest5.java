package com.example.sumon.androidvolley;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
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
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest5 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest5() throws InterruptedException {
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
                allOf(withId(R.id.btnWebsocket),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                4),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username_input),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.connect_button), withText("Connect"),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.chatMessage), withText("---\nconnection closed by server\nreason: "),
                        withParent(allOf(withId(R.id.chat_recyclerview),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("---\nconnection closed by server\nreason: ")));

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.username_input), withText("test"),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.username_input), withText("test"),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Keenan"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.username_input), withText("Keenan"),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.connect_button), withText("Connect"),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.chatMessage), withText("Welcome to the chat, Keenan"),
                        withParent(allOf(withId(R.id.chat_recyclerview),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                        isDisplayed()));
        textView2.check(matches(withText("Welcome to the chat, Keenan")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.chatMessage), withText("User: Keenan has joined the chat"),
                        withParent(allOf(withId(R.id.chat_recyclerview),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                        isDisplayed()));
        textView3.check(matches(withText("User: Keenan has joined the chat")));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.message_input),
                        childAtPosition(
                                allOf(withId(R.id.sendMessage),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("Hello"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.send_button), withText("Send"),
                        childAtPosition(
                                allOf(withId(R.id.sendMessage),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction textView4 = onView(withId(R.id.chat_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.message_input), withText("Hello"),
                        childAtPosition(
                                allOf(withId(R.id.sendMessage),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatEditText6.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.message_input), withText("Hello"),
                        childAtPosition(
                                allOf(withId(R.id.sendMessage),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("Report this message"));

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.message_input), withText("Report this message"),
                        childAtPosition(
                                allOf(withId(R.id.sendMessage),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatEditText8.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.send_button), withText("Send"),
                        childAtPosition(
                                allOf(withId(R.id.sendMessage),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.chat_recyclerview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(19, longClick()));

        ViewInteraction textView5 = onView(
                allOf(withId(android.R.id.title), withText("Like message"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView5.check(matches(withText("Like message")));

        ViewInteraction textView6 = onView(
                allOf(withId(android.R.id.title), withText("Report user"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView6.check(matches(withText("Report user")));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Report user"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(withParent(allOf(withId(R.id.my_drawer_layout),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.etReportBody),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("He is a bum"), closeSoftKeyboard());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btnSendReport), withText("Send Report"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_drawer_layout),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton6.perform(click());
        Thread.sleep(50);
        ViewInteraction textView7 = onView(
                allOf(IsInstanceOf.<View>instanceOf(android.widget.TextView.class), withText("Enter Username"),
                        withParent(allOf(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView7.check(matches(withText("Enter Username")));

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(android.R.id.button2), withText("Play as Guest"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        appCompatButton7.perform(scrollTo(), click());

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
                allOf(withId(R.id.btnWebsocket),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                4),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.username_input),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText10.perform(replaceText("Keenan"), closeSoftKeyboard());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.connect_button), withText("Connect"),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.chatMessage), withText("Username is already in use"),
                        withParent(allOf(withId(R.id.chat_recyclerview),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                        isDisplayed()));
        textView8.check(matches(withText("Username is already in use")));
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
