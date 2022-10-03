package com.example.latecheventsapp.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latecheventsapp.R;
import com.example.latecheventsapp.TagListener;

import java.util.ArrayList;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    View view;
    Context context;
    ArrayList<String> arrayList;
    TagListener tagListener;

    ArrayList<String> arrayList0 = new ArrayList<>();

    public TagAdapter(Context context, ArrayList<String> arrayList, TagListener tagListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.tagListener = tagListener;
    }

    public View getView() {
        return view;
    }

    @NonNull
    @Override
    public TagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.rv_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagAdapter.ViewHolder holder, int position) {
        if(arrayList != null && arrayList.size() > 0){
            holder.checkBox.setText(arrayList.get(position));
            if(holder.checkBox.isChecked()){
                arrayList0.add(arrayList.get(position));
            }
            else{
                arrayList0.remove(arrayList.get(position));
            }
            tagListener.onTagChange(arrayList0);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.check_box);
        }
    }
}
