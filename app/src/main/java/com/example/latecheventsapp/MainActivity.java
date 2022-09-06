package com.example.latecheventsapp;

import android.os.Bundle;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    TextView textView;
    EditText subjectInputText;

    Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Set up navigation stuff
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener((toggle));
        toggle.syncState();

        //Input stuff
        textView = (TextView) findViewById(R.id.textView);

        // Get the inputs from the create events fragment
        //subjectInputText = (EditText) findViewById(R.id.inputEventSubjectText);

        // Allows the Title of the toolbar to be changed.
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle("LATEvents");
        setSupportActionBar(mActionBarToolbar);



    }


    @Override // To switch between navigation screens.
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_create_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new create_events()).commit();
                mActionBarToolbar.setTitle("Create Events");
                break;
            case R.id.nav_general_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new general_events()).commit();
                mActionBarToolbar.setTitle("General Events");
                break;
            case R.id.nav_myevents:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyEvents()).commit();
                mActionBarToolbar.setTitle("My Events");
                break;
            case R.id.nav_preferences:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new preferences()).commit();
                mActionBarToolbar.setTitle("Preferences");
                break;
        }
        return true;
    }

    @Override // Close the drawer
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
        System.out.println(subjectInputText);
    }
}