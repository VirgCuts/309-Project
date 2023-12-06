package com.example.sumon.androidvolley;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * The ShopActivity class represents the main activity for the shopping functionality
 * in the Android application. Users can buy and select colors for their points.
 *
 */
public class ShopActivity extends AppCompatActivity {
    private Navigation navigationHelper;
    private Button orange, purple, lightblue, yellow, magenta, green, white;
    private TextView balance;
    private int bal = 10000;
    private String User = "Carter", selected, purchased = "false, false, false, false, false, false";

    /**
     * Called when the activity is starting. This is where most initialization
     * should go: calling setContentView(int) to inflate the activity's UI,
     * initializing views, setting up navigation, and initializing button click listeners.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        initializeViews();
        //setBalance();
        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();

        setButtonClickListener(orange, 100, "orange");
        setButtonClickListener(purple, 300, "purple");
        setButtonClickListener(lightblue, 500, "lightblue");
        setButtonClickListener(yellow, 600, "yellow");
        setButtonClickListener(magenta, 900, "magenta");
        setButtonClickListener(green, 1000, "green");
        setButtonClickListener(white, 0, "none");

        getSelectColor();
        getPurchased();
        long delayMillis = 500;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateButtons();
                toastSelected(selected);
            }
        }, delayMillis);
    }
    /**
     * Initializes the UI views such as buttons and text views.
     */
    private void initializeViews() {
        orange = findViewById(R.id.orange);
        purple = findViewById(R.id.purple);
        lightblue = findViewById(R.id.lightblue);
        yellow = findViewById(R.id.yellow);
        magenta = findViewById(R.id.magenta);
        green = findViewById(R.id.green);
        white = findViewById(R.id.white);
        balance = findViewById(R.id.balance);
    }
    /**
     * Sets the click listener for the specified button, allowing users to buy a color.
     *
     * @param button The button to set the click listener for.
     * @param cost   The cost associated with buying the color.
     * @param color  The color being purchased.
     */
    private void setButtonClickListener(Button button, int cost, String color) {
        button.setOnClickListener(v -> buyColor(button, cost, color));
    }
    /**
     * Handles the color purchase when a button is clicked.
     *
     * @param button The button clicked.
     * @param cost   The cost of the color.
     * @param color  The color being purchased.
     */
    private void buyColor(Button button, int cost, String color) {
        toastSelected(color);
        setSelectColor(color);
        getPurchased();
        setPurchased(color);

    }
    /**
     * Retrieves the list of purchased colors for the user from the server.
     */
    public void getPurchased() {
        String url = "http://coms-309-022.class.las.iastate.edu:8080/inventory/"+User;

        // StringRequest for fetching a string response from the given URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("GETPUR", response);
                        purchased = response;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("GETURERR",error.toString());
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
    /**
     * Sets the purchased status for a specific color on the server.
     *
     * @param color The color for which the purchased status is being set.
     */
    public void setPurchased(String color) {
        String url = "http://coms-309-022.class.las.iastate.edu:8080/inventory/"+User+"/" + color;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SETPUR",response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("SETPURERR",error.toString());
                    }
                }
        );
        Volley.newRequestQueue(this).add(request);
    }
    /**
     * Retrieves the currently selected color for the user from the server.
     */
    public void getSelectColor() {
        String url = "http://coms-309-022.class.las.iastate.edu:8080/gameColor/"+User;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SINGLEGET",response.toString());
                        String value = response.toString();
                        String[] splitValue = value.split(":");
                        value = splitValue[1];
                        value = value.replace("}", "");
                        value = value.replace("\"", "");
                        Log.d("GET", value);
                        selected = value;

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERRGET",error.toString());

                    }
                }
        );
        Volley.newRequestQueue(this).add(request);
    }

    /**
     * Sets the selected color for the user on the server.
     *
     * @param color The color to set as the selected color.
     */
    public void setSelectColor(String color) {
        String url = "http://coms-309-022.class.las.iastate.edu:8080/gameColor/"+User+"/" + color;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SET",response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERRGET",error.toString());

                    }
                }
        );
        Volley.newRequestQueue(this).add(request);
    }
    /**
     * Updates the state of buttons based on the purchased colors.
     */
    private void updateButtons() {
        String[] splitPur = purchased.split(",");
        Log.d("UPDATE", Arrays.toString(splitPur));

        setBoughtIfTrue(splitPur[0], orange, "orange");
        setBoughtIfTrue(splitPur[1], purple, "purple");
        setBoughtIfTrue(splitPur[2], lightblue, "lightblue");
        setBoughtIfTrue(splitPur[3], yellow, "yellow");
        setBoughtIfTrue(splitPur[4], magenta, "magenta");
        setBoughtIfTrue(splitPur[5], green, "green");
    }

    /**
     * Sets the text of a button to "Select" if the corresponding color has been purchased.
     *
     * @param value  The purchased status as a string.
     * @param button The button to set if purchased.
     * @param color  The color associated with the button.
     */
    private void setBoughtIfTrue(String value, Button button, String color) {
        if (Boolean.parseBoolean(value.trim())) {
            setBought(button, color);
        }
    }
    /**
     * Sets a button as "Select" to indicate it has been purchased.
     *
     * @param button The button to set as purchased.
     * @param color  The color associated with the button.
     */
    private void setBought(Button button, String color) {
        button.setText("Select");
    }
    /**
     * Displays a Snackbar with the selected color information.
     *
     * @param color The selected color.
     */
    private void toastSelected(String color) {
        View view = findViewById(R.id.shop);
        Snackbar snackbar = Snackbar.make(view, "Your Current Color is - " + color, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}