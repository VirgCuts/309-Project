package com.example.sumon.androidvolley;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity is the entry point for the application.
 * It sets up the user interface and navigation helpers, and prompts the user
 * for a username if it is not set.
 */
public class MainActivity extends AppCompatActivity {
    private Navigation navigationHelper;
    private static final String PREFS_NAME = "LeaderboardPrefs";
    private static final String USERNAME_KEY = "username";
    /**
     * Called when the activity is starting.
     * This is where most initialization should go: calling setContentView(int)
     * to inflate the activity's UI, using findViewById(int) to programmatically
     * interact with widgets in the UI, calling setupNavigation to initialize
     * the navigation helper, and checking for a username to prompt for input if necessary.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);

        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();
        // Removed the first run check and prompt
        if (getUsername(this) == null) {
            promptUsername();
        }
    }
    /**
     * This hook is called whenever an item in your options menu is selected.
     * It delegates the call to the navigation helper or handles it by default behavior.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to proceed,
     * true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    /**
     * Retrieves the stored username from SharedPreferences.
     *
     * @param context The context used to access the SharedPreferences.
     * @return String Returns the stored username or null if it's not found.
     */
    private String getUsername(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(USERNAME_KEY, null); // Return null if username isn't set
    }

    /**
     * Prompts the user to enter a username. This is typically called when a username
     * is not found in SharedPreferences. It presents an AlertDialog with an input field
     * for the user to enter their username, which it then saves in SharedPreferences.
     */
    private void promptUsername() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Username");

        final EditText inputField = new EditText(this);
        inputField.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(inputField);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String inputUsername = inputField.getText().toString();
            // Save the username
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            prefs.edit().putString(USERNAME_KEY, inputUsername).apply();
            // Continue with any other actions you need to do with the username
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            String inputUsername = "Guest";
            // Save the username
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            prefs.edit().putString(USERNAME_KEY, inputUsername).apply();
        });
        builder.setNeutralButton("Add User", (dialogInterface, i) -> {
            // Handle adding a new user
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            prefs.edit().remove(USERNAME_KEY).apply();
            promptUsername(); // Consider this part carefully. This may lead to a recursive call.
        });
        builder.show();
    }
}
