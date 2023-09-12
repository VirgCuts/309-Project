package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {
    Button toCounter;
    Button toPicture;
    Button toHome;
    Button increaseBtn;
    Button decreaseBtn;
    Button numSet;
    TextView number;
    EditText numberTxt;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        increaseBtn = findViewById(R.id.increaseBtn);
        decreaseBtn = findViewById(R.id.decreaseBtn);

        numSet = findViewById(R.id.numSet);

        numberTxt = findViewById(R.id.setNumber);

        number = findViewById(R.id.number);

        counter = Integer.parseInt(number.getText().toString());
        increaseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                number.setText(String.valueOf(++counter));
            }
        });

        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                number.setText(String.valueOf(--counter));
            }
        });

        numSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String value = numberTxt.getText().toString();
                int num;
                if(!value.isEmpty()) {
                    try {
                        num = Integer.parseInt(value);
                    } catch (NumberFormatException e) {

                        num = 0;
                    }
                }
                else {
                    num = 0;
                }
                counter = num;
                number.setText(String.valueOf(num));
            }
        });

        toCounter = findViewById(R.id.toCounterBtn);
        toPicture = findViewById(R.id.toPictureBtn);
        toHome = findViewById(R.id.toHomeBtn);

        toCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CounterActivity.this, CounterActivity.class);
                startActivity(intent);
            }
        });
        toPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CounterActivity.this, ExtraActivity.class);
                startActivity(intent);
            }
        });
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CounterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}