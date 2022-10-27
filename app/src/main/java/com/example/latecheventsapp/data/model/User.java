package com.example.latecheventsapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties

public class User implements Parcelable {
    private String Email;
    private String privilege;



    public static  final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public User createFromParcel(Parcel in)
        {
            return new User(in);
        }
        public User[] newArray(int size){
            return  new User[size];
        }
    };

    public User (String Email, String privilege)
    {
        this.Email = Email;
        this.privilege = privilege;

    }

    public User() {}
    // empty constructor needed



    public  String getEmail() {return  Email;}
    public void setEmail(String Email) {this.Email = Email;}

    public String getprivilege() {return privilege;}
    public void setprivilege(String privilege) {this.privilege = privilege;}




    private User (Parcel in){
        this.Email = in.readString();
        this.privilege = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.Email);
        dest.writeString(this.privilege);

    }
}

