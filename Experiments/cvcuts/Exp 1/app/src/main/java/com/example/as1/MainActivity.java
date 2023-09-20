package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button toCounter;

    Button toPicture;

    Button toHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toCounter = findViewById(R.id.toCounterBtn);

        toPicture = findViewById(R.id.toPictureBtn);

        toHome = findViewById(R.id.toHomeBtn);

        toCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, CounterActivity.class);
                startActivity(intent);
            }
        });
        toPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ExtraActivity.class);
                startActivity(intent);
            }
        });
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }


}