package com.example.sumon.androidvolley;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
/**
 * LobbyActivity is an AppCompatActivity that serves as a main menu for a game application.
 * It provides buttons for navigating to single player, multiplayer, and study sections of the app.
 */
public class LobbyActivity extends AppCompatActivity implements OnClickListener {
    private Button singlePlayer, multiPlayer, btnStudy, btnTeam;
    private Navigation navigationHelper;
    /**
     * Called when the activity is starting. This method initializes the UI components
     * and sets up the navigation and button click listeners.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();

        singlePlayer = (Button) findViewById(R.id.singlePlayer);
        multiPlayer = (Button) findViewById(R.id.multiPlayer);
        btnStudy = (Button) findViewById(R.id.btnStudy);
        btnTeam = (Button) findViewById(R.id.btnTeam);


        // button click listeners
        singlePlayer.setOnClickListener(this);
        multiPlayer.setOnClickListener(this);
        btnStudy.setOnClickListener(this);
        btnTeam.setOnClickListener(this);
    }
    /**
     * Handles item selections in the options menu.
     * @param item The MenuItem that was selected.
     * @return boolean Return false to allow normal menu processing to proceed,
     *                 true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    /**
     * Called when a view has been clicked.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.singlePlayer:
                startActivity(new Intent(LobbyActivity.this,
                        SinglePlayerGame.class));
                break;
            case R.id.multiPlayer:
                startActivity(new Intent(LobbyActivity.this,
                        MultiPlayerGame.class));
                break;
            case R.id.btnStudy:
                startActivity(new Intent(LobbyActivity.this,
                        StudyActivity.class));
                break;
            case R.id.btnTeam:
                startActivity(new Intent(LobbyActivity.this,
                        TeamMultiplayerGame.class));
                break;
            default:
                break;
        }
    }

}
