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

public class ShopActivity extends AppCompatActivity {
    private Navigation navigationHelper;
    private Button orange, purple, lightblue, yellow, magenta, green, white;
    private TextView balance;
    private int bal = 10000;
    private String User = "Carter", selected, purchased = "false, false, false, false, false, false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        initializeViews();
        setBalance();
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

    private void setButtonClickListener(Button button, int cost, String color) {
        button.setOnClickListener(v -> buyColor(button, cost, color));
    }

    private void buyColor(Button button, int cost, String color) {
        toastSelected(color);
        setSelectColor(color);
        getPurchased();
        setPurchased(color);

    }
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
    public boolean checkPoints(int neededPts) {
        if(bal < neededPts) {
            //throw some kinda indicator
            return false;
        }
        else {

            return true;
        }
    }
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
    public void setBalance() {
        balance = findViewById(R.id.balance);
        balance.setText("Balance: " + getBalance(User));
    }
    public int getBalance(String user) {
        //value should be saved to bal at end
        return bal;
    }
    private void setBoughtIfTrue(String value, Button button, String color) {
        if (Boolean.parseBoolean(value.trim())) {
            setBought(button, color);
        }
    }

    private void setBought(Button button, String color) {
        button.setText("Select");
    }

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