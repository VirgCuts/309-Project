package com.example.sumon.androidvolley;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.java_websocket.handshake.ServerHandshake;

public class GameWebSockets extends AppCompatActivity implements WebSocketListener{

    private String BASE_URL = "ws://coms-309-022.class.las.iastate.edu:8080/multiplayer/";

    private Button connectBtn, sendBtn;
    private EditText usernameEtx, msgEtx;
    private TextView msgTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_socket);

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
            WebSocketManager.getInstance().setWebSocketListener(GameWebSockets.this);
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
    //receive board state here
    @Override
    public void onWebSocketMessage(String message) {

        runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "\n"+message);
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

    private void sendGameState(String boardState) {
        WebSocketManager.getInstance().sendMessage(boardState);
        Log.d("Sent board State", boardState);
    }
    private void endWebsocket() {
        WebSocketManager.getInstance().disconnectWebSocket();
    }
}

