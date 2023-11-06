package com.example.sumon.androidvolley;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.sumon.androidvolley.R;
/**
 * A {@link Fragment} subclass that represents the all-time leaderboard.
 * This fragment is used to display a list or a summary of top scorers or performers
 * across all time in a specific category.
 */
public class AllTimeFragment extends Fragment {

    /**
     * Required empty public constructor for instantiating the fragment.
     */
    public AllTimeFragment() {}
    /**
     * Called to have the fragment instantiate its user interface view. This is optional,
     * and non-graphical fragments can return null (which is the default implementation).
     * This will be called between {@code onCreate(Bundle)} and {@code onActivityCreated(Bundle)}.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to. The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_time_leaderboard, container, false);
    }
}
