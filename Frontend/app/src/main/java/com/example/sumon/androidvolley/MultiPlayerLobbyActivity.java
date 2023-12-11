package com.example.sumon.androidvolley;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MultiPlayerLobbyActivity extends AppCompatActivity implements LobbyAdapter.OnItemClickListener {
    private RecyclerView rvLobbies;
    private Navigation navigationHelper;
    private LobbyAdapter lobbyAdapter;
    private List<Lobby> lobbies; // This should be populated with actual lobby data
    private static final String PREFS_NAME = "LeaderboardPrefs";
    private static final String USERNAME_KEY = "username";
    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_lobby);

        rvLobbies = findViewById(R.id.rvLobbies);
        rvLobbies.setLayoutManager(new LinearLayoutManager(this));
        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();
        // Initialize your lobby list here
        lobbies = new ArrayList<>();
        lobbies.add(new Lobby(1, 0, 2, false));
        lobbies.add(new Lobby(2, 0, 2, false));
        lobbies.add(new Lobby(3, 0, 2, false));
        lobbies.add(new Lobby(4, 0, 2, false));
        lobbies.add(new Lobby(5, 0, 2, false));
        lobbies.add(new Lobby(6, 0, 2, false));
        lobbies.add(new Lobby(7, 0, 2, false));
        lobbies.add(new Lobby(8, 0, 2, false));
        lobbies.add(new Lobby(9, 0, 2, false));
        lobbies.add(new Lobby(10, 0, 2, false));


        lobbyAdapter = new LobbyAdapter(lobbies, this);
        rvLobbies.setAdapter(lobbyAdapter);

        username = getUsername(this);
        handleIncomingErrorMessage();

    }
    private void handleIncomingErrorMessage() {
        String errorMessage = getIntent().getStringExtra("ERROR_MESSAGE");
        if (errorMessage != null && !errorMessage.isEmpty()) {
            // Create and show an AlertDialog with the error message
            new AlertDialog.Builder(this)
                    .setTitle("Error") // You can set the title as needed
                    .setMessage(errorMessage)
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); // Close the dialog
                            // Optionally, you can add more actions here, like navigating back
                        }
                    })
                    .show();

            // Clear the error message to prevent it from showing again
            getIntent().removeExtra("ERROR_MESSAGE");
        }
    }
    public String getUsername(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String username = prefs.getString(USERNAME_KEY, null); // Return null if not found

        // Debugging log
        Log.d("SharedPreferences", "Retrieving username: " + username);

        return username;
    }

    public void onItemClick(Lobby lobby) {
        if (lobby.isGameInProgress()) {
            // Show message if game is in progress
            showToast("Game in progress. Cannot join this lobby.");
        } else if (lobby.getCurrentUserCount() >= lobby.getMaxUserCount()) {
            // Show AlertDialog if lobby is full
            showLobbyFullDialog();
        } else {
            // Proceed if lobby is not full and game is not in progress
            Intent intent = new Intent(MultiPlayerLobbyActivity.this, MultiPlayerWaitingRoom.class);
            intent.putExtra("LOBBY_NAME", lobby.getNum());
            intent.putExtra("USERNAME", username);
            if (lobby.getNum() >= 1 && lobby.getNum() <= 5) {
                intent.putExtra("MATCH_TYPE", "1v1");
            } else if (lobby.getNum() >= 6 && lobby.getNum() <= 10) {
                intent.putExtra("MATCH_TYPE", "2v2");
            }
            startActivity(intent);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void showLobbyFullDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Lobby is full.")
                .setCancelable(false)
                .setPositiveButton("X", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Close the dialog
                    }
                })
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

}
