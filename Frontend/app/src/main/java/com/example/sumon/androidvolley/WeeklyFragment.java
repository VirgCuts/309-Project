package com.example.sumon.androidvolley;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.sumon.androidvolley.R;


/**
 * Currently Not implemented, will display weekly leaderboard.
 */
public class WeeklyFragment extends Fragment {

    // Constructor
    public WeeklyFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly_leaderboard, container, false);
    }
}
