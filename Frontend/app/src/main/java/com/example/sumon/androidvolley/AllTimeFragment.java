package com.example.sumon.androidvolley;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.sumon.androidvolley.R;

public class AllTimeFragment extends Fragment {

    // Constructor
    public AllTimeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_time_leaderboard, container, false);
    }
}
