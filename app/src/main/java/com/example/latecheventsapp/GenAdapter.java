package com.example.latecheventsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latecheventsapp.data.model.Event;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GenAdapter extends RecyclerView.Adapter<GenAdapter.MyViewHolder> {

    Context context;
    ArrayList<Event> eventArrayList;

    public GenAdapter(Context context, ArrayList<Event> eventArrayList) {
        this.context = context;
        this.eventArrayList = eventArrayList;
    }

    @NonNull
    @Override
    public GenAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event = eventArrayList.get(position);
        holder.eventViewName.setText(event.getEvent_Name());
        holder.eventViewLocation.setText(event.getLocation());
        SimpleDateFormat spf = new SimpleDateFormat(" EEE, d MMM yyyy HH:mm aaa");
        Timestamp start_temp = event.getStart();
        Date start_date =start_temp.toDate();
        Date end_date = event.getEnd().toDate();
        String start = spf.format(start_date);
        String end = spf.format(end_date);
        holder.eventViewStart.setText(start);
    }

    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView eventViewName;
        TextView eventViewStart;
        TextView eventViewLocation;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventViewName = itemView.findViewById(R.id.Event_Name);
            eventViewStart = itemView.findViewById(R.id.event_start_date);
            eventViewLocation = itemView.findViewById(R.id.Event_Location);
        }
    }
}
