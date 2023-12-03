package com.example.sumon.androidvolley;

import com.example.sumon.androidvolley.MainActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;

import android.widget.EditText;


@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void promptUsernameDialog_Shows() {
        // Assuming the activity will show the dialog if username is not set.
        // You can use Espresso to check if the dialog is displayed.
        onView(withText("Enter Username")).check(matches(isDisplayed()));
    }
    @Test
    public void promptUsernameDialog_SavesUsername_WhenOKClicked() {
        // Input some text into the EditText field in the dialog
        onView(withClassName(Matchers.equalTo(EditText.class.getName()))).perform(replaceText("NewUser"), closeSoftKeyboard());

        // Click on the 'OK' button in the dialog
        onView(withText("OK")).perform(click());

        // Now check if the username is saved
        MainActivity activity =intentsTestRule.getActivity();
        assertEquals("NewUser", activity.getUsername());
    }
    @Test
    public void promptUsernameDialog_DoesNotChangeUsername_WhenCancelClicked() {
        // Set a username
        MainActivity activity = intentsTestRule.getActivity();
        activity.setUsername("ExistingUser");

        // Click on the 'Cancel' button in the dialog
        onView(withText("Play as Guest")).perform(click());

        // Check that the username remains unchanged
        assertEquals("Guest", activity.getUsername());
    }
    @Test
    public void navigateToLeaderboard() {
        onView(withText("Play as Guest")).perform(click());
        // Open the drawer
        onView(withId(R.id.my_drawer_layout)).perform(DrawerActions.open());

        // Click on the "Leaderboard" item in the navigation drawer
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.btnleaderboard));

        // Verify that an intent to start the LeaderboardActivity was sent
        Intents.intended(hasComponent(LeaderboardActivity.class.getName()));
    }
    @Test
    public void navigateToArtistChanges() {
        onView(withText("Play as Guest")).perform(click());
        // Open the drawer
        onView(withId(R.id.my_drawer_layout)).perform(DrawerActions.open());

        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.btnArtists));

        Intents.intended(hasComponent(ArtistActivity.class.getName()));
    }
    @Test
    public void navigateToChatRoom() {
        onView(withText("Play as Guest")).perform(click());
        // Open the drawer
        onView(withId(R.id.my_drawer_layout)).perform(DrawerActions.open());

        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.btnWebsocket));

        Intents.intended(hasComponent(ChatActivity.class.getName()));
    }
    @Test
    public void navigateToSinglePlayerGame() throws InterruptedException {
        Thread.sleep(50);
        onView(withText("Play as Guest")).perform(click());
        // Open the drawer
        onView(withId(R.id.my_drawer_layout)).perform(DrawerActions.open());

        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.btnLobby));

        Intents.intended(hasComponent(LobbyActivity.class.getName()));
        Thread.sleep(50);

        onView(withText("SinglePlayer")).check(matches(isDisplayed()));

        onView(withText("Multiplayer")).check(matches(isDisplayed()));

        onView(withText("Study")).check(matches(isDisplayed()));

        onView(withText("SinglePlayer")).perform(click());

        Intents.intended(hasComponent(SinglePlayerGame.class.getName()));
        Thread.sleep(50);

        // Perform actions in SinglePlayerGame
        onView(withId(R.id.r1c1)).perform(replaceText("Test"), closeSoftKeyboard());
        onView(withId(R.id.r1c1)).check(matches(withText("Test")));

        // End the game
        onView(withId(R.id.endGameButton)).perform(click());

        // Check the end game screen
        onView(withText(containsString("Player Guest wins"))).check(matches(isDisplayed()));
    }
}