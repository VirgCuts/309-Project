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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * MainActivity is the entry point for the application.
 * It sets up the user interface and navigation helpers, and prompts the user
 * for a username if it is not set.
 */
public class MainActivity extends AppCompatActivity {
    private Navigation navigationHelper;
    private AlertDialog dialog;
    private static final String PREFS_NAME = "LeaderboardPrefs";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static EditText usernameText, passwordText;
    private static Button login, forgotPW, createAcct;


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
        setContentView(R.layout.login);

        usernameText = findViewById(R.id.username_input);
        passwordText = findViewById(R.id.password_input);
        login = findViewById(R.id.login);
        forgotPW = findViewById(R.id.forgotPW);
        createAcct = findViewById(R.id.createAcct);


        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String user = prefs.getString(USERNAME_KEY,null);
        if (user != null && !user.isEmpty()) {
            startActivity(new Intent(MainActivity.this, LobbyActivity.class));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();

                if(isValidPassword(password) && isValidUsername(username)) {
                    inDatabase(username,password, new DatabaseCallback() {
                        @Override
                        public void onResult(boolean isUser) {
                            // Handle the result here
                            Log.d("ISUSER", Boolean.toString(isUser));

                            // Now you can use the boolean result as needed
                            if(isUser) {
                                prefs.edit().putString(USERNAME_KEY, usernameText.getText().toString()).apply();
                                prefs.edit().putString(PASSWORD_KEY, passwordText.getText().toString()).apply();
                                startActivity(new Intent(MainActivity.this,
                                        LobbyActivity.class));
                            }
                            else {
                                Toast.makeText(MainActivity.this, "User does not exist",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(MainActivity.this, "Please enter a Correct Username and Password (must contain letters and numbers pw > 8",Toast.LENGTH_SHORT).show();
                }
            }
        });
        forgotPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPWDialog();
            }
        });

        createAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        CreateAccount.class));
            }
        });
    }
    public boolean inDatabase(String username, String password,DatabaseCallback callback) {
        String url = "http://coms-309-022.class.las.iastate.edu:8080/users/"+username+"/"+password;
        boolean isUser = false;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("boolean", response);
                        boolean isUser = Boolean.parseBoolean(response);
                        callback.onResult(isUser);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("GETURERR",error.toString());
                callback.onResult(false); // Handle error case
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
        Log.d("ISUSER",Boolean.toString(isUser));
        return isUser;
    }
    interface DatabaseCallback {
        void onResult(boolean isUser);
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
    private void forgotPWDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Email");

        final EditText inputField = new EditText(this);
        inputField.setInputType(InputType.TYPE_CLASS_TEXT);
        inputField.setHint("Enter Email Associated with Account");
        builder.setView(inputField);

        builder.setPositiveButton("Enter", (dialog, which) -> {
            String email = inputField.getText().toString();
            if(isEmailValid(email)) {
                getPassword(email);
                //SEND EMAIL TO BACKEND HERE
                Toast.makeText(MainActivity.this,"Password request sent",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this,"Error: Not a valid email",Toast.LENGTH_SHORT).show();
            }

        });

        builder.show();
    }
    private void sendEmail(String email, String password) {
        try {
            Log.d("EmailCall","Should be sending email");
            String stringSenderEmail = "immaculatetaste595@gmail.com";
            String stringReceiverEmail = email;
            String stringPasswordSenderEmail = "oxxz bglo dhmm xedx";

            Properties properties=new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            Session session=Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("DO NOT REPLY: Immaculate Music Password");
            mimeMessage.setText("Here is your account password DO NOT SHARE THIS: "+ password);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private void getPassword(String email) {
        Log.d("EMAIL",email);
        //http://coms-309-022.class.las.iastate.edu:8080/forgot/cvcuts@iastate.edu
        String url = "http://coms-309-022.class.las.iastate.edu:8080/forgot/"+email;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("PWREQ", response);
                        sendEmail(email,response);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PWERR",error.toString());
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
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
        passwordEditText.setHint("Password (Longer than 8 only letters and numbers)");
        layout.addView(passwordEditText);

        builder.setView(layout);

        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve the entered username and password
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Save the username
                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                if (!isValidPassword(password)) {
                    // Show an error message for invalid password
                    Toast.makeText(MainActivity.this, "Invalid password. Please try again.", Toast.LENGTH_SHORT).show();
                    // Don't close the dialog
                }

                // Continue with the login process
                prefs.edit().putString(USERNAME_KEY, username).apply();
                prefs.edit().putString(PASSWORD_KEY, password).apply();
            }
        });
        // Set up the negative button (Cancel)
        builder.setNegativeButton("Play as Guest", (dialog, which) -> {
            String inputUsername = "Guest";
            // Save the username
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            prefs.edit().putString(USERNAME_KEY, inputUsername).apply();
            prefs.edit().putString(PASSWORD_KEY, "null").apply();
        });
        // Create and show the dialog
        builder.create().show();
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
            navigationHelper = new Navigation(this); // 'this' refers to MainActivity which is a Context
            navigationHelper.setupNavigation();
            // Continue with any other actions you need to do with the username
        });
        builder.setNegativeButton("Play as Guest", (dialog, which) -> {
            String inputUsername = "Guest";
            // Save the username
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            prefs.edit().putString(USERNAME_KEY, inputUsername).apply();
            navigationHelper = new Navigation(this); // 'this' refers to MainActivity which is a Context
            navigationHelper.setupNavigation();
        });
        builder.show();

    }
    @Override
    protected void onDestroy() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        super.onDestroy();
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
        // Check if the password contains both letters and numbers using regular expressions
        if(username.trim().length() != 0) {
            return true;
        }
        return false;
    }
    public static boolean isEmailValid(String email) {
        // Define a simple pattern for a valid email address
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the pattern
        Pattern pattern = Pattern.compile(emailPattern);

        // Create a matcher with the provided email
        Matcher matcher = pattern.matcher(email);

        // Check if the email matches the pattern
        return matcher.matches();
    }
}

