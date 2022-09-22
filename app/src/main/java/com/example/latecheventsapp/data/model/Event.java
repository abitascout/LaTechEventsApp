package com.example.latecheventsapp.data.model;

import android.os.Parcelable;
import android.os.Parcel;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties

public class Event implements Parcelable {
    private String Event_Name;
    private String Event_Desc;
    private String Club_Name;
    private Timestamp Start;
    private Timestamp End;
    private String Location;

    public Event (String Event_Name, String Event_Desc, String Club_Name, Timestamp Start, String Location, Timestamp End)
    {
        this.Event_Name = Event_Name;
        this.Event_Desc = Event_Desc;
        this.Club_Name = Club_Name;
        this.Start = Start;
        this.End = End;
        this.Location = Location;


    }

    public Event() {}

    protected Event(Parcel in)
    {
        Event_Name = in.readString();
        Event_Desc = in.readString();
        Club_Name = in.readString();
        Location = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };



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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Event_Name);
        parcel.writeString(Event_Desc);
        parcel.writeString(Club_Name);
        parcel.writeString(Location);
    }
}


