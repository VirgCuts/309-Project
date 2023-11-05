package com.example.sumon.androidvolley;

import android.os.Bundle;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopActivity extends AppCompatActivity {
    private Navigation navigationHelper;
    private Button orange, purple, lightblue, yellow, magenta, green, white;
    private TextView balance;
    private int bal = 10000;
    private String User = "Carter",selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();

        setBalance();
        orange = findViewById(R.id.orange);
        purple = findViewById(R.id.purple);
        lightblue = findViewById(R.id.lightblue);
        yellow = findViewById(R.id.yellow);
        magenta = findViewById(R.id.magenta);
        green = findViewById(R.id.green);
        white = findViewById(R.id.white);
        selected = getSelectColor();
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyColor(orange, 100, "orange");
            }
        });
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyColor(purple, 300, "purple");
            }
        });
        lightblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyColor(lightblue, 500, "lightblue");
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyColor(yellow, 600, "yellow");
            }
        });
        magenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyColor(magenta, 900, "magenta");
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyColor(green, 1000, "green");
            }
        });
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyColor(white, 0, "none");
            }
        });
    }
    public void purchasedButtons(Array colorArr) {
        //orange,purple,lightblue,yellow,magenta,green
        String input = "false, false, true, true, false, false";
        String[] substrings = input.split(",");
        boolean[] booleanArray = new boolean[substrings.length];

        for (int i = 0; i < substrings.length; i++) {
            booleanArray[i] = Boolean.parseBoolean(substrings[i].trim());
        }

        // Print the boolean array to verify the result
        for (boolean value : booleanArray) {
            Log.d("VAL",Boolean.toString(value));
        }
    }
    //runs on startup to see if a color has been priorly purchased
    public void getPurchased() {
        String url = "http://coms-309-022.class.las.iastate.edu:8080/inventory/"+User;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("GETPUR",response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GETURERR",error.toString());
                    }
                }
        );
        Volley.newRequestQueue(this).add(request);
    }
    //sends color to backend to notify that color is now owned by user
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
    //sends selected color to backend to notify that it is now the selected color
    public String getSelectColor() {
        final String[] value = {"white"};
      String url = "http://coms-309-022.class.las.iastate.edu:8080/gameColor/"+User;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SINGLEGET",response.toString());
                        value[0] = response.toString();
                        String[] splitValue = value[0].split(":");
                        value[0] = splitValue[1];
                        value[0] = value[0].replace("}", "");
                        Log.d("GET", value[0]);

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
        return value[0];
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
    //calls to backend to get the point balance associated with user
    //sends user to backend need to get back string with a point val to Intparse
    public int getBalance(String user) {

        //value should be saved to bal at end
        return bal;
    }
    public void setBalance() {
        balance = findViewById(R.id.balance);
        balance.setText("Balance: " + getBalance(User));
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
    public void buyColor(Button button, int cost, String color) {
        if(checkPoints(cost)) {
            changeBuyToSelect(button, color);
            //addColorToUser(color);
            setSelectColor(color);
            getPurchased();
            setPurchased(color);
            if(button.getText().toString().equals("SELECT")) {

            }
        }


    }
    public void changeBuyToSelect(Button button, String color) {
        View view = findViewById(R.id.shop);
        Snackbar snackbar = Snackbar.make(view, "Your Current Color is - " + color, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
