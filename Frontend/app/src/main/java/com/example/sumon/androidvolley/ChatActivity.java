package com.example.sumon.androidvolley;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.java_websocket.handshake.ServerHandshake;

public class ChatActivity extends AppCompatActivity implements WebSocketListener{




    private String BASE_URL = "ws://coms-309-022.class.las.iastate.edu:8080/chat/";



    private Button connectBtn, sendBtn, btnGetBanCount;
    private EditText usernameEtx, msgEtx, userBan;
    private TextView msgTv, tvBanCount;

    private Navigation navigationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom_layout);

        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();

        /* initialize UI elements */
        connectBtn = (Button) findViewById(R.id.connect_button);
        sendBtn = (Button) findViewById(R.id.send_button);
        usernameEtx = (EditText) findViewById(R.id.username_input);
        msgEtx = (EditText) findViewById(R.id.message_input);
        msgTv = (TextView) findViewById(R.id.chatMessage);

        /* connect button listener */
        connectBtn.setOnClickListener(view -> {

            String serverUrl = BASE_URL + usernameEtx.getText().toString();
            Log.d("URL", "URL is " + serverUrl);
            // Establish WebSocket connection and set listener
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(ChatActivity.this);
        });

        /* send button listener */
        sendBtn.setOnClickListener(v -> {
            try {

                // send message
                WebSocketManager.getInstance().sendMessage(msgEtx.getText().toString());
            } catch (Exception e) {
                Log.d("ExceptionSendMessage:", e.getMessage().toString());
            }
        });
        btnGetBanCount = (Button) findViewById(R.id.ban_count);
        userBan = (EditText) findViewById(R.id.username_inputban);
        tvBanCount = (TextView) findViewById(R.id.tvBanCount);

        // Set the listener for the get ban count button
        btnGetBanCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the username from the input field
                String username = userBan.getText().toString();
                // Make the GET request to fetch ban count
                getBanCountForUser(username);
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    // Method to make a GET request to the backend
    private void getBanCountForUser(String username) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://http://coms-309-022.class.las.iastate.edu:8080/banStrikes/" + username;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response (ban count) on the TextView
                        tvBanCount.setText("Ban Count: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvBanCount.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    @Override
    public void onWebSocketMessage(String message) {
        /**
         * In Android, all UI-related operations must be performed on the main UI thread
         * to ensure smooth and responsive user interfaces. The 'runOnUiThread' method
         * is used to post a runnable to the UI thread's message queue, allowing UI updates
         * to occur safely from a background or non-UI thread.
         */
        runOnUiThread(() -> {
            // Assuming that the backend sends a JSON string with a "type" field
            // Check if the message contains "ban" information
            if (message.contains("\"type\":\"ban\"")) {
                // Handle the ban message, e.g., disable the send button
                sendBtn.setEnabled(false);
                // Possibly parse the JSON to extract the actual ban message
            }

            // Display the received message in the chat
            String currentText = msgTv.getText().toString();
            msgTv.setText(currentText + "\n" + message);
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    @Override
    public void onWebSocketError(Exception ex) {}

}

