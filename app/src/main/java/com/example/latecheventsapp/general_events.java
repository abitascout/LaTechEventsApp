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
import com.example.latecheventsapp.data.model.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link general_events#newInstance} factory method to
 * create an instance of this fragment.
 */
public class general_events extends Fragment implements
    SwipeRefreshLayout.OnRefreshListener

{



    // Fire base and viewing event references
    private TextView eventView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventRef = db.collection("Events");
    // Event array list
    private ArrayList<Event> eventArray = new ArrayList<>();
    private ArrayList<Map> MapArray = new ArrayList<>();
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
        Query eventsQuery = null;
        if(LastQueriedDocument != null)
        {
            eventsQuery = eventsRef;
            eventsQuery
                    .orderBy("Start", Query.Direction.ASCENDING)
                    .startAfter(LastQueriedDocument);
        }
        else
        {
            eventsQuery = eventsRef;
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
        Erecyle.setLayoutManager(new LinearLayoutManager(getContext()));
        getEvents();
        Eventchanger changer = new Eventchanger(getContext(),eventArray);
        Erecyle.setAdapter(changer);

        changer.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance)
    {
        super.onViewCreated(view, savedInstance);
        ;
    }





    public void onRefresh() {
        getEvents();
        eventSwipe.setRefreshing(false);
    }

   /* @Override
    public void createEvent(String title, Timestamp Start, String desc, Timestamp End, String Location, String Club_Name) {
                FirebaseFirestore bd = FirebaseFirestore.getInstance();

                DocumentReference newEventRef = db.collection("Event").document();
                Event event = new Event();
                event.setEvent_Name(title);
                event.setEvent_Desc(desc);
                event.setClub_Name(Club_Name);
                event.setStart(Start);
                event.setLocation(Location);
                event.setEnd(End);

                newEventRef.set(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            getEvents();
                        }
                        else{
                            //makeSnackBarMessage("Failed. Check log.");
                        }
                    }
                });

    }*/
}