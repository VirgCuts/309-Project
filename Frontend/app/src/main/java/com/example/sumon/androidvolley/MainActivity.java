package com.example.sumon.androidvolley;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
    private Button btnLeaderboard, btnLobby, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);


        btnLeaderboard = (Button) findViewById(R.id.btnleaderboard);
        btnLobby = (Button) findViewById(R.id.lobbyButton);
        btnSettings = (Button) findViewById(R.id.settingsButton);


        // button click listeners

        btnLeaderboard.setOnClickListener(this);
        btnLobby.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnleaderboard:
                startActivity(new Intent(MainActivity.this,
                        LeaderboardActivity.class));
                break;
            case R.id.lobbyButton:
                startActivity(new Intent(MainActivity.this,
                        LobbyActivity.class));
                break;
            case R.id.settingsButton:
                startActivity(new Intent(MainActivity.this,
                        SettingsActivity.class));
                break;
            default:
                break;
        }
    }

}
