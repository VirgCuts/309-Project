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

public class MainActivity extends AppCompatActivity {
    private Navigation navigationHelper;
    private static final String PREFS_NAME = "LeaderboardPrefs";
    private static final String USERNAME_KEY = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);

        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();
        // Removed the first run check and prompt
        String username = getUsername(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private String getUsername(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(USERNAME_KEY, null); // Return null if username isn't set
    }

    // Consider whether you still need this method if you are removing the first run logic.
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
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.setNeutralButton("Add User", (dialogInterface, i) -> {
            // Handle adding a new user
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            prefs.edit().remove(USERNAME_KEY).apply();
            promptUsername(); // Consider this part carefully. This may lead to a recursive call.
        });
        builder.show();
    }
}
