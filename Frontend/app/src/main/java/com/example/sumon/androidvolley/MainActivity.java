package com.example.sumon.androidvolley;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
    private Button btnJson, btnString, btnLeaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnString = (Button) findViewById(R.id.btnStringRequest);
        btnJson = (Button) findViewById(R.id.btnJsonRequest);
        btnLeaderboard = (Button) findViewById(R.id.btnleaderboard);

        // button click listeners
        btnString.setOnClickListener(this);
        btnJson.setOnClickListener(this);
        btnLeaderboard.setOnClickListener(this);
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
            default:
                break;
        }
    }

}
