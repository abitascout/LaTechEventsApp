package com.example.latecheventsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.latecheventsapp.data.Eventchanger;
import com.example.latecheventsapp.data.Igen;
import com.example.latecheventsapp.data.model.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link general_events#newInstance} factory method to
 * create an instance of this fragment.
 */
public class general_events extends Fragment implements
    Igen,
    SwipeRefreshLayout.OnRefreshListener
{



    // Fire base and viewing event references
    private TextView eventView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference eventRef = db.collection("Events").document("MOb43uHSEdK54WJqydqM");
    // Event array list
    private ArrayList<Event> eventArray = new ArrayList<>();
    private DocumentSnapshot LastQueriedDocument;
    private Eventchanger changeEvent;


    //widgets
    private RecyclerView Erecyle;
    private SwipeRefreshLayout eventSwipe;





    public static general_events newInstance() {
        general_events fragment = new general_events();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    private void getEvents()
    {
        CollectionReference eventsRef = db.collection("Events");
        Query eventsQuery = eventsRef;
        if(LastQueriedDocument != null)
        {
            eventsQuery
                    .orderBy("Start", Query.Direction.ASCENDING)
                    .startAfter(LastQueriedDocument);
        }
        else
        {
            eventsQuery
                    .orderBy("Start", Query.Direction.ASCENDING);
        }

        eventsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> Hold)
                {
                    if(Hold.isSuccessful())
                    {
                        for(QueryDocumentSnapshot document: Hold.getResult())
                        {
                            Event event = document.toObject(Event.class);
                            eventArray.add(event);
                        }
                        if(Hold.getResult().size() !=0)
                        {
                            LastQueriedDocument = Hold.getResult().getDocuments().get(Hold.getResult().size() -1);
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext().getApplicationContext(), "Query Failed", Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_events, container, false);
        Erecyle = view.findViewById(R.id.recyle);
        eventSwipe = view.findViewById(R.id.refresh_layout);
        eventSwipe.setOnRefreshListener(this);
        RecyleView();
        getEvents();
        return view;
    }


    private void RecyleView(){
        if(changeEvent ==null)
        {
            changeEvent = new Eventchanger(getContext().getApplicationContext(), eventArray);
        }
        Erecyle.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        Erecyle.setAdapter(changeEvent);
    }
   /* @Override
    public void onClick(View view) {
        eventRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            String name = documentSnapshot.getString("Event_Name");
                            String desc = documentSnapshot.getString("Event_Desc");
                            eventView.setText("Event Name: "+ name+"\n"+ "Event_Desc: "+ desc);
                        }
                        else {
                            Toast.makeText(getContext().getApplicationContext(), "No Event Listed 1", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext().getApplicationContext(), "No Event Listed 2", Toast.LENGTH_SHORT).show();
                    }
                });

    }
*/

    public void onRefresh() {
        getEvents();
        eventSwipe.setRefreshing(false);
    }

    @Override
    public void createEvent(String title, Timestamp Start, String desc, Timestamp End, String Location) {

    }
}