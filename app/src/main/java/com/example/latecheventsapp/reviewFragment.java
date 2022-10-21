package com.example.latecheventsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.latecheventsapp.data.Igen;
import com.example.latecheventsapp.data.model.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;


public class reviewFragment extends Fragment implements Igen {

    String subject;
    String location;
    String description;
    String date;
    String startTime;
    String endTime;
    String tags;
    String clubs;
    String eAllTime;
    String sAllTime;

    TextView subjectTV;
    TextView locationTV;
    TextView descriptionTV;
    TextView dateTV;
    TextView startTimeTV;
    TextView tagsTV;
    TextView clubsTV;

    Button editButton;

    Button submitButton;

    Bundle rbundle = new Bundle();

    Timestamp stimestamp;

    Timestamp etimestamp;

    Toolbar toolbar;
    MenuItem menuItem;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventRef = db.collection("Events");


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

        // Change Toolbar title.
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Review");




        editButton = view.findViewById(R.id.buttonEdit);
        submitButton = view.findViewById(R.id.buttonSubmit);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save data to send to review fragment
                saveInformation();

                Fragment rFragment = new create_events();
                rFragment.setArguments(rbundle);

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

                try {
                     stimestamp = new Timestamp(new Date(sAllTime));
                } catch(Exception e) { //this generic but you can control another types of exception
                    // look the origin of exception
                }

                try {
                    etimestamp = new Timestamp(new Date(eAllTime));
                } catch(Exception e) { //this generic but you can control another types of exception
                    // look the origin of excption
                }

                createEvent(subject, stimestamp, description, etimestamp, location, clubs, tags);

            }
        });
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
        startTimeTV = view.findViewById(R.id.textViewTimeReview);
        tagsTV = view.findViewById(R.id.textViewTagsReview);
        clubsTV = view.findViewById(R.id.textViewClubsReview);

        // Get info
        Bundle bundle = this.getArguments();
        //setBundleInfo();

        if(bundle != null){
            subject = bundle.getString("subject", "");
            location = bundle.getString("location", "");
            description = bundle.getString("description", "");
            date = bundle.getString("date", "");
            startTime = bundle.getString("startTime", "");
            endTime = bundle.getString("endTime", "");
            tags = bundle.getString("tags", "");
            clubs = bundle.getString("clubs", "");
            eAllTime = bundle.getString("eAllTime", "");
            sAllTime = bundle.getString("sAllTime", "");

            stripTagsAndClubs();

            // set Textviews
            subjectTV.setText(subject);
            locationTV.setText(location);
            descriptionTV.setText(description);
            dateTV.setText(date);
            if(endTime != ""){
                startTimeTV.setText(startTime + " - " + endTime);
            }
            else{
                startTimeTV.setText(startTime);
            }
            tagsTV.setText(tags);
            clubsTV.setText(clubs);
        }
    }

    private void saveInformation(){
        rbundle.putString("subject", subject);
        rbundle.putString("location", location);
        rbundle.putString("description", description);

        rbundle.putString("date", date);

        rbundle.putString("startTime", startTime);
        if(endTime != ""){
            rbundle.putString("endTime", endTime);
        }
        else{
            rbundle.putString("endTime", "_:__PM");
        }

        rbundle.putString("tags", tags);
        rbundle.putString("clubs", clubs);
    }

    private void stripTagsAndClubs(){
        if(tags != ""){
            tags = tags.replace("[","");
            tags = tags.replace("]","");
        }
        else{
            tags = "No Tags";
        }
        if(clubs != ""){
            clubs = clubs.replace("[","");
            clubs = clubs.replace("]","");
        }
        else{
            clubs = "No Clubs";
        }
    }

    @Override
    public void createEvent(String title, Timestamp Start, String desc, Timestamp End, String Location, String Club_Name, String Tag) {
        Event event = new Event();
        event.setEvent_Name(title);
        event.setEvent_Desc(desc);
        event.setClub_Name(Club_Name);
        event.setStart(Start);
        event.setLocation(Location);
        event.setEnd(End);
        event.setTag(Tag);

        eventRef.add(event).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new general_events());
                fragmentTransaction.commit();
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                navigationView.setCheckedItem(R.id.nav_general_events);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "You do not have permission.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}