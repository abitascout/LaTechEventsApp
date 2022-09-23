package com.example.latecheventsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link general_events#newInstance} factory method to
 * create an instance of this fragment.
 */
public class general_events extends Fragment implements
    SwipeRefreshLayout.OnRefreshListener

{



    // Fire base and viewing event references

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventRef = db.collection("Events");
    private EventAdapter adapter;

    //widgets
    private RecyclerView recyclerView;
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



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_events, container, false);
        recyclerView =view.findViewById(R.id.recycle);
        Query query = eventRef.orderBy("Event_Name", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class)
                .build();
        adapter = new EventAdapter(options);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        return view;
    }


    private void setupCycleView(View view) {
        Query query = eventRef.orderBy("Event_Name", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class)
                .build();
        adapter = new EventAdapter(options);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void onRefresh() {

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