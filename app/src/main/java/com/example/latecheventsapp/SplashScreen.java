package com.example.latecheventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       // if (user != null) {
            // User is signed in
            // Start home activity
        startActivity(new Intent(SplashScreen.this, MainActivity.class));
       // } else {
            // No user is signed in
            // start login activity
           // startActivity(new Intent(SplashScreen.this, Login.class));
        //}

        // close splash activity
        finish();
    }
}