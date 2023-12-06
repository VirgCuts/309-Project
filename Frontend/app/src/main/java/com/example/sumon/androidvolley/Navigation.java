package com.example.sumon.androidvolley;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
/**
 * Navigation helper class used to add a navigation drawer to an activity.
 * This class is designed to work with activities that extend {@link AppCompatActivity}.
 * <p>
 * To use this helper class, create an instance of {@code Navigation} and call {@code setupNavigation()}
 * in your activity's onCreate method. Then override {@code onOptionsItemSelected} to delegate the
 * selection handling to the {@code Navigation} instance.
 * <p>
 * Your activity's layout XML should include a {@link DrawerLayout} and a {@link NavigationView}.
 * Refer to the activity_main.xml for an example structure.
 */
public class Navigation extends AppCompatActivity {
    private AppCompatActivity activity;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private static final String PREFS_NAME = "LeaderboardPrefs";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private Context context;
    /**
     * Default constructor for the Navigation helper class.
     */
    public Navigation() {
        
        // Initialize any default values or perform other setup here if needed.
    }
    /**
     * Constructor for the Navigation helper class.
     *
     * @param activity The activity in which the navigation is to be setup. This activity must extend {@link AppCompatActivity}.
     */
    public Navigation(AppCompatActivity activity) {
        this.activity = activity;
        this.context = activity;
    }
    /**
     * Sets up the navigation drawer and the toggle switch.
     * This method initializes the {@link NavigationView}, the {@link DrawerLayout}, and the {@link ActionBarDrawerToggle}.
     * It also handles the navigation item click events.
     */
    public void setupNavigation() {
        NavigationView navigationView = activity.findViewById(R.id.nav_view);
        drawerLayout = activity.findViewById(R.id.my_drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Menu menu = navigationView.getMenu();
        MenuItem curUserMenuItem = menu.findItem(R.id.curUser);
        SharedPreferences prefs = activity.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        curUserMenuItem.setTitle("User: " + prefs.getString(USERNAME_KEY,null));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                switch (item.getItemId()) {
                    case R.id.btnleaderboard:
                        activity.startActivity(new Intent(activity, LeaderboardActivity.class));
                        break;
                    case R.id.btnArtists:
                        activity.startActivity(new Intent(activity, ArtistActivity.class));
                        break;
                    case R.id.btnWebsocket:
                        activity.startActivity(new Intent(activity, ChatActivity.class));
                        break;
                    case R.id.btnLobby:
                        activity.startActivity(new Intent(activity, LobbyActivity.class));
                        break;
                        //change Later just to quickly add shop
                    case R.id.btnSettings:
                        activity.startActivity(new Intent(activity, ShopActivity.class));
                        break;
                    case R.id.btnAdmin:
                        activity.startActivity(new Intent(activity, AdminActivity.class));
                        break;
                    case R.id.logout:
                        SharedPreferences prefs = activity.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        prefs.edit().putString(USERNAME_KEY, "").apply();
                        prefs.edit().putString(PASSWORD_KEY, "").apply();

                        activity.startActivity(new Intent(activity, MainActivity.class));
                        break;


                    default:
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    /**
     * Handles the action bar's home/up button clicks.
     *
     * @param item The menu item that was selected.
     * @return boolean Return true if the navigation drawer toggle handled the event; otherwise, return false.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle navbar open clicks
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }
}


