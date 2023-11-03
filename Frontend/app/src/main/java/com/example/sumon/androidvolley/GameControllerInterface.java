package com.example.sumon.androidvolley;

import android.widget.EditText;

public interface GameControllerInterface {


    boolean checkAnswer(EditText editText, String artistCheck, String songCheck);
    //void checkAnswer(EditText editText); //For backend
    void endGame();
}