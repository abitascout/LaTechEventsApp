package com.example.latecheventsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.latecheventsapp.data.model.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
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
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link general_events#newInstance} factory method to
 * create an instance of this fragment.
 */
public class general_events extends Fragment implements SwipeRefreshLayout.OnRefreshListener, GenAdapter.GenEventListener {
    private static final String TAG = "General Events";


    // Fire base and viewing event references

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference eventRef = db.collection("Events");
    private GenAdapter adapter;
    private ArrayList<Event> eventArrayList;
    private final ArrayList<Event> filteredEvents = new ArrayList<>();
    private ProgressDialog progressDialog;
    private String selectedFilter = "all";
    private ArrayList<Event> Fevents = new ArrayList<>();

    //widgets
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String currnetSearch = "";
    private SearchView searchView;

    public static general_events newInstance() {
        general_events fragment = new general_events();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void unSelectAllFilters(Button bt1, Button bt2, Button bt3, Button bt4, Button bt5){
        lookUnSelected(bt1);
        lookUnSelected(bt2);
        lookUnSelected(bt3);
        lookUnSelected(bt4);
        lookUnSelected(bt5);
    }

    private void lookSelected(Button parsedButton)
    {
        parsedButton.setTextColor(ContextCompat.getColor(getContext(), R.color.tech_Red));
        parsedButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
    }
    private void lookUnSelected(Button parsedButton){
        parsedButton.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        parsedButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.tech_Red));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_events, container, false);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("General Events");

        Button allBtn = view.findViewById(R.id.All_button);
        Button partyBtn = view.findViewById(R.id.Party_button);
        Button foodBtn =  view.findViewById(R.id.Food_button);
        Button greekBtn=  view.findViewById(R.id.Greek_button);
        Button tutorBtn =  view.findViewById(R.id.Tutor_button);
        view = Swiping(view);
        view = genSearch(view);



        allBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AllFilterTapped();
                        unSelectAllFilters(allBtn,partyBtn,foodBtn,greekBtn,tutorBtn);
                        lookSelected(allBtn);
                    }
                });

        partyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PartyFilter(v);
                        unSelectAllFilters(allBtn,partyBtn,foodBtn,greekBtn,tutorBtn);
                        lookSelected(partyBtn);
                    }
                });

        foodBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        FoodFilter(v);
                        unSelectAllFilters(allBtn,partyBtn,foodBtn,greekBtn,tutorBtn);
                        lookSelected(foodBtn);
                    }
                });


        greekBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GreekFilter(v);
                        unSelectAllFilters(allBtn,partyBtn,foodBtn,greekBtn,tutorBtn);
                        lookSelected(greekBtn);
                    }
                });

        tutorBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TutorFilter(v);
                        unSelectAllFilters(allBtn,partyBtn,foodBtn,greekBtn,tutorBtn);
                        lookSelected(tutorBtn);
                    }
                });
       /* view.findViewById(R.id.Club_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClubFilter(v);
                    }
                });*/

        view = create_handler(view);

        return view;
    }

    // used for the swipe refresh layout
    private View Swiping(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swiper);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                eventRef.addSnapshotListener((Activity) getContext(), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d("Error", error.toString());
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            DocumentSnapshot snap = dc.getDocument();
                            int oldIndex = dc.getOldIndex();
                            Event temp = snap.toObject(Event.class);
                            Date date = new Date();
                            Date eventDate = temp.getStart().toDate();

                                switch (dc.getType()) {
                                    case ADDED:
                                        boolean tempswitch = checking(temp);
                                        if (!tempswitch)
                                            if(eventDate.compareTo(date) >= 0)
                                                eventArrayList.add(temp);
                                        break;
                                    case MODIFIED:
                                        eventArrayList.remove(oldIndex);
                                        eventArrayList.add(temp);
                                        break;
                                    case REMOVED:
                                        Event tempEvent = eventCheck(temp, eventArrayList);
                                        if (tempEvent != null)
                                            eventArrayList.remove(tempEvent);



                            }
                            eventArrayList.sort(Comparator.comparing(e -> e.getStart()));

                        }
                    }
                });

                GenAdapter refreshAdapter = new GenAdapter(getContext(), eventArrayList, general_events.this::onEventClick);
                recyclerView.setAdapter(refreshAdapter);
                swipeRefreshLayout.setRefreshing(false);

            }

        });
        return view;
    }
    // finds it in the list
    private Event eventCheck(Event event, ArrayList<Event> eventArrayList) {
        for (int i = 0; i < eventArrayList.size(); i++) {
            String name = eventArrayList.get(i).getEvent_Name();
            Date temp = eventArrayList.get(i).getStart().toDate();
            Date eventDate = event.getStart().toDate();
            if (name.equals(event.getEvent_Name()) && (temp.compareTo(eventDate) == 0) && event.getLocation().equals(eventArrayList.get(i).getLocation())) {
                return eventArrayList.get(i);
            }
        }

        return null;
    }

    //checks to see if it is in the list already
    private boolean checking(Event event) {
        for (int i = 0; i < eventArrayList.size(); i++) {
            String name = eventArrayList.get(i).getEvent_Name();
            Date temp = eventArrayList.get(i).getStart().toDate();
            Date eventDate = event.getStart().toDate();
            if (name.equals(event.getEvent_Name()) && (temp.compareTo(eventDate) == 0) && event.getLocation().equals(eventArrayList.get(i).getLocation())) {
                return true;
            }
        }
        return false;
    }

    // creates the start up view for the app
    private View create_handler(View view) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Events ...");
        progressDialog.show();

        eventArrayList = new ArrayList<>();
        adapter = new GenAdapter(getContext(), eventArrayList, this::onEventClick);
        testChangeListener();
        recyclerView = view.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }


    // populates the eventArrayList with the initial data
    private void testChangeListener() {
        Date date = new Date();
        Timestamp time = new Timestamp(date);
        eventRef.orderBy("start", Query.Direction.ASCENDING).orderBy("event_Name", Query.Direction.ASCENDING)
                .whereGreaterThanOrEqualTo("start", time)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                eventArrayList.add(documentSnapshot.toObject(Event.class));
                            }
                            adapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                        }
                    }
                });
    }


    private View genSearch(View view) {
        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                currnetSearch = s.stripLeading();
                filteredEvents.clear();
                for (Event event : eventArrayList) {
                    if (event.getEvent_Name().toLowerCase().contains(currnetSearch.toLowerCase())) {
                        if (selectedFilter.equals("all"))
                            filteredEvents.add(event);
                        else {
                            if (event.getTag().equalsIgnoreCase(selectedFilter))
                                filteredEvents.add(event);
                        }

                    }
                }
                GenAdapter searchAdapter = new GenAdapter(getContext(), filteredEvents, general_events.this::onEventClick);
                recyclerView.setAdapter(searchAdapter);
                return false;
            }
        });
        return view;

    }

    private void FilterList(String status) {
        selectedFilter = status;
        Fevents.clear();
        for (Event event : eventArrayList) {
            if(event.getTag() != null) {
                if (event.getTag().toLowerCase().contains(selectedFilter.toLowerCase())) {
                    if (event.getEvent_Name().toLowerCase().contains(currnetSearch.toLowerCase()))
                        Fevents.add(event);
                }
            }

        }
        GenAdapter filterAdapter = new GenAdapter(getContext(), Fevents, this::onEventClick);
        recyclerView.setAdapter(filterAdapter);
    }

    public void AllFilterTapped() {
        selectedFilter = "all";
        Fevents.clear();
        searchView.setQuery("", false);
        searchView.clearFocus();
        GenAdapter filterAdapter = new GenAdapter(getContext(), eventArrayList, this::onEventClick);
        recyclerView.setAdapter(filterAdapter);
    }

    public void PartyFilter(View view) {
        FilterList("Party");
    }

    public void FoodFilter(View view) {
        FilterList("Food");
    }

    public void GreekFilter(View view) {
        FilterList("Greek Life");
    }

    public void TutorFilter(View view) {
        FilterList("Tutoring");
    }

    public void ClubFilter(View view) {
        FilterList("Club");
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

    @Override
    public void onEventClick(int position) {
        Log.d(TAG, "onEventClick: clicked ");
        Bundle bundle = new Bundle();
        Event tempevent;
        if(!filteredEvents.isEmpty())
            tempevent = filteredEvents.get(position);
        else if(Fevents.isEmpty())
            tempevent = eventArrayList.get(position);
        else
            tempevent = Fevents.get(position);
        bundle.putParcelable("event",tempevent);
        getParentFragmentManager().setFragmentResult("dataFromGen", bundle);
        Fragment fragment = new moreInfoFragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment).commit();

    }

}
