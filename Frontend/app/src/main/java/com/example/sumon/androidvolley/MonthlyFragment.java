package com.example.sumon.androidvolley;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.sumon.androidvolley.R;
/**
 * MonthlyFragment is a subclass of Fragment used to represent the monthly leaderboard.
 * It displays data specific to the monthly leaderboard in the application.
 */
public class MonthlyFragment extends Fragment {

    /**
     * Constructor for creating a new instance of MonthlyFragment.
     * This constructor is empty but can be modified to pass specific arguments or perform initialization.
     */
    public MonthlyFragment() {}
    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null.
     * This method is called between onCreate(Bundle) and onActivityCreated(Bundle).
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     *                  The fragment should not add the view itself, but this can be used to generate the
     *                  LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     *                           as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monthly_leaderboard, container, false);
    }
}
