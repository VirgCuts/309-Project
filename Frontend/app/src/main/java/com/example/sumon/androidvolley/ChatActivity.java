package com.example.sumon.androidvolley;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.java_websocket.handshake.ServerHandshake;

import java.util.ArrayList;
import java.util.List;

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
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages = new ArrayList<>();

    private LinearLayout messagesContainer;

    private boolean isWebSocketConnected;

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
        connectBtn = (Button) findViewById(R.id.connect_button);
        sendBtn = (Button) findViewById(R.id.send_button);
        usernameEtx = (EditText) findViewById(R.id.username_input);
        msgEtx = (EditText) findViewById(R.id.message_input);

        recyclerView = findViewById(R.id.chat_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(chatMessages);
        recyclerView.setAdapter(chatAdapter);

        /* connect button listener */
        connectBtn.setOnClickListener(view -> {

            String serverUrl = BASE_URL + usernameEtx.getText().toString();
            Log.d("URL", "URL is " + serverUrl);
            // Establish WebSocket connection and set listener
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(ChatActivity.this);
            isWebSocketConnected = true;
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
        chatMessages.add(new ChatMessage(message));
        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
        scrollToBottom();
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
                String history = message.substring("History\n".length());
                parseAndDisplayHistory(history);
            } else {
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
            addMessageToView("---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }
    /**
     * Parses and displays chat history.
     * @param history The chat history in String format.
     */
    private void parseAndDisplayHistory(String history) {
        String[] messages = history.split("\n");
        for (String msg : messages) {
            addMessageToView(msg);
        }
        scrollToBottom();
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Disconnect WebSocket when the activity is no longer in the foreground
        if (isWebSocketConnected) {
            WebSocketManager.getInstance().disconnectWebSocket();
            WebSocketManager.getInstance().removeWebSocketListener();
            isWebSocketConnected = false;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Additional check in onDestroy in case the activity is destroyed without onPause being called
        if (isWebSocketConnected) {
            WebSocketManager.getInstance().disconnectWebSocket();
            WebSocketManager.getInstance().removeWebSocketListener();
            isWebSocketConnected = false;
        }
    }


        /**
         * Scrolls the chat view to the bottom.
         */
    private void scrollToBottom() {
        recyclerView.scrollToPosition(chatMessages.size() - 1);
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
    public class ChatMessage {
        private final String message;

        public ChatMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
    public class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;
        private View currentlyHighlighted;

        public ChatViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.chatMessage);
            setListeners();
        }

        private void setListeners() {
            messageTextView.setOnHoverListener((v, event) -> {
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
            });

            messageTextView.setOnLongClickListener(v -> {
                if (currentlyHighlighted != null) {
                    currentlyHighlighted.setBackgroundResource(R.drawable.textview_border);
                }
                // Highlight the current view and update the reference
                v.setBackgroundColor(Color.parseColor("#ADD8E6")); // Light blue color
                currentlyHighlighted = v;
                showPopup(v); // Show the popup when the user long presses the TextView
                return true; // Return true to indicate that the event has been consumed.
            });
        }

        private void showPopup(View anchorView) {
            PopupMenu popupMenu = new PopupMenu(anchorView.getContext(), anchorView);
            popupMenu.getMenu().add("Like message");
            popupMenu.getMenu().add("Report user");

            popupMenu.setOnMenuItemClickListener(item -> {
                String title = item.getTitle().toString();
                if ("Report user".equals(title)) {
                    String messageContent = ((TextView) anchorView).getText().toString();
                    Intent intent = new Intent(ChatActivity.this, ReportUserActivity.class);
                    // You can put extra data into the intent if needed, for example, the user ID to report
                    intent.putExtra(EXTRA_USERNAME, usernameEtx.getText().toString());
                    intent.putExtra(EXTRA_MESSAGE_CONTENT, messageContent);
                    ChatActivity.this.startActivity(intent);
                }
                return true;
            });

            popupMenu.show();
        }

    }

    public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
        private final List<ChatMessage> chatMessages;

        public ChatAdapter(List<ChatMessage> chatMessages) {
            this.chatMessages = chatMessages;
        }

        @Override
        public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_item, parent, false);
            return new ChatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ChatViewHolder holder, int position) {
            ChatMessage message = chatMessages.get(position);
            holder.messageTextView.setText(message.getMessage());
            // Reset any special styling from previous uses of this holder
            holder.messageTextView.setBackgroundResource(R.drawable.textview_border);
            holder.messageTextView.setTextColor(Color.BLACK);
            holder.messageTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }

        @Override
        public int getItemCount() {
            return chatMessages.size();
        }
    }

}


