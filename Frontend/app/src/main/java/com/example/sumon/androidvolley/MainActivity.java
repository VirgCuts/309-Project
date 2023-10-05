package com.example.sumon.androidvolley;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
    private Button btnJson, btnString, btnImage, btnArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnString = (Button) findViewById(R.id.btnStringRequest);
        btnJson = (Button) findViewById(R.id.btnJsonRequest);
        btnImage = (Button) findViewById(R.id.btnleaderboard);
        btnArtists = (Button) findViewById(R.id.btnArtists);

        // button click listeners
        btnString.setOnClickListener(this);
        btnJson.setOnClickListener(this);
        btnImage.setOnClickListener(this);
        btnArtists.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStringRequest:
                startActivity(new Intent(MainActivity.this,
                        StringRequestActivity.class));
                break;
            case R.id.btnJsonRequest:
                startActivity(new Intent(MainActivity.this,
                        JsonRequestActivity.class));
                break;
            case R.id.btnleaderboard:
                startActivity(new Intent(MainActivity.this,
                        LeaderboardActivity.class));
                break;
            case R.id.btnArtists:
                startActivity(new Intent(MainActivity.this,
                        ArtistActivity.class));
                break;
            default:
                break;
        }
    }

}
