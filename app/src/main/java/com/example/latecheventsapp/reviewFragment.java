package com.example.latecheventsapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;


public class reviewFragment extends Fragment {

    String subject;
    String location;
    String description;
    String date;
    String startTime;
    String endTime;
    String tags;
    String clubs;

    TextView subjectTV;
    TextView locationTV;
    TextView descriptionTV;
    TextView dateTV;
    TextView startTimeTV;
    TextView tagsTV;
    TextView clubsTV;

    Button editButton;

    Button submitButton;

    Bundle bundle = new Bundle();

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

        editButton = view.findViewById(R.id.buttonEdit);
        submitButton = view.findViewById(R.id.buttonSubmit);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save data to send to review fragment
                //saveInformation();

                Fragment rFragment = new reviewFragment();
                rFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, rFragment);
                fragmentTransaction.commit();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: SEND EVENT TO DATABASE

            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Get textViews
        subjectTV = view.findViewById(R.id.textViewSubjectMoreInfo);
        locationTV = view.findViewById(R.id.textViewLocationMoreInfo);
        descriptionTV = view.findViewById(R.id.textViewDescriptionMoreInfo);
        dateTV = view.findViewById(R.id.textViewDateMoreInfo);
        startTimeTV = view.findViewById(R.id.textViewTimeMoreInfo);
        tagsTV = view.findViewById(R.id.textViewTagsMoreInfo);
        clubsTV = view.findViewById(R.id.textViewClubsMoreInfo);

        // Get info
        Bundle bundle = this.getArguments();
        if(bundle != null){
            subject = bundle.getString("subject", "");
            location = bundle.getString("location", "");
            description = bundle.getString("description", "");
            date = bundle.getString("date", "");
            startTime = bundle.getString("startTime", "");
            endTime = bundle.getString("endTime", "");
            tags = bundle.getString("tags", "");
            clubs = bundle.getString("clubs", "");
        }
        stripTagsAndClubs();

        // set Textviews
        subjectTV.setText(subject);
        locationTV.setText(location);
        descriptionTV.setText(description);
        dateTV.setText(date);
        startTimeTV.setText(startTime + " - " + endTime);
        tagsTV.setText(tags);
        clubsTV.setText(clubs);
    }

    private void stripTagsAndClubs(){
        if(tags != ""){
            tags = tags.replace("[","");
            tags = tags.replace("]","");
        }
        if(clubs != ""){
            clubs = clubs.replace("[","");
            clubs = clubs.replace("]","");
        }
    }
}