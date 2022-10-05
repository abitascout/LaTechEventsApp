package com.example.latecheventsapp;

import static androidx.core.os.BundleKt.bundleOf;
import static androidx.fragment.app.FragmentKt.setFragmentResult;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.latecheventsapp.data.TagAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class create_events extends Fragment implements TagListener{

    private int MAX_CHAR_LIMIT_LOC = 18;
    private int MAX_CHAR_LIMIT_SUB = 25;

    // Date Picker Variables
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    public String date;

    // Time Picker Variables
    private TimePickerDialog startTimePickerDialog;
    private TimePickerDialog endTimePickerDialog;

    private Button startTimeButton;
    private Button endTimeButton;

    private Button reviewPageButton;

    int sHour, sMin, eHour, eMin;

    public String startTime;
    public String endTime;
    private Date startTime24;
    private Date endTime24;

    private boolean noEndTime = false;
    private Switch endTimeSwitch;
    private TextView endTimeTextView;

    private Context context;

    // Tag Accordion Variables
    private ScrollView tagScrollView;
    private RecyclerView tagRecyclerView;
    private Button tagButton;
    private boolean tagIsVisible = false;
    TagAdapter tagAdapter;
    private ArrayList<String> selectedTags;

    // Club Accordion Variables
    private ScrollView clubScrollView;
    private RecyclerView clubRecyclerView;
    private Button clubButton;
    private boolean clubIsVisible = false;
    private ArrayList<String> selectedClubs;

    // Check Input Text Variables
    TextInputEditText subjectEditText;
    TextInputEditText locationEditText;
    TextInputEditText descriptionEditText;

    // Display Msg
    Snackbar mySnackbar;

    // String array for holding all information for Review page
    static public String[] eventInfo;
    Bundle bundle = new Bundle();

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

    // Methods for choseing date
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void popStartTimePicker(){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, min);
                c.setTimeZone(TimeZone.getDefault());
                startTime24 = c.getTime();
                SimpleDateFormat format = new SimpleDateFormat("h:mm:a");
                startTime = format.format(c.getTime());
                startTimeButton.setText(startTime);

                sHour = hour;
                sMin = min;
            }
        };
        int style = AlertDialog.THEME_HOLO_LIGHT;
        startTimePickerDialog = new TimePickerDialog(getActivity(), style, onTimeSetListener, sHour, sMin, false);
    }

    private void popEndTimePicker(){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, min);
                c.setTimeZone(TimeZone.getDefault());
                endTime24 = c.getTime();
                SimpleDateFormat format = new SimpleDateFormat("h:mm:a");
                endTime = format.format(c.getTime());
                endTimeButton.setText(endTime);

                eHour = hour;
                eMin = min;
            }
        };

        int style = AlertDialog.THEME_HOLO_LIGHT;
        endTimePickerDialog = new TimePickerDialog(getActivity(), style, onTimeSetListener, eHour, eMin, false);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + ", " + year;
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

        //Input Text refrences
        subjectEditText = view.findViewById(R.id.TextInputEditTextSubject);
        locationEditText = view.findViewById(R.id.TextInputEditTextLocation);
        descriptionEditText = view.findViewById(R.id.TextInputEditTextDescription);

        // Review references
        reviewPageButton = view.findViewById(R.id.buttonReview);

        // Time select references
        startTimeButton = view.findViewById(R.id.buttonStartTime);
        endTimeButton = view.findViewById(R.id.buttonEndTime);
        endTimeSwitch = view.findViewById(R.id.switchEndTime);
        endTimeTextView = view.findViewById(R.id.textViewEndTime);

        // Date references
        dateButton = view.findViewById(R.id.datePickerButton);

        // Tag references
        tagScrollView = view.findViewById(R.id.scrollViewTags);
        tagRecyclerView = view.findViewById((R.id.recyclerViewTags));
        tagButton = view.findViewById(R.id.buttonTags);

        // Club references
        clubScrollView = view.findViewById(R.id.scrollViewClubs);
        clubRecyclerView = view.findViewById((R.id.recyclerViewClubs));
        clubButton = view.findViewById(R.id.buttonClubs);

        Bundle rbundle = this.getArguments();

        if(rbundle != null){
            Toast.makeText(getContext(), "Please reselect Tags & Clubs", Toast.LENGTH_SHORT).show();
            subjectEditText.setText(rbundle.getString("subject", ""));
            locationEditText.setText(rbundle.getString("location", ""));
            descriptionEditText.setText(rbundle.getString("description", ""));
            dateButton.setText(rbundle.getString("date", ""));

            startTime = rbundle.getString("startTime", "_:__PM");
            endTime = rbundle.getString("endTime", "_:__PM");

            ParsePosition pos = new ParsePosition(0);

            try {
                startTime24 = new SimpleDateFormat("h:mm:a").parse(startTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                endTime24 = new SimpleDateFormat("h:mm:a").parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            startTimeButton.setText(startTime);
            endTimeButton.setText(endTime);


            //tags = bundle.getString("tags", "");
            //clubs = bundle.getString("clubs", "");
            //TODO: carry over tags and clubs from review page.
        }

        context = getContext();

        // Switch to review page
        reviewPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInformation()){
                    // Save data to send to review fragment
                    saveInformation();

                    Fragment rFragment = new reviewFragment();
                    rFragment.setArguments(bundle);

                    FragmentTransaction fragmentTransaction = getActivity()
                            .getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, rFragment);
                    fragmentTransaction.commit();
                }
                else{
                    displayErrorMsgs();
                }
            }
        });

        // Time picker
        popStartTimePicker();
        popEndTimePicker();
        startTimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startTimePickerDialog.show();
            }
        });
        endTimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                endTimePickerDialog.show();
            }
        });

        // Check if End Time switch clicked.
        endTimeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    endTimeTextView.setVisibility(View.VISIBLE);
                    endTimeButton.setVisibility(View.VISIBLE);
                    endTimeButton.setText("_:__PM");

                } else {
                    endTimeTextView.setVisibility(View.GONE);
                    endTimeButton.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No End Time Selected", Toast.LENGTH_SHORT).show();
                    endTimeButton.setText("NO END");
                    endTime = "";
                    endTime24 = null;
                }
            }
        });

        // Date picker for Create Events
        initDatePicker();
        dateButton.setText(getTodaysDate());
        dateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                datePickerDialog.show();
            }
        });

        //Tag Accordion

        setTagRecyclerView();
        tagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tagIsVisible){
                    tagScrollView.setVisibility(View.GONE);
                }
                else{
                    tagScrollView.setVisibility(View.VISIBLE);
                    clubScrollView.setVisibility(View.GONE);
                    clubIsVisible = false;
                }
                tagIsVisible = !tagIsVisible;
            }
        });

        //Club Accordion
        setClubRecyclerView();
        clubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clubIsVisible){
                    clubScrollView.setVisibility(View.GONE);
                }
                else{
                    clubScrollView.setVisibility(View.VISIBLE);
                    tagScrollView.setVisibility(View.GONE);
                    tagIsVisible = false;
                }
                clubIsVisible = !clubIsVisible;
            }
        });



        return view;
    }


    private void saveInformation(){
        bundle.putString("subject", subjectEditText.getText().toString());
        bundle.putString("location", locationEditText.getText().toString());
        bundle.putString("description", descriptionEditText.getText().toString());

        bundle.putString("date", dateButton.getText().toString());

        bundle.putString("startTime", startTime);
        CharSequence base = "NO END";
        if(endTimeButton != base){
            bundle.putString("endTime", endTime);
        }
        else{
            bundle.putString("endTime", "");
        }


        //TODO: Implement tags and clubs saving.

    }

    private boolean checkInformation(){
        if(checkTime() && checkTextInputs()){
            return true;
        }
        else{ return false; }
    }

    private void displayErrorMsgs(){
        // display subject errors
        if((subjectEditText.getText().length() == 0)){
            Toast.makeText(getContext(), "Subject Line not Filled out", Toast.LENGTH_SHORT).show();
        }
        else if((subjectEditText.getText().length() >= MAX_CHAR_LIMIT_SUB)){
            Toast.makeText(getContext(), "Subject Line to long", Toast.LENGTH_SHORT).show();
        }

        // display time errors
        if(startTime24 == null){
            Toast.makeText(getContext(), "Please pick a Start time", Toast.LENGTH_SHORT).show();
        }
        CharSequence base = "NO END";
        if(endTimeButton.getText() != base){
            if (endTime24 == null && endTimeButton.getText() != base){
                Toast.makeText(getContext(), "Please pick an End time", Toast.LENGTH_SHORT).show();
            }
            else if(startTime24.after(endTime24)){
                Toast.makeText(getContext(), "End time before start time", Toast.LENGTH_SHORT).show();
            }
        }


        // display location errors
        if((locationEditText.getText().length() == 0)){
            Toast.makeText(getContext(), "Location Line not Filled out", Toast.LENGTH_SHORT).show();
        }
        else if((locationEditText.getText().length() >= MAX_CHAR_LIMIT_LOC)){
            Toast.makeText(getContext(), "Location Line to long", Toast.LENGTH_SHORT).show();
        }

        // display description errors
        if((descriptionEditText.getText().length() == 0)){
            Toast.makeText(getContext(), "Description Line not Filled out", Toast.LENGTH_SHORT).show();
        }

    }

    //TODO: Connect this function to the database to get the preset tags.
    private ArrayList<String> getTagData(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Food");
        arrayList.add("Music");
        arrayList.add("Tutoring");
        arrayList.add("GeekLife");
        arrayList.add("Party");
        return arrayList;
    }

    //TODO: Connect this function to the database to get the preset clubs.
    private ArrayList<String> getClubData(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("C");
        arrayList.add("D");
        arrayList.add("E");
        return arrayList;
    }

    private boolean checkTextInputs(){
        if((subjectEditText.getText().length() != 0) &&
                (locationEditText.getText().length() != 0) &&
                (descriptionEditText.getText().length() != 0)){
            if((subjectEditText.getText().length() <= MAX_CHAR_LIMIT_SUB) && (locationEditText.getText().length() <= MAX_CHAR_LIMIT_LOC)){
                return true;
            }
            else{ return false; }
        }
        else{ return false; }
    }

    private boolean checkTime(){
        // Checks if the switch has been selected.
        CharSequence base = "NO END";
        if (endTimeButton.getText() ==base && startTime24 != null) { return true; }
        if(startTime24 != null && endTime24 != null){
            if(startTime24.before(endTime24)){ return true; }
            else{return false;}
        }
        else{return false;}
    }

    private void setTagRecyclerView() {
        tagRecyclerView.setHasFixedSize(true);
        tagRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false));
        tagAdapter = new TagAdapter(requireContext(), getTagData(), this);
        tagRecyclerView.setAdapter(tagAdapter);

    }
    private void setClubRecyclerView() {
        clubRecyclerView.setHasFixedSize(true);
        clubRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false));
        tagAdapter = new TagAdapter(requireContext(), getClubData(), this);
        clubRecyclerView.setAdapter(tagAdapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onTagChange(ArrayList<String> arrayList) {
        if(clubIsVisible){
            bundle.putString("clubs",arrayList.toString());

        }
        else if (tagIsVisible){
            bundle.putString("tags",arrayList.toString());
        }
    }
}