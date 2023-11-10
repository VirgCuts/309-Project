package com.example.sumon.androidvolley;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.java_websocket.handshake.ServerHandshake;
/**
 * GameWebSockets is an AppCompatActivity that manages WebSocket connections
 * for a multiplayer game. It handles sending and receiving messages through
 * WebSockets and updates the UI based on the game state.
 */
public class GameWebSockets extends AppCompatActivity implements WebSocketListener{
    // Base URL for WebSocket connection
    private String BASE_URL = "ws://coms-309-022.class.las.iastate.edu:8080/multiplayer/";
    // UI Elements
    private Button connectBtn, sendBtn;
    private EditText usernameEtx;
    private TextView msgTv;
    // Player names and board state
    private String Player1 = "Carter", Player2 = "Conor", boardState;
    /**
     * Initializes the activity. Sets up the UI components and event listeners.
     * @param savedInstanceState If the activity is being re-initialized after being previously shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_socket);

        /* initialize UI elements */
        connectBtn = (Button) findViewById(R.id.connect_button);
        sendBtn = (Button) findViewById(R.id.send_button);
        usernameEtx = (EditText) findViewById(R.id.username_input);
        msgTv = (TextView) findViewById(R.id.chatMessage);

          boardState = "";
        /* connect button listener */
        connectBtn.setOnClickListener(view -> {
            String serverUrl = BASE_URL + usernameEtx.getText().toString();
            Log.d("URL", "URL is " + serverUrl);
            Player1 = usernameEtx.getText().toString();

            // Establish WebSocket connection and set listener
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(GameWebSockets.this);

            String s = msgTv.getText().toString();
            msgTv.setText(s + "\n"+ "Button Clicked");
        });
        /* send button listener */
        sendBtn.setOnClickListener(v -> {
            try {
                // sends the edit text message
                WebSocketManager.getInstance().sendMessage(boardToString(Player1 ,Player2));
                String s = msgTv.getText().toString();
                msgTv.setText(s + "\n"+boardToString(Player1 ,Player2));
            } catch (Exception e) {
                Log.d("ExceptionSendMessage:", e.getMessage().toString());
            }
        });
    }
    /**
     * Converts the board state to a String format for sending via WebSocket.
     * @param user First player's name.
     * @param user2 Second player's name.
     * @return String representation of the board state.
     */
    private String boardToString(String user, String user2) {
        return "{" +
                "  \"name1\": \""+ user + "\"," +
                "  \"name2\": \""+ user2 + "\"," +
                "  \"board\": {" +
                "    \"game\": [" +
                "      [0, 0, 0]," +
                "      [0, 0, 0]," +
                "      [0, 0, 0]" +
                "    ]," +
                "    \"won\": false," +
                "    \"score\": 0" +
                "  }" +
                "}";
    }
    /**
     * Callback for receiving messages from the WebSocket.
     * @param message The message received from the WebSocket.
     */
    @Override
    public void onWebSocketMessage(String message) {

        runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "Got a Message: \n"+message);
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
     * Callback for WebSocket connection opening.
     * @param handshakedata The handshake data from the server.
     */
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}
    /**
     * Callback for WebSocket errors.
     * @param ex The exception that occurred.
     */
    @Override
    public void onWebSocketError(Exception ex) {}
    /**
     * Ends the current WebSocket connection.
     */
    private void endWebsocket() {
        WebSocketManager.getInstance().disconnectWebSocket();
    }
}

