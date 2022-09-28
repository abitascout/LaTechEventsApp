package com.example.latecheventsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latecheventsapp.data.model.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
    public void onBindViewHolder(@NonNull GenAdapter.MyViewHolder holder, int position) {
        Event event = eventArrayList.get(position);
        holder.eventViewName.setText(event.getEvent_Name());
        holder.eventViewLocation.setText(event.getLocation());
        SimpleDateFormat spf = new SimpleDateFormat(" EEE, d MMM yyyy HH:mm aaa");
        String start = spf.format((event.getStart()).toString());
        String end = spf.format((event.getEnd()).toString());
        holder.eventViewStart.setText(start);
        holder.eventViewEnd.setText(end);
    }

    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView eventViewName;
        TextView eventViewStart;
        TextView eventViewEnd;
        TextView eventViewLocation;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventViewName = itemView.findViewById(R.id.Event_Name);
            eventViewStart = itemView.findViewById(R.id.event_start_date);
            eventViewEnd = itemView.findViewById(R.id.event_end_date);
            eventViewLocation = itemView.findViewById(R.id.Event_Location);
        }
    }
}
