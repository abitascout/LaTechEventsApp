package com.example.latecheventsapp.data.model;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

@IgnoreExtraProperties

public class Event {
    private String Event_Name;
    private String Event_Desc;
    private String Club_Name;
    private @ServerTimestamp Date timestamp;
    private String Location;
    private String eventId;

    public Event (String Event_Name, String Event_Desc, String Club_Name, Date timestamp, String Location, String eventId)
    {
        this.Event_Name = Event_Name;
        this.Event_Desc = Event_Desc;
        this.Club_Name = Club_Name;
        this.timestamp = timestamp;
        this.Location = Location;
        this.eventId = eventId;

    }

    public  String getEvent_Name() {return  Event_Name;}
    public void setEvent_Name() {this.Event_Name = Event_Name;}

    public String getEvent_Desc() {return Event_Desc;}
    public void setEvent_Desc() {this.Event_Desc = Event_Desc;}

    public String getClub_Name() {return Club_Name;}
    public void setClub_Name() {this.Club_Name = Club_Name;}

    public Date getTimestamp() {return timestamp;}
    public void setTimestamp() {this.timestamp = timestamp;}

    public String getLocation() {return Location;}
    public void setLocation() {this.Location = Location;}

    public String getEventId() {return eventId;}
    public void setEventId() {this.eventId = eventId;}
}
