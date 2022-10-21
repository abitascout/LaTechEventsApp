package com.example.latecheventsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.example.latecheventsapp.data.model.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    TextView endTimeTV;
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

        getParentFragmentManager().setFragmentResultListener("dataFromGen", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Event tempevent = result.getParcelable("event");
                subject = tempevent.getEvent_Name();
                location = tempevent.getLocation();
                description = tempevent.getEvent_Desc();
                SimpleDateFormat spfDate = new SimpleDateFormat("EEE, MMM d");
                SimpleDateFormat spf = new SimpleDateFormat("hh:mm aaa");
                Timestamp start_temp = tempevent.getStart();
                Date start_day = start_temp.toDate();
                Date start_date =start_temp.toDate();
                String day = spfDate.format(start_day);
                String start = spf.format(start_date);
                date =day;
                startTime = start;
                tags = tempevent.getTag();
                clubs = tempevent.getClub_Name();
                subjectTV.setText(subject);
                locationTV.setText(location);
                descriptionTV.setText(description);
                startTimeTV.setText(date);
                tagsTV.setText(tags);
                clubsTV.setText(clubs);
                if(tempevent.getEnd() != null)
                {
                    Date end_date = tempevent.getEnd().toDate();
                    String end = spf.format(end_date);
                    endTime = end;
                    String tim = String.format("%1$s - %2$s",startTime,endTime);
                    endTimeTV.setText(tim);
                }
                else
                {
                    String timm = String.format("%1$s",startTime);
                    endTimeTV.setText(timm);
                }



            }
        });



         FloatingActionButton back = view.findViewById(R.id.buttonGen);

        //Get text ref
        subjectTV = view.findViewById(R.id.textViewSubjectMoreInfo);
        locationTV = view.findViewById(R.id.textViewLocationMoreInfo);
        descriptionTV = view.findViewById(R.id.textViewDescriptionMoreInfo);
        startTimeTV = view.findViewById(R.id.textViewStartMoreInfo);
        endTimeTV = view.findViewById(R.id.textViewEndMoreInfo);
        tagsTV = view.findViewById(R.id.textViewTagsMoreInfo);
        clubsTV = view.findViewById(R.id.textViewClubsMoreInfo);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new general_events();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,fragment).commit();

            }
        });




        return view;
    }

   /* private void getEventInfo(){
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
            *//*public void onComplete(@NonNull Task<DocumentSnapshot> task) {
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
        }); *//*
        ArrayList<String> arrayList = new ArrayList<>();
        *//*eventCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    arrayList.add()
                    for(QueryDocumentSnapshot tags: queryDocumentSnapshots){
                        arrayList.add(events.getString("Club_Name"));
                    }
                }

            }
        }); *//*
    }*/
}