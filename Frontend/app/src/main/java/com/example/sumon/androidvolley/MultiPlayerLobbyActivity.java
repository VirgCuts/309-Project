package com.example.sumon.androidvolley;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
        lobbies.add(new Lobby("Lobby 1", 2, 2, false));
        lobbies.add(new Lobby("Lobby 2", 0, 2, false));
        lobbies.add(new Lobby("Lobby 3", 0, 2, false));
        lobbies.add(new Lobby("Lobby 4", 0, 2, false));
        lobbies.add(new Lobby("Lobby 5", 0, 2, false));
        lobbies.add(new Lobby("Lobby 6", 0, 2, false));
        lobbies.add(new Lobby("Lobby 7", 0, 2, false));
        lobbies.add(new Lobby("Lobby 8", 0, 2, false));
        lobbies.add(new Lobby("Lobby 9", 0, 2, false));
        lobbies.add(new Lobby("Lobby 10", 0, 2, false));


        lobbyAdapter = new LobbyAdapter(lobbies, this);
        rvLobbies.setAdapter(lobbyAdapter);

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
            intent.putExtra("LOBBY_NAME", lobby.getName());
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
