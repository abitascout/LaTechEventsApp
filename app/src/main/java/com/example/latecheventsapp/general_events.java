package com.example.latecheventsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.latecheventsapp.data.model.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link general_events#newInstance} factory method to
 * create an instance of this fragment.
 */
public class general_events extends Fragment implements SwipeRefreshLayout.OnRefreshListener

{



    // Fire base and viewing event references

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventRef = db.collection("Events");
    private GenAdapter adapter;
    private ArrayList<Event> eventArrayList;
    private  ProgressDialog progressDialog;

    //widgets
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CoordinatorLayout coordinatorLayout;






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


        view = create_handler(view);
        view = Swiping(view);

        return view;
    }

    // used for the swipe refresh layout
    private View Swiping(View view)
    {
        swipeRefreshLayout = view.findViewById(R.id.swiper);
        coordinatorLayout = view.findViewById(R.id.general);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                eventRef.addSnapshotListener((Activity) getContext(), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error !=null)
                        {
                            Log.d("Error", error.toString());
                        }
                        for (DocumentChange dc: value.getDocumentChanges())
                        {
                            DocumentSnapshot snap = dc.getDocument();
                            int oldIndex = dc.getOldIndex();
                            Event temp = snap.toObject(Event.class);
                            switch (dc.getType())
                            {
                                case ADDED:
                                    boolean tempswitch = checking(temp);
                                    if(!tempswitch)
                                        eventArrayList.add(temp);
                                    break;
                                case MODIFIED:
                                    eventArrayList.remove(oldIndex);
                                    eventArrayList.add(temp);
                                    break;
                                case REMOVED:
                                    Event tempEvent = eventCheck(temp);
                                    if(tempEvent != null)
                                        eventArrayList.remove(oldIndex);
                                    break;

                            }
                            eventArrayList.sort(Comparator.comparing(e -> e.getStart()));

                        }
                    }
                });

                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

        });
        return view;
    }

    private Event eventCheck(Event event)
    {
        for (int i = 0; i< eventArrayList.size(); i++)
        {
            String name = eventArrayList.get(i).getEvent_Name();
            if(name.equals(event.getEvent_Name()))
            {
                return eventArrayList.get(i);
            }
        }

        return null;
    }
    //checks to see if it is in the list already
    private boolean checking(Event event){
        for (int i = 0; i < eventArrayList.size(); i++ )
        {
            String name = eventArrayList.get(i).getEvent_Name();
            if(name.equals(event.getEvent_Name()))
            {
                return true;
            }
        }
        return false;
    }

    // creates the start up view for the app
    private View create_handler(View view){

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Events ...");
        progressDialog.show();

        eventArrayList = new ArrayList<Event>();
        adapter = new GenAdapter(getContext(), eventArrayList);
        testChangeListener();
        recyclerView =view.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }


    // populates the eventArrayList with the initial data
    private void testChangeListener()
    {
        eventRef.orderBy("Start", Query.Direction.ASCENDING).orderBy("Event_Name", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful())
                            {
                                for (QueryDocumentSnapshot documentSnapshot: task.getResult())
                                {
                                    eventArrayList.add(documentSnapshot.toObject(Event.class));
                                }
                                adapter.notifyDataSetChanged();
                                if(progressDialog.isShowing())
                                {
                                    progressDialog.dismiss();
                                }

                            }
                    }
                });
    }





    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


    //Todo: work on refreshing the page next

    @Override
    public void onRefresh() {

        swipeRefreshLayout.setRefreshing(false);
    }



   //Todo: use this for create events maybe?????
/*
   @Override
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