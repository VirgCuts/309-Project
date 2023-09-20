package com.example.helloworldbuttontest;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    TextView helloWorldTxt;
    Button button;
    boolean helloDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helloWorldTxt = findViewById(R.id.helloWorldTxt);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(helloDisplayed) {
                    helloWorldTxt.setText("");
                    button.setText("Click me for a surprise!");
                }
                else {
                    helloWorldTxt.setText("Hello World!");
                    button.setText("Tell Android Goodbye!");
                }
                helloDisplayed = !helloDisplayed;
            }
        });
    }
}