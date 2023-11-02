package com.example.sumon.androidvolley;

import android.widget.EditText;
import android.widget.TextView;

public interface GameViewInterface {

    /*
    handles when box is clicked
     */
    void Timer();



    /*
            Method that sets the side value of a box so example would be has a platinum etc.
         */
    void setBoxText(TextView textView, String text);
    /*
        Method to handle when a artist is found and is correct, should change the box
        from whatever the base color is to another
     */
    void changeBoxColor(EditText editText, boolean isCorrect);


}
