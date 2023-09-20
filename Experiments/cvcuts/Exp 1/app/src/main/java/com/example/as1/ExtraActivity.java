package com.example.as1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExtraActivity extends AppCompatActivity {

    Button toCounter;

    Button toPicture;

    Button toHome;
    TextView numberTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        toCounter = findViewById(R.id.toCounterBtn);

        toPicture = findViewById(R.id.toPictureBtn);

        toHome = findViewById(R.id.toHomeBtn);

        toCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ExtraActivity.this, CounterActivity.class);
                startActivity(intent);
            }
        });
        toPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ExtraActivity.this, ExtraActivity.class);
                startActivity(intent);
            }
        });
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ExtraActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}