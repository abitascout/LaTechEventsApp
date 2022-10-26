package com.example.latecheventsapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

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
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;



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
    private final ArrayList<Event> Fevents = new ArrayList<>();

    //widgets
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String currnetSearch = "";
    private SearchView searchView;



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
        parsedButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.tech_Red));
        parsedButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));
    }
    private void lookUnSelected(Button parsedButton){
        parsedButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
        parsedButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.tech_Red));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_events, container, false);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("General Events");

        Button allBtn = view.findViewById(R.id.All_button);
        Button partyBtn = view.findViewById(R.id.Party_button);
        Button foodBtn =  view.findViewById(R.id.Food_button);
        Button greekBtn=  view.findViewById(R.id.Greek_button);
        Button tutorBtn =  view.findViewById(R.id.Tutor_button);

        view = genSearch(view);



        allBtn.setOnClickListener(v -> {
            AllFilterTapped();
            unSelectAllFilters(allBtn,partyBtn,foodBtn,greekBtn,tutorBtn);
            lookSelected(allBtn);
        });

        partyBtn.setOnClickListener(v -> {
            PartyFilter();
            unSelectAllFilters(allBtn,partyBtn,foodBtn,greekBtn,tutorBtn);
            lookSelected(partyBtn);
        });

        foodBtn.setOnClickListener(v -> {
            FoodFilter();
            unSelectAllFilters(allBtn,partyBtn,foodBtn,greekBtn,tutorBtn);
            lookSelected(foodBtn);
        });


        greekBtn.setOnClickListener(v -> {
            GreekFilter();
            unSelectAllFilters(allBtn,partyBtn,foodBtn,greekBtn,tutorBtn);
            lookSelected(greekBtn);
        });

        tutorBtn.setOnClickListener(v -> {
            TutorFilter();
            unSelectAllFilters(allBtn,partyBtn,foodBtn,greekBtn,tutorBtn);
            lookSelected(tutorBtn);
        });
        //for when we decided to filter by clubs
       /* view.findViewById(R.id.Club_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClubFilter(v);
                    }
                });*/

        create_handler(view);
        Swiping(view);

        return view;
    }

    // used for the swipe refresh layout
    private void Swiping(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swiper);


        swipeRefreshLayout.setOnRefreshListener(() -> {

            eventRef.addSnapshotListener((Activity) requireContext(), (value, error) -> {
                if (error != null) {
                    Log.d("Error", error.toString());
                }
                assert value != null;
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
                    eventArrayList.sort(Comparator.comparing(Event::getStart));

                }
            });

            GenAdapter refreshAdapter = new GenAdapter(getContext(), eventArrayList, general_events.this);
            recyclerView.setAdapter(refreshAdapter);
            swipeRefreshLayout.setRefreshing(false);

        });
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
    private void create_handler(View view) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Events ...");
        progressDialog.show();

        eventArrayList = new ArrayList<>();
        adapter = new GenAdapter(getContext(), eventArrayList, this);
        testChangeListener();
        recyclerView = view.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }


    // populates the eventArrayList with the initial data
    @SuppressLint("NotifyDataSetChanged")
    private void testChangeListener() {
        Date date = new Date();
        Timestamp time = new Timestamp(date);
        eventRef.orderBy("start", Query.Direction.ASCENDING).orderBy("event_Name", Query.Direction.ASCENDING)
                .whereGreaterThanOrEqualTo("start", time)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            eventArrayList.add(documentSnapshot.toObject(Event.class));
                        }
                        adapter.notifyDataSetChanged();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                    }
                });
    }


    private View genSearch(View view) {
        searchView = view.findViewById(R.id.search_view);
        EditText searchEditText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.BLACK);
        searchEditText.setHintTextColor(Color.BLACK);
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
                GenAdapter searchAdapter = new GenAdapter(getContext(), filteredEvents, general_events.this);
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
        GenAdapter filterAdapter = new GenAdapter(getContext(), Fevents, this);
        recyclerView.setAdapter(filterAdapter);
    }

    public void AllFilterTapped() {
        selectedFilter = "all";
        Fevents.clear();
        searchView.setQuery("", false);
        searchView.clearFocus();
        GenAdapter filterAdapter = new GenAdapter(getContext(), eventArrayList, this);
        recyclerView.setAdapter(filterAdapter);
    }

    public void PartyFilter() {
        FilterList("Party");
    }

    public void FoodFilter() {
        FilterList("Food");
    }

    public void GreekFilter() {
        FilterList("Greek Life");
    }

    public void TutorFilter() {
        FilterList("Tutoring");
    }

    //need for when we implement club filter
    /*public void ClubFilter() {
        FilterList("Club");
    }*/


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onRefresh() {

        swipeRefreshLayout.setRefreshing(false);
    }




    // method for making events clickable and sending data to the more info fragment
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
