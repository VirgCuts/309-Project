package com.example.sumon.androidvolley;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
    private Button btnLeaderboard, btnArtists, btnWebsocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLeaderboard = (Button) findViewById(R.id.btnleaderboard);
        btnArtists = (Button) findViewById(R.id.btnArtists);
        btnWebsocket = (Button) findViewById(R.id.btnWebsocket);

        btnLeaderboard.setOnClickListener(this);
        btnArtists.setOnClickListener(this);
        btnWebsocket.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnleaderboard:
                startActivity(new Intent(MainActivity.this,
                        LeaderboardActivity.class));
                break;
            case R.id.btnArtists:
                startActivity(new Intent(MainActivity.this,
                        ArtistActivity.class));
                break;
            case R.id.btnWebsocket:
                startActivity(new Intent(MainActivity.this,
                        MessageBoard.class));
                break;
            default:
                break;
        }
    }

}
