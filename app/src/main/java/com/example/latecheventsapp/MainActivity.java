package com.example.latecheventsapp;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.latecheventsapp.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference users = db.collection("users");
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("General Events");
        setSupportActionBar(toolbar);



        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new general_events()).commit();

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener((toggle));
        toggle.syncState();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_create_events:
                toolbar.setTitle("Create Event");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new create_events()).commit();
                break;
            case R.id.nav_general_events:
                toolbar.setTitle("General Events");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new general_events()).commit();
                break;
           /* case R.id.nav_myevents:
                toolbar.setTitle("My Events");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyEvents()).commit();
                break;
            case R.id.nav_preferences:
                toolbar.setTitle("Prefrences");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new preferences()).commit();
                break; */
            case R.id.logout:
                Intent i = new Intent(MainActivity.this, Login.class);
                FirebaseAuth.getInstance().getCurrentUser();
                FirebaseAuth.getInstance().signOut();
                startActivity(i);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }





}