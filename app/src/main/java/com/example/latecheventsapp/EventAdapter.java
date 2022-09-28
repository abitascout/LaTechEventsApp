package com.example.latecheventsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latecheventsapp.data.model.Event;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;

@IgnoreExtraProperties
public class EventAdapter extends FirestoreRecyclerAdapter<Event, EventAdapter.EventHolder> {



    public EventAdapter(@NonNull FirestoreRecyclerOptions<Event> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull EventHolder holder, int position, @NonNull Event model) {
        holder.eventViewName.setText(model.getEvent_Name());
        holder.eventViewLocation.setText(model.getLocation());
        SimpleDateFormat spf = new SimpleDateFormat(" EEE, d MMM yyyy HH:mm aaa");
        String start = spf.format((model.getStart()).toString());
        String end = spf.format((model.getEnd()).toString());
        holder.eventViewStart.setText(start);
        holder.eventViewEnd.setText(end);
    }




    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list, parent, false);
        return new EventHolder(v);

    }

    public class EventHolder extends RecyclerView.ViewHolder {
        TextView eventViewName;
        TextView eventViewStart;
        TextView eventViewEnd;
        TextView eventViewLocation;
        public EventHolder(@NonNull View itemView) {
            super(itemView);
            eventViewName = itemView.findViewById(R.id.Event_Name);
            eventViewStart = itemView.findViewById(R.id.event_start_date);
            eventViewLocation = itemView.findViewById(R.id.Event_Location);

        }


    }


}