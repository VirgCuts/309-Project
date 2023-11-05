package com.example.sumon.androidvolley;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ReportUserActivity extends AppCompatActivity {

    private EditText etReportBody;
    private Button btnSendReport;
    private Navigation navigationHelper;
    private static final String BASE_URL = "http://coms-309-022.class.las.iastate.edu:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_user_layout);


        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();


        etReportBody = findViewById(R.id.etReportBody);
        btnSendReport = findViewById(R.id.btnSendReport);

        btnSendReport.setOnClickListener(view -> {

            String reportMessage = etReportBody.getText().toString().trim();
            if (!reportMessage.isEmpty()) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String username = extras.getString(ChatActivity.EXTRA_USERNAME);
                    String reportedMessageContent = extras.getString(ChatActivity.EXTRA_MESSAGE_CONTENT);
                        sendReport(username, reportedMessageContent, reportMessage);
                    // Use these values to display in the UI or send a report
                }

            } else {
                etReportBody.setError("Please enter your report details.");
            }
        });
    }

    private void sendReport(String username, String reportedMessageContent, String reportContent) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = BASE_URL + "/report/" + username + "/" + reportedMessageContent;

        // Create a request with the required parameters
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(this, "Report has been successfully sent", Toast.LENGTH_SHORT).show();

                    // Navigate back to the home screen
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                },
                error -> {
                    // Handle error here
                    Toast.makeText(this, "Failed to send report", Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            public byte[] getBody() {
                // Return the content of the report as byte array
                return reportContent.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                // Specify your content type here, for example, "text/plain; charset=utf-8"
                return "text/plain; charset=utf-8";
            }

            // Optionally, override getHeaders if you need to set any headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                // headers.put("Content-Type", "application/json"); // If you are sending JSON
                // headers.put("Authorization", "Bearer " + YOUR_TOKEN); // If you need authorization
                return headers;
            }
        };

        // Set retry policy to override Volley's default retry policy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000, // timeout in milliseconds
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // max number of retries
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); // backoff multiplier

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}

