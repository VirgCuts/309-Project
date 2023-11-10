package com.example.sumon.androidvolley;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.widget.PopupMenu;
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
/**
 * ChatActivity provides the user interface and logic for a chat application.
 * It handles WebSocket connections for real-time messaging, sending and receiving messages,
 * and displaying chat history. This activity also includes UI elements for connecting,
 * sending messages, and displaying chat messages.
 */
public class ChatActivity extends AppCompatActivity implements WebSocketListener{




    private String BASE_URL = "ws://coms-309-022.class.las.iastate.edu:8080/chat/";



    private Button connectBtn, sendBtn, btnGetBanCount;
    private EditText usernameEtx, msgEtx, userBan;
    private TextView msgTv, tvBanCount;

    private Navigation navigationHelper;

    private LinearLayout messagesContainer;

    private TextView currentlyHighlighted;

    public static final String EXTRA_USERNAME = "EXTRA_USERNAME";
    public static final String EXTRA_MESSAGE_CONTENT = "EXTRA_MESSAGE_CONTENT";
    /**
     * Initializes the activity. This method sets up the UI components and event listeners.
     * @param savedInstanceState If the activity is being re-initialized after being shut down,
     *                           this Bundle contains the most recent data provided by onSaveInstanceState.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom_layout);

        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();

        /* initialize UI elements */
        messagesContainer = findViewById(R.id.chat_container);
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



        messagesContainer.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (currentlyHighlighted != null) {
                    currentlyHighlighted.setBackgroundResource(R.drawable.textview_border);
                    currentlyHighlighted = null;
                }
            }
            return false; // Return false to allow normal handling of the touch event
        });

    }
    /**
     * Handles options item selected events.
     * @param item The menu item that was selected.
     * @return boolean Returns true if the event was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    /**
     * Adds a message to the chat view.
     * @param message The message text to be added.
     */
    private void addMessageToView(String message) {
        TextView messageView = new TextView(this);
        messageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        messageView.setText(message);
        messageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        messageView.setTextColor(Color.BLACK);
        messageView.setBackgroundResource(R.drawable.textview_border);
        // Add other styling to your messageView as needed
        messageView.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_HOVER_ENTER:
                        // Show the popup when the mouse enters the view
                        showPopup(v);
                        break;
                    case MotionEvent.ACTION_HOVER_EXIT:
                        // Optionally handle the mouse exiting the view
                        break;
                }
                return true;
            }
        });
        messageView.setOnLongClickListener(v -> {
            if (currentlyHighlighted != null) {
                currentlyHighlighted.setBackgroundResource(R.drawable.textview_border);
            }
            // Highlight the current view and update the reference
            v.setBackgroundColor(Color.parseColor("#ADD8E6")); // Light blue color
            currentlyHighlighted = (TextView) v;
            showPopup(v); // Show the popup when the user long presses the TextView
            return true; // Return true to indicate that the event has been consumed.
        });
        messagesContainer.addView(messageView); // Add the TextView to your LinearLayout
    }
    /**
     * Displays a popup menu for the given view.
     * @param anchorView The view for which the popup menu is to be shown.
     */
    private void showPopup(View anchorView) {
        PopupMenu popupMenu = new PopupMenu(this, anchorView);
        popupMenu.getMenu().add("Like message");
        popupMenu.getMenu().add("Report user");

        popupMenu.setOnMenuItemClickListener(item -> {
            String title = item.getTitle().toString();
            if ("Report user".equals(title)) {
                String messageContent = ((TextView) anchorView).getText().toString();
                Intent intent = new Intent(this, ReportUserActivity.class);
                // You can put extra data into the intent if needed, for example, the user ID to report
                intent.putExtra(EXTRA_USERNAME, usernameEtx.getText().toString());
                intent.putExtra(EXTRA_MESSAGE_CONTENT, messageContent);
                startActivity(intent);
            }
            return true;
        });

        popupMenu.show();
    }
    /**
     * Callback for receiving messages from the WebSocket.
     * @param message The message received from the WebSocket.
     */
    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            // Check if the message contains the history
            if (message.startsWith("History\n")) {
                // Extract the history part by removing "HISTORY\n" and parse it
                String history = message.substring("History\n".length());
                parseAndDisplayHistory(history);
            }
            else if(message.startsWith("Welcome to the chat")){
                String s = msgTv.getText().toString();
                msgTv.setText(s + message + "\n");
            }
            else if(message.startsWith("User: ")){
                String s = msgTv.getText().toString();
                msgTv.setText(s + message + "\n");
            }else {
                // It's a regular message
                addMessageToView(message);
            }
        });
    }
    /**
     * Callback for WebSocket closure.
     * @param code The closure code.
     * @param reason The reason for the closure.
     * @param remote Flag indicating if the closure was initiated by the remote host.
     */
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }
    /**
     * Parses and displays chat history.
     * @param history The chat history in String format.
     */
    private void parseAndDisplayHistory(String history) {
        // Split the history into individual messages
        String[] messages = history.split("\n");
        for (String msg : messages) {
            // Add each message to the view
            addMessageToView(msg);
        }
        // Scroll to the bottom of the chat to show the latest messages
        scrollToBottom();
    }

    /**
     * Scrolls the chat view to the bottom.
     */
    private void scrollToBottom() {
        ScrollView scrollView = findViewById(R.id.chat_scrollview); // Replace with your ScrollView ID if different
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }
    /**
     * Callback for WebSocket connection opening.
     * @param handshakedata The handshake data from the server.
     */
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
        runOnUiThread(() -> {
            // Here you can display a message like "Connected to server" or perform other UI updates
            // as soon as the WebSocket connection is established.
        });
    }
    /**
     * Callback for WebSocket errors.
     * @param ex The exception that occurred.
     */
    @Override
    public void onWebSocketError(Exception ex) {}

}

