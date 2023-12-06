package com.example.sumon.androidvolley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
/**
 * Activity class that handles administration actions such as displaying reports
 * and showing the ban count for a specific user. It uses the Volley library
 * for network requests to a specified base URL.
 */
public class AdminActivity extends AppCompatActivity {
    private Navigation navigationHelper;
    private EditText etUsernameForReport, username_ban;
    private LinearLayout containerReports;
    private TextView tvBanCount;
    private String baseUrl = "http://coms-309-022.class.las.iastate.edu:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_activity);

        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();


        Button btnGetAllReports = findViewById(R.id.btnGetAllReports);
        Button btnGetUsersReports = findViewById(R.id.btnGetUsersReports);
        etUsernameForReport = findViewById(R.id.etUsernameForReport);
        containerReports = findViewById(R.id.containerReports);
        Button BtnBanCount = findViewById(R.id.ban_count);
        tvBanCount = findViewById(R.id.tvBanCount);
        username_ban = findViewById(R.id.username_inputban);


        btnGetAllReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllReports();
            }
        });

        btnGetUsersReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsernameForReport.getText().toString();
                if (!username.isEmpty()) {
                    getUsersReports(username);
                } else {
                    etUsernameForReport.setError("Please enter a username");
                }
            }
        });

        BtnBanCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_ban.getText().toString();
                if (!username.isEmpty()) {
                    getBanCountForUser(username);
                } else {
                    username_ban.setError("Please enter a username");
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    /**
     * Fetches the ban count for a given user by sending a GET request.
     *
     * @param username The username whose ban count is to be retrieved.
     */
    private void getBanCountForUser(String username) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-022.class.las.iastate.edu:8080/banStrikes/" + username;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Handle the response containing a JSON array of reports for the user
                    tvBanCount.setText(response);

                }, error -> {
            // Handle error

        });

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(stringRequest);
    }


    /**
     * Fetches all reports from the server by sending a GET request.
     */
    private void getAllReports() {
        String url = baseUrl + "/reports";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Handle the response containing a JSON array of reports
                    displayReports(response);
                }, error -> {

        });

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(stringRequest);
    }
    /**
     * Fetches reports specific to a user from the server by sending a GET request.
     *
     * @param reportedUsername The username for which the reports need to be fetched.
     */
    private void getUsersReports(String reportedUsername) {
        String url = baseUrl + "/reports/" + reportedUsername;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Handle the response containing a JSON array of reports for the user
                    displayReports(response);
                }, error -> {
            // Handle error

        });

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(stringRequest);
    }
    /**
     * Parses the JSON response containing reports and displays them in the UI.
     *
     * @param jsonReports The JSON string containing an array of reports.
     */
    private void displayReports(String jsonReports) {
        try {
            // Convert the JSON response into a JSONArray
            JSONArray reportsArray = new JSONArray(jsonReports);

            // Clear the container before adding new report views
            containerReports.removeAllViews();

            // Iterate over the reports and create a TextView for each one
            for (int i = 0; i < reportsArray.length(); i++) {
                JSONObject report = reportsArray.getJSONObject(i);
                TextView reportView = new TextView(this);
                reportView.setText(report.toString()); // You might want to format this properly
                reportView.setPadding(0, 0, 0, 10); // Set some bottom padding for each report view
                containerReports.addView(reportView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
