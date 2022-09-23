package com.example.latecheventsapp.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latecheventsapp.R;
import com.example.latecheventsapp.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Eventchanger extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Event> genEvents = new ArrayList<>();
    private Context genContext;
    private int SelectedEventIndex;
    private Igen igen;

    public Eventchanger(Context context, ArrayList<Event> events) {
        genEvents = events;
        genContext = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holds;
        View view = LayoutInflater.from(parent.getContext()).inflate(

                R.layout.layout_event_list_item, parent, false);
        holds = new ViewHolder(view);

        return holds;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).title.setText(genEvents.get(position).getEvent_Name());
            SimpleDateFormat spf = new SimpleDateFormat(" MMM dd, yyyy \n hh:mm");
            String date = spf.format(genEvents.get(position).getStart());
            ((ViewHolder)holder).timestamp.setText(date);
        }
    }

    @Override
    public int getItemCount() {
        return genEvents.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, timestamp;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.name);
            timestamp = itemView.findViewById(R.id.hours);

        }
    }
}
