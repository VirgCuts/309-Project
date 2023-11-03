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

public class LobbyActivity extends AppCompatActivity implements OnClickListener {
    private Button singlePlayer, multiPlayer, btnStudy;
    private Navigation navigationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();

        singlePlayer = (Button) findViewById(R.id.singlePlayer);
        multiPlayer = (Button) findViewById(R.id.multiPlayer);
        btnStudy = (Button) findViewById(R.id.btnStudy);

        // button click listeners
        singlePlayer.setOnClickListener(this);
        multiPlayer.setOnClickListener(this);
        btnStudy.setOnClickListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.singlePlayer:
                startActivity(new Intent(LobbyActivity.this,
                        SinglePlayerGame.class));
                break;
            case R.id.multiPlayer:
                startActivity(new Intent(LobbyActivity.this,
                        GameWebSockets.class));
                break;
            case R.id.btnStudy:
                startActivity(new Intent(LobbyActivity.this,
                        StudyActivity.class));
                break;
            default:
                break;
        }
    }

}
