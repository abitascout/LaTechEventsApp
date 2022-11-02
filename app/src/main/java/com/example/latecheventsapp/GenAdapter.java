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

    public Context context;
    public ArrayList<Event> eventArrayList;
    public GenEventListener genEventListener;



    public GenAdapter(Context context, ArrayList<Event> eventArrayList, GenEventListener genEventListener) {
        this.context = context;
        this.eventArrayList = eventArrayList;
        this.genEventListener = genEventListener;
    }

    public GenAdapter(ArrayList<Event> eventArrayList, MyEvents myEvents) {
    }

    @NonNull
    @Override
    public GenAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list, parent, false);

        return new MyViewHolder(v, genEventListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event = eventArrayList.get(position);
        holder.eventViewName.setText(event.getEvent_Name());
        holder.eventViewLocation.setText(event.getLocation());
        SimpleDateFormat spf = new SimpleDateFormat(" EEE, d MMM hh:mm aaa");
        Timestamp start_temp = event.getStart();
        Date start_date =start_temp.toDate();
        Date end_date;
        String end;
        try{
            end_date = event.getEnd().toDate();
            end = spf.format(end_date);
        } catch (Exception e) {
            end_date = null;
            end = null;
        }

        String start = spf.format(start_date);

        holder.eventViewStart.setText(start);
    }

    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView eventViewName;
        TextView eventViewStart;
        TextView eventViewLocation;
        GenEventListener genEventListener;

        public MyViewHolder(@NonNull View itemView, GenEventListener genEventListener) {
            super(itemView);
            eventViewName = itemView.findViewById(R.id.Event_Name);
            eventViewStart = itemView.findViewById(R.id.event_start_date);
            eventViewLocation = itemView.findViewById(R.id.Event_Location);
            this.genEventListener = genEventListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            genEventListener.onEventClick(getAdapterPosition());
        }
    }

    public interface GenEventListener{
        void onEventClick(int position);

    }
}
