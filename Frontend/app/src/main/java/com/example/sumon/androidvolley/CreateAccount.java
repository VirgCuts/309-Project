package com.example.sumon.androidvolley;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MainActivity is the entry point for the application.
 * It sets up the user interface and navigation helpers, and prompts the user
 * for a username if it is not set.
 */
public class CreateAccount extends AppCompatActivity {
    private static final String PREFS_NAME = "LeaderboardPrefs";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static EditText usernameText, passwordText, emailText;
    private static Button createAccount, have_account;

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
        setContentView(R.layout.create_account);

        usernameText = findViewById(R.id.create_username_input);
        emailText = findViewById(R.id.create_email_input);
        passwordText = findViewById(R.id.create_password_input);
        createAccount = findViewById(R.id.create_account);
        have_account = findViewById(R.id.have_account);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordText.getText().toString();
                String username = usernameText.getText().toString();
                String email = emailText.getText().toString();
                if(isValidPassword(passwordText.getText().toString()) && isEmailValid(emailText.getText().toString()) && isValidUsername(usernameText.getText().toString())) {

                    setUserBackend(username, password,email);

                    prefs.edit().putString(USERNAME_KEY, username).apply();
                    prefs.edit().putString(PASSWORD_KEY, password).apply();
                    Toast.makeText(CreateAccount.this, "Account Created",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateAccount.this,
                            LobbyActivity.class));
                }
                else if (!isValidPassword(password)) {
                    Toast.makeText(CreateAccount.this, "Please enter a valid password (Length 8 one letter and number)",Toast.LENGTH_SHORT).show();
                }
                else if (!isEmailValid(email)) {
                    Toast.makeText(CreateAccount.this, "Please enter a valid Email",Toast.LENGTH_SHORT).show();
                }
                else if (!isValidUsername(username)) {
                    Toast.makeText(CreateAccount.this, "Please enter a Username",Toast.LENGTH_SHORT).show();
                }
            }
        });
        have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccount.this,
                        MainActivity.class));
            }
        });
    }

    public static boolean isValidPassword(String password) {
        // Minimum length requirement
        int minLength = 8;

        // Check if the password meets the minimum length
        if (password.trim().length() < minLength) {
            return false;
        }

        // Check if the password contains both letters and numbers using regular expressions
        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d).+$");
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
    public static boolean isValidUsername(String username) {
        // Check if the username is not null and not empty after trimming leading/trailing spaces

        //check if username is in database
        if(inUserDatabase(username));

        return username != null && !username.trim().isEmpty();
    }

    public static boolean inUserDatabase(String username) {
        String url = "http://coms-309-022.class.las.iastate.edu:8080/";

        return false;
    }
    public static boolean isEmailValid(String email) {
        // Define a simple pattern for a valid email address
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the pattern
        Pattern pattern = Pattern.compile(emailPattern);

        // Create a matcher with the provided email
        Matcher matcher = pattern.matcher(email);

        if(inEmailDatabase(email));

        // Check if the email matches the pattern
        return matcher.matches();
    }

    public static boolean inEmailDatabase(String email) {
        String url = "http://coms-309-022.class.las.iastate.edu:8080/";

        return false;
    }

    public static void setUserBackend(String username, String password, String email) {
        String url = "http://coms-309-022.class.las.iastate.edu:8080/";
    }
}
