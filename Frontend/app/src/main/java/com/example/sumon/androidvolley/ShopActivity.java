package com.example.sumon.androidvolley;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ShopActivity extends AppCompatActivity {
    private Navigation navigationHelper;
    private Button orange, purple, lightblue, yellow, magenta, green;
    private TextView balance;
    private int bal = 10000;
    private String User = "Carter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();

        setBalance();
        orange = findViewById(R.id.orange);
        purple = findViewById(R.id.purple);
        lightblue = findViewById(R.id.lightblue);
        yellow = findViewById(R.id.yellow);
        magenta = findViewById(R.id.magenta);
        green = findViewById(R.id.green);

        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyColor(orange, 100, "orange");
            }
        });
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyColor(purple, 300, "purple");
            }
        });
        lightblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyColor(lightblue, 500, "lightblue");
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyColor(yellow, 600, "yellow");
            }
        });
        magenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyColor(magenta, 900, "magenta");
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyColor(green, 1000, "green");
            }
        });
    }
    //runs on startup to see if a color has been priorly purchased
    public void checkPurchased() {

    }
    //sends color to backend to notify that color is now owned by user
    public void addColorToUser(String color) {

    }
    //sends selected color to backend to notify that it is now the selected color
    public void selectColor(String color) {

    }
    //calls to backend to get the point balance associated with user
    //sends user to backend need to get back string with a point val to Intparse
    public int getBalance(String user) {

        //value should be saved to bal at end
        return bal;
    }
    public void setBalance() {
        balance = findViewById(R.id.balance);
        balance.setText("Balance: " + getBalance(User));
    }

    public boolean checkPoints(int neededPts) {
        if(bal < neededPts) {
            //throw some kinda indicator
            return false;
        }
        else {

            return true;
        }
    }
    public void buyColor(Button button, int cost, String color) {
        if(checkPoints(cost)) {
            changeBuyToSelect(button);
            Log.d("BTNVAL", color);
            addColorToUser(color);
            if(button.getText().toString().equals("SELECT")) {
                selectColor(color);
            }
        }


    }
    public void changeBuyToSelect(Button button) {
        button.setText("Select");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
