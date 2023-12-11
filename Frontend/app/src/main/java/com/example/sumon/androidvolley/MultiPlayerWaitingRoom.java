package com.example.sumon.androidvolley;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.java_websocket.handshake.ServerHandshake;

import java.util.ArrayList;
import java.util.List;

public class MultiPlayerWaitingRoom extends AppCompatActivity implements WebSocketListener {

    private Button btnReadyUp;
    private TextView tvReadyUsers, tvSpectators;
    private int readyUsers = 0; // This should be updated based on actual data
    private int spectators = 0; // This should be updated based on actual data
    private boolean isPlayerReady = false;
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages = new ArrayList<>();
    private EditText messageInput;
    private Button sendButton;
    private String BASE_URL = "ws://coms-309-022.class.las.iastate.edu:8080/lobby";
    private String username = "";
    private int lobbyNum;
    private boolean isWebSocketConnected;
    private String matchType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplayer_waiting_room);
        btnReadyUp = findViewById(R.id.btnReadyUp);
        tvReadyUsers = findViewById(R.id.tvReadyUsers);
        // Initialize chat components
        chatRecyclerView = findViewById(R.id.chat_recyclerview);
        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(chatMessages);
        chatRecyclerView.setAdapter(chatAdapter);
        Intent intent = getIntent();
        if (intent != null) {
            lobbyNum = intent.getIntExtra("LOBBY_NAME",0);
            username = intent.getStringExtra("USERNAME");
            matchType = intent.getStringExtra("MATCH_TYPE");
        }



        sendButton.setOnClickListener(v -> {
            try {
                WebSocketManager.getInstance().sendMessage(messageInput.getText().toString());
            } catch (Exception e) {
                Log.d("ExceptionSendMessage:", e.getMessage().toString());
            }
        });

        btnReadyUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the ready-up logic here
                setPlayerReady();
            }
        });
        Button restartButton = findViewById(R.id.btnExit);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebSocketManager.getInstance().disconnectWebSocket();
                WebSocketManager.getInstance().removeWebSocketListener();
                sendUnreadyMessage();
                startActivity(new Intent(MultiPlayerWaitingRoom.this,
                        MultiPlayerLobbyActivity.class));
            }
        });
        String serverUrl = BASE_URL + "/" + lobbyNum + "/" + username;//Will need to change once log-in system works
        // Establish WebSocket connection and set listener
        Log.d("URL",serverUrl);
        WebSocketManager.getInstance().connectWebSocket(serverUrl);
        WebSocketManager.getInstance().setWebSocketListener(MultiPlayerWaitingRoom.this);
        isWebSocketConnected = true;
        // Update the UI with the current number of ready users and spectators
        updateUI();
    }


    @Override
    protected void onPause() {
        super.onPause();
        // Disconnect WebSocket when the activity is no longer in the foreground
        if (isWebSocketConnected) {
            sendUnreadyMessage();
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
            sendUnreadyMessage();
            WebSocketManager.getInstance().disconnectWebSocket();
            WebSocketManager.getInstance().removeWebSocketListener();
            isWebSocketConnected = false;
        }
    }

    private void setPlayerReady() {
        if (!isPlayerReady) {
            // Toggle player's ready state
            isPlayerReady = true;
            readyUsers++; // Increment the count of ready users
            updateUI();

            // Change button appearance to indicate the player is ready and disable it
            btnReadyUp.setBackgroundColor(Color.TRANSPARENT); // Set the button color to transparent
            btnReadyUp.setTextColor(Color.BLACK); // Set the text color to black (or choose another visible color)
            btnReadyUp.setText("Ready!"); // Change button text to "Ready"
            btnReadyUp.setEnabled(false); // Disable the button
            sendReadyMessage();
        }
    }


    private void updateUI() {
        // Update the number of ready users and spectators displayed in the TextViews
        tvReadyUsers.setText(getString(R.string.ready_users, readyUsers));

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
    private void sendReadyMessage() {
        try {
            String message = matchType.equals("2v2") ? "@ready2" : "@ready";
            WebSocketManager.getInstance().sendMessage(message);
        } catch (Exception e) {
            Log.e("WebSocket", "Error sending ready message", e);
        }
    }

    private void sendUnreadyMessage() {
        try {
            String message = matchType.equals("2v2") ? "@unready2" : "@unready";
            WebSocketManager.getInstance().sendMessage(message);
        } catch (Exception e) {
            Log.e("WebSocket", "Error sending unready message", e);
        }
    }

    /**
     * Callback for receiving messages from the WebSocket.
     * @param message The message received from the WebSocket.
     */
    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            Log.d("WebMessage",message);
            // Check if the message indicates another player's readiness
            if(matchType =="2v2"){
                if (message.equals("@ready2")) {
                    readyUsers++;
                    updateUI();
                } else if (message.equals("@unready2")) {
                    readyUsers--;
                    updateUI();
                } else if(message.equals("Lobby is full.")){
                    Intent intent = new Intent(MultiPlayerWaitingRoom.this, MultiPlayerLobbyActivity.class);
                    intent.putExtra("ERROR_MESSAGE", "Lobby is full, sorry");
                    startActivity(intent);
                    finish();
                }//else if(message.equals("Start team game")){
                //Intent intent = new Intent(MultiPlayerWaitingRoom.this, MultiPlayerGame.class); //Change MultiPlayerGame.class to new Team class
                //intent.putExtra("PLAYER", username); //Whatever intents you need
                //startActivity(intent);
                //}
                else {
                    // Handle other messages (e.g., adding them to the chat view)
                    addMessageToView(message);
                }
            }else if (matchType.equals("1v1")) {
                if (message.equals("@ready")) {
                    readyUsers++;
                    updateUI();
                } else if (message.equals("@unready")) {
                    readyUsers--;
                    updateUI();
                } else if(message.equals("Lobby is full.")){
                    Intent intent = new Intent(MultiPlayerWaitingRoom.this, MultiPlayerLobbyActivity.class);
                    intent.putExtra("ERROR_MESSAGE", "Lobby is full, sorry");
                    startActivity(intent);
                    finish();
                }else if(message.equals("Start game")){
                    Intent intent = new Intent(MultiPlayerWaitingRoom.this, MultiPlayerGame.class);
                    intent.putExtra("PLAYER", username);
                    startActivity(intent);
                }
                else {
                    // Handle other messages (e.g., adding them to the chat view)
                    addMessageToView(message);
                }
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
            if (reason != null && reason.contains("Invalid status code received: 404")) {
                // Redirect to the lobby with an error message
                Intent intent = new Intent(MultiPlayerWaitingRoom.this, MultiPlayerLobbyActivity.class);
                intent.putExtra("ERROR_MESSAGE", "Connection failed: Please go back to the main menu and enter a valid username (404)");
                WebSocketManager.getInstance().disconnectWebSocket();
                WebSocketManager.getInstance().removeWebSocketListener();
                isWebSocketConnected = false;
                startActivity(intent);
                finish();
            }else{
                addMessageToView("---\nconnection closed by " + closedBy + "\nreason: " + reason);
            }
        });

    }

    /**
     * Scrolls the chat view to the bottom.
     */
    private void scrollToBottom() {
        chatRecyclerView.scrollToPosition(chatMessages.size() - 1);
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
    public void onWebSocketError(Exception ex) {
        runOnUiThread(() -> {
            // Log the error for debugging purposes
            Log.e("WebSocketError", "Error in WebSocket connection: " + ex.getMessage());

            // Handle the error and navigate back to the lobby with an error message
            Intent intent = new Intent(MultiPlayerWaitingRoom.this, MultiPlayerLobbyActivity.class);
            intent.putExtra("ERROR_MESSAGE", "Connection error: " + ex.getMessage());
            WebSocketManager.getInstance().disconnectWebSocket();
            WebSocketManager.getInstance().removeWebSocketListener();
            isWebSocketConnected = false;
            startActivity(intent);
            finish();
        });
    }
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
            Log.d("MESSAGE",message.getMessage());
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
