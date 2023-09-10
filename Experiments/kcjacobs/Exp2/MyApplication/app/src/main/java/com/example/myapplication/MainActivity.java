package com.example.myapplication;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    Button subtractButton, increaseButton, finalScoreButton;
     TextView counter;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

     int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subtractButton = findViewById(R.id.subtractButton);
        increaseButton = findViewById(R.id.addButton);
        finalScoreButton = findViewById(R.id.finalBtn);
        counter = findViewById(R.id.counter);
        Intent sendIntent = new Intent();


        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                counter.setText(String.valueOf(--count));
            }
        });
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                counter.setText(String.valueOf(++count));
            }
        });

        finalScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = counter.getText().toString();
                Intent intent = new Intent(MainActivity.this, Counter.class);
                intent.putExtra("message_key", str);
                startActivity(intent);
            }
        });



    }

}
