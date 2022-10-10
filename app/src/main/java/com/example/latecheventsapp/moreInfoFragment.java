package com.example.latecheventsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class moreInfoFragment extends Fragment {
    String subject;
    String location;
    String description;
    String date;
    String startTime;
    String endTime;
    String tags;
    String clubs;
    String docPath;

    TextView subjectTV;
    TextView locationTV;
    TextView descriptionTV;
    TextView dateTV;
    TextView startTimeTV;
    TextView tagsTV;
    TextView clubsTV;

    private FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    private CollectionReference eventCollection = mDatabase.collection("Events");

    public moreInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static moreInfoFragment newInstance(String param1, String param2) {
        moreInfoFragment fragment = new moreInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_more_info, container, false);

        // Change Toolbar title.
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("More Information");



        //Get text ref
        subjectTV = view.findViewById(R.id.textViewSubjectMoreInfo);
        locationTV = view.findViewById(R.id.textViewLocationMoreInfo);
        descriptionTV = view.findViewById(R.id.textViewDescriptionMoreInfo);
        dateTV = view.findViewById(R.id.textViewDateMoreInfo);
        startTimeTV = view.findViewById(R.id.textViewTimeMoreInfo);
        tagsTV = view.findViewById(R.id.textViewTagsMoreInfo);
        clubsTV = view.findViewById(R.id.textViewClubsMoreInfo);

        Bundle rbundle = this.getArguments();

        if(rbundle != null){
            docPath = rbundle.getString("docPath", "");
        }

        //Get event information
        getEventInfo();

        subjectTV.setText(subject);
        locationTV.setText(location);
        descriptionTV.setText(description);
        dateTV.setText(date); //TODO: pull date data from start or end time from docref.
        startTimeTV.setText(startTime); //TODO: Concat start and end time.

        tagsTV.setText(tags);
        clubsTV.setText(clubs);




        return view;
    }

    private void getEventInfo(){
        mDatabase.collection("Events").document(docPath).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        subject = document.getString("Event_Name");
                        location = document.getString("Location");
                        description = document.getString("Event_Desc");
                        startTime = document.getString("Start");
                        endTime = document.getString("End");
                        clubs = document.getString("Event_Club");
                        tags = document.getString("Event_Tag");
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        //Log.d(TAG,"String value: " + document.getString("names"));
                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
            /*public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<String> list = new ArrayList<>();

                        Map<String, Object> map = document.getData();
                        if (map != null) {
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                subject =


                            }
                        }

                        //So what you need to do with your list
                        for (String s : list) {
                            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                            //Log.d("TAG", s);
                        }
                    }
                }
            }
        }); */
        ArrayList<String> arrayList = new ArrayList<>();
        /*eventCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    arrayList.add()
                    for(QueryDocumentSnapshot tags: queryDocumentSnapshots){
                        arrayList.add(events.getString("Club_Name"));
                    }
                }

            }
        }); */
    }
}