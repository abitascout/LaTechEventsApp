package com.example.latecheventsapp.data;


import com.google.firebase.Timestamp;

public interface Igen {
    void createEvent(String title, Timestamp Start, String desc, Timestamp End, String Location, String Club_Name);
}
