package com.example.latecheventsapp.data;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latecheventsapp.data.model.Event;

import java.util.ArrayList;

public class Eventchanger extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Event> genEvents = new ArrayList<>();
    private Context genContext;
    private int SelectedEventIndex;
    private Igen igen;

    public Eventchanger(Context context, ArrayList<Event> events)
    {
        genEvents = events;
        genContext = context;
    }





    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
