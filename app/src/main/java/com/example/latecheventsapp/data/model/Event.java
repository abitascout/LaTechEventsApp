package com.example.latecheventsapp.data.model;

import static java.lang.String.*;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties

public class Event implements Parcelable {
    private String Tag;
    private String Event_Name;
    private String Event_Desc;
    private String Club_Name;
    private Timestamp Start;
    private Timestamp End;
    private String Location;


    public static  final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public Event createFromParcel(Parcel in)
        {
            return new Event(in);
        }
        public Event[] newArray(int size){
            return  new Event[size];
        }
    };

    public Event (String Event_Name, String Event_Desc, String Club_Name, Timestamp Start, String Location, Timestamp End, String Tag)
    {
        this.Event_Name = Event_Name;
        this.Event_Desc = Event_Desc;
        this.Club_Name = Club_Name;
        this.Start = Start;
        this.End = End;
        this.Location = Location;
        this.Tag = Tag;


    }

    public Event() {}
    // empty constructor needed



    public  String getEvent_Name() {return  Event_Name;}
    public void setEvent_Name(String Event_Name) {this.Event_Name = Event_Name;}

    public String getEvent_Desc() {return Event_Desc;}
    public void setEvent_Desc(String Event_Desc) {this.Event_Desc = Event_Desc;}

    public String getClub_Name() {return Club_Name;}
    public void setClub_Name(String Club_Name) {this.Club_Name = Club_Name;}

    public Timestamp getStart() {return Start;}
    public void setStart(Timestamp Start) {this.Start = Start;}

    public Timestamp getEnd() {return  End;}
    public void setEnd(Timestamp End) {this.End = End;}

    public String getLocation() {return Location;}
    public void setLocation(String Location) {this.Location = Location;}

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }


    private Event (Parcel in){
        this.Club_Name = in.readString();
        this.End = (Timestamp) in.readValue(getClass().getClassLoader());
        this.Event_Desc = in.readString();
        this.Event_Name = in.readString();
        this.Location = in.readString();
        this.Start = (Timestamp) in.readValue(getClass().getClassLoader());
        this.Tag = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.Club_Name);
        dest.writeString(valueOf(this.End));
        dest.writeString(this.Event_Desc);
        dest.writeString(this.Event_Name);
        dest.writeString(this.Location);
        dest.writeString(valueOf(this.Start));
        dest.writeString(this.Tag);


    }
}


