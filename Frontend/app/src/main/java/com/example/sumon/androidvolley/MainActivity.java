package com.example.sumon.androidvolley;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
    private Navigation navigationHelper;
    private static final String PREFS_NAME = "LeaderboardPrefs";
    private static final String USERNAME_KEY = "username";

    private static final String FIRST_RUN_KEY = "first_run";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);



        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();
        if (isFirstRun()) {
            promptUsername();
            markFirstRun();
        }
        String username = getUsername(this);

    }
    // Method to show prompt and handle input
    // Method to show prompt and handle input
    private void promptUsername() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Username");

            final EditText inputField = new EditText(this);
            inputField.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(inputField);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String inputUsername = inputField.getText().toString();
                    // Save the username
                    SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    prefs.edit().putString(USERNAME_KEY, inputUsername).apply();
                    // Continue with any other actions you need to do with the username
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    // Handle what you want to do if the user doesn't enter a username
                }
            });

            // Option to add a new user instead
            builder.setNeutralButton("Add User", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Handle adding a new user
                    // This could involve opening a new dialog to enter new user information, for instance.
                    // For now, let's clear the old username and prompt again.
                    SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    prefs.edit().remove(USERNAME_KEY).apply();
                    promptUsername();
                }
            });
            builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    private String getUsername(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(USERNAME_KEY, null); // Return null if username isn't set
    }
    private boolean isFirstRun() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getBoolean(FIRST_RUN_KEY, true); // Default is true
    }
    private void markFirstRun() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putBoolean(FIRST_RUN_KEY, false).apply();
    }
}
