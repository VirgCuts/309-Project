package com.example.sumon.androidvolley;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
    private Navigation navigationHelper;
    private static final String PREFS_NAME = "LeaderboardPrefs";
    private static final String USERNAME_KEY = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);
        promptUsername();


        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();

    }
    // Method to show prompt and handle input
    private void promptUsername() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String username = prefs.getString(USERNAME_KEY, null);

        if (username == null) {
            // No username stored, need to ask for it
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

            builder.show();
        } else {
            // Username is already stored, you might want to use it directly
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
