package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalActivity extends AppCompatActivity {

    Button backBtn;
    TextView numberTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        backBtn = findViewById(R.id.backBtn);
        numberTxt = findViewById(R.id.finalScore);



        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(FinalActivity.this, MainActivity.class);
            startActivity(intent);
        });



    }
}