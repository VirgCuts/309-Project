package com.example.sumon.androidvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.handshake.ServerHandshake;

import java.util.Objects;

public class MessageBoard extends AppCompatActivity implements WebSocketListener {

    private final String BASE_URL = "ws://10.0.2.2:8080/chat/";

    private EditText usernameEtx, msgEtx;
    private TextView msgTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_board);

        /* initialize UI elements */
        Button connectBtn = (Button) findViewById(R.id.bt1);
        Button sendBtn = (Button) findViewById(R.id.bt2);
        usernameEtx = (EditText) findViewById(R.id.et1);
        msgEtx = (EditText) findViewById(R.id.et2);
        msgTv = (TextView) findViewById(R.id.tx1);

        /* connect button listener */
        connectBtn.setOnClickListener(view -> {
            String serverUrl = BASE_URL + usernameEtx.getText().toString();

            // Establish WebSocket connection and set listener
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(MessageBoard.this);
        });

        /* send button listener */
        sendBtn.setOnClickListener(v -> {
            try {

                // send message
                WebSocketManager.getInstance().sendMessage(msgEtx.getText().toString());
            } catch (Exception e) {
                Log.d("ExceptionSendMessage:", Objects.requireNonNull(e.getMessage()));
            }
        });
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "\n"+message);
        });
    }

    @SuppressLint("SetTextI18n")
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