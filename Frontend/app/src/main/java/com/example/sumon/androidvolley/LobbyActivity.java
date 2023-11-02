package com.example.sumon.androidvolley;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import org.java_websocket.handshake.ServerHandshake;

public class LobbyActivity extends Activity implements OnClickListener, WebSocketListener {
    private Button btnArtists, btnChatRoom, lobby1, lobby2, lobby3;

    private String BASE_URL = "ws://coms-309-022.class.las.iastate.edu:8080/multiplayer/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnArtists = (Button) findViewById(R.id.btnArtists);
        btnChatRoom = (Button) findViewById(R.id.btnChatRoom);
        lobby1 = (Button) findViewById(R.id.lobby1);
        lobby2 = (Button) findViewById(R.id.lobby2);
        lobby3 = (Button) findViewById(R.id.lobby3);

        // button click listeners

        btnArtists.setOnClickListener(this);
        btnChatRoom.setOnClickListener(this);

        lobby1.setOnClickListener(view -> {

            String serverUrl = BASE_URL + "lobby1";
            Log.d("URL", "URL is " + serverUrl);
            // Establish WebSocket connection and set listener
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(LobbyActivity.this);
        });

        lobby2.setOnClickListener(view -> {

            String serverUrl = BASE_URL + "lobby2";
            Log.d("URL", "URL is " + serverUrl);
            // Establish WebSocket connection and set listener
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(LobbyActivity.this);
        });

        lobby3.setOnClickListener(view -> {

            String serverUrl = BASE_URL + "lobby3";
            Log.d("URL", "URL is " + serverUrl);
            // Establish WebSocket connection and set listener
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(LobbyActivity.this);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnArtists:
                startActivity(new Intent(LobbyActivity.this,
                        ArtistActivity.class));
                break;
            case R.id.btnChatRoom:
                startActivity(new Intent(LobbyActivity.this,
                        ChatActivity.class));
                break;
            default:
                break;
        }
    }
    private void joinlobby(String lobbyID) {
        String joinMessage = "JOIN_LOBBY:" + lobbyID;
        //webSocket.send(joinMessage);
    }

    private void readyUp() {
        // Send a message to the server to signal readiness
        String readyMessage = "READY";
        //webSocket.send(readyMessage);
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onWebSocketMessage(String message) {

    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}
