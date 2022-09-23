package com.example.latecheventsapp.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latecheventsapp.R;
import com.example.latecheventsapp.data.model.Event;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;

public class EventAdapter extends FirestoreRecyclerAdapter<Event, EventAdapter.EventHolder> {


    @Override
    protected void onBindViewHolder(@NonNull EventHolder holder, int position, @NonNull Event model) {
        holder.eventViewName.setText(model.getEvent_Name());
        holder.eventViewLocation.setText(model.getLocation());
        SimpleDateFormat spf = new SimpleDateFormat(" MMM dd, yyyy \n hh:mm");
        String date = spf.format(model.getStart());
        holder.eventViewDate.setText(date);
    }
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EventAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }



    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list, parent, false);
        return new EventHolder(v);

    }

    class EventHolder extends RecyclerView.ViewHolder {
        TextView eventViewName;
        TextView eventViewDate;
        TextView eventViewLocation;
        public EventHolder(@NonNull View itemView) {
            super(itemView);
            eventViewName = itemView.findViewById(R.id.Event_Name);
            eventViewDate = itemView.findViewById(R.id.event_date);
            eventViewLocation = itemView.findViewById(R.id.Event_Location);

        }


    }


}