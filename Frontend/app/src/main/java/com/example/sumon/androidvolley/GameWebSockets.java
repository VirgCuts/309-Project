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
    private EditText usernameEtx;
    private TextView msgTv;

    private String Player1, Player2 = "Conor", boardState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_socket);

        /* initialize UI elements */
        connectBtn = (Button) findViewById(R.id.connect_button);
        sendBtn = (Button) findViewById(R.id.send_button);
        usernameEtx = (EditText) findViewById(R.id.username_input);
        msgTv = (TextView) findViewById(R.id.chatMessage);

//        boardState = "[[true, true, true]," +
//                     "[true, true, true]," +
//                     "[true, true, true]]";
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
    //receive board state here
    @Override
    public void onWebSocketMessage(String message) {

        runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "Got a Message: \n"+message);
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

    private void endWebsocket() {
        WebSocketManager.getInstance().disconnectWebSocket();
    }
}

