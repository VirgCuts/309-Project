package com.example.sumon.androidvolley;

import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Navigation {
    private AppCompatActivity activity;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    /*
    General Navigation Bar class essential to add a navbar to any class,
    @REQ Class Extends AppCompatActivity to function, to add to a class simply add these lines of code in respective places, use MainActivity for example

     private Navigation navigationHelper;

    navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();

           @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    also need to add XML structures look to activity main for an example of what needs to be added,
    basically surround code in a drawer layout and add a NavigationView to the bottom of any xml document
     */
    public Navigation(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setupNavigation() {
        NavigationView navigationView = activity.findViewById(R.id.nav_view);
        drawerLayout = activity.findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                switch (item.getItemId()) {
                    case R.id.home:
                        activity.startActivity(new Intent(activity, MainActivity.class));
                        break;
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
                    case R.id.btnSettings:
                        activity.startActivity(new Intent(activity, SettingsActivity.class));
                        break;

                    default:
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle navbar open clicks
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }
}


