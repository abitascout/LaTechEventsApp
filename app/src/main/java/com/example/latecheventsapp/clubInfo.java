package com.example.latecheventsapp;

public class clubInfo {
    // string variable for
    // storing employee name.
    private String clubName;

    // string variable for storing
    // employee contact number
    private String clubType;

    // string variable for storing
    // employee address.
    private String officerAddress;

    // an empty constructor is
    // required when using
    // Firebase Realtime Database.
    public clubInfo() {

    }

    // created getter and setter methods
    // for all our variables.
    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubType() {
        return clubType;
    }

    public void setClubType(String clubType) {
        this.clubType = clubType;
    }

    public String getOfficerAddress() {
        return officerAddress;
    }

    public void setOfficerAddress(String officerAddress) {
        this.officerAddress = officerAddress;
    }
}
