package com.example.sumon.androidvolley;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

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
    private static final String PASSWORD_KEY = "password";
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
        showLoginDialog();

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
    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login");

        // Set up the layout for the dialog
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Add an EditText for the username
        final EditText usernameEditText = new EditText(this);
        usernameEditText.setHint("Username");
        layout.addView(usernameEditText);

        // Add an EditText for the password
        final EditText passwordEditText = new EditText(this);
        passwordEditText.setHint("Password");
        layout.addView(passwordEditText);

        builder.setView(layout);

        // Set up the positive button (Login)
        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve the entered username and password
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // You can perform login validation here
                // For simplicity, just display a toast with the entered credentials
                // Replace this with your actual login logic
                String message = "Username: " + username + "\nPassword: " + password;
                // Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        // Set up the negative button (Cancel)
        builder.setNegativeButton("Play as Guest", (dialog, which) -> {
            String inputUsername = "Guest";

            // Save the username
            saveCredentials(inputUsername, null);
        });

        // Create and show the dialog
        builder.create().show();
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
        builder.setTitle("Login");

        final EditText inputUsernameField = new EditText(this);
        final EditText inputPasswordField = new EditText(this);

        inputUsernameField.setInputType(InputType.TYPE_CLASS_TEXT);
        inputPasswordField.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

        inputUsernameField.setHint("Username");
        inputPasswordField.setHint("Password");

        builder.setView(inputUsernameField);
        builder.setView(inputPasswordField);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String inputUsername = inputUsernameField.getText().toString();
            String inputPassword = inputPasswordField.getText().toString();

            // Save the username and password
            saveCredentials(inputUsername, inputPassword);

            // Continue with any other actions you need to do with the username
        });

        builder.setNegativeButton("Play as Guest", (dialog, which) -> {
            String inputUsername = "Guest";

            // Save the username
            saveCredentials(inputUsername, null);
        });

        builder.show();
    }

    /**
     * Saves the username and password in SharedPreferences.
     *
     * @param username The username to be set.
     * @param password The password to be set.
     */
    private void saveCredentials(String username, String password) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(USERNAME_KEY, username);
        editor.putString(PASSWORD_KEY, password);

        editor.apply();
    }

    /**
     * Retrieves the stored username from SharedPreferences.
     *
     * @return String Returns the stored username or "Guest" if it's not found.
     */
    public String getUsername() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(USERNAME_KEY, "Guest"); // Return "Guest" if username isn't set
    }
    /**
     * Sets the username in SharedPreferences.
     *
     * @param username The username to be set.
     */
    public void setUsername(String username) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USERNAME_KEY, username);
        editor.apply();
    }
}
