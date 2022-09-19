package com.example.latecheventsapp.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties

public class Event {
    private String Event_Name;
    private String Event_Desc;
    private String Club_Name;
    private Timestamp Start;
    private Timestamp End;
    private String Location;
    private String eventId;

    public Event (String Event_Name, String Event_Desc, String Club_Name, Timestamp Start, String Location)
    {
        this.Event_Name = Event_Name;
        this.Event_Desc = Event_Desc;
        this.Club_Name = Club_Name;
        this.Start = Start;
        this.End = End;
        this.Location = Location;


    }

    public  String getEvent_Name() {return  Event_Name;}
    public void setEvent_Name() {this.Event_Name = Event_Name;}

    public String getEvent_Desc() {return Event_Desc;}
    public void setEvent_Desc() {this.Event_Desc = Event_Desc;}

    public String getClub_Name() {return Club_Name;}
    public void setClub_Name() {this.Club_Name = Club_Name;}

    public Timestamp Start() {return Start;}
    public void setStart() {this.Start = Start;}

    public Timestamp End() {return  End;}
    public void setEnd() {this.End = End;}

    public String getLocation() {return Location;}
    public void setLocation() {this.Location = Location;}


}
