package com.example.latecheventsapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


public class reviewFragment extends Fragment {

    String subject;
    String location;
    String description;
    String date;
    String startTime;
    String endTime;

    TextView subjectTV;
    TextView locationTV;
    TextView descriptionTV;
    TextView dateTV;
    TextView startTimeTV;
    TextView endTimeTV;

    public reviewFragment() {
        // Required empty public constructor
    }

    public static reviewFragment newInstance() {
        reviewFragment fragment = new reviewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);






        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Get textViews
        subjectTV = view.findViewById(R.id.textViewSubjectReview);
        locationTV = view.findViewById(R.id.textViewLocationReview);
        descriptionTV = view.findViewById(R.id.textViewDescriptionReview);
        dateTV = view.findViewById(R.id.textViewDateReview);
        startTimeTV = view.findViewById(R.id.textViewStartTimeReview);
        endTimeTV = view.findViewById(R.id.textViewEndTimeReview);

        // Get info
        Bundle bundle = this.getArguments();
        if(bundle != null){
            subject = bundle.getString("subject", "");
            location = bundle.getString("location", "");
            description = bundle.getString("description", "");
            date = bundle.getString("date", "");
            startTime = bundle.getString("startTime", "");
            endTime = bundle.getString("endTime", "");
        }

        // set Textviews
        subjectTV.setText(subject);
        locationTV.setText(location);
        descriptionTV.setText(description);
        dateTV.setText(date);
        startTimeTV.setText(startTime);
        endTimeTV.setText(endTime);
    }
}