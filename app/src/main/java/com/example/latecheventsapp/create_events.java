package com.example.latecheventsapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link create_events#newInstance} factory method to
 * create an instance of this fragment.
 */
public class create_events extends Fragment {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    public create_events() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static create_events newInstance() {
        create_events fragment = new create_events();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1){ return "Jan";}
        if(month == 2){ return "Feb";}
        if(month == 3){ return "Mar";}
        if(month == 4){ return "Apr";}
        if(month == 5){ return "May";}
        if(month == 6){ return "Jun";}
        if(month == 7){ return "Jul";}
        if(month == 8){ return "Aug";}
        if(month == 9){ return "Sep";}
        if(month == 10){ return "Oct";}
        if(month == 11){ return "Nov";}
        if(month == 12){ return "Dec";}

        // default, should never happen
        return "Error: Couldn't find month";
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_events, container, false);
        // Inflate the layout for this fragment
            // Date picker for Create Events
        initDatePicker();
        dateButton = view.findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());


        dateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                datePickerDialog.show();
            }
        });

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}