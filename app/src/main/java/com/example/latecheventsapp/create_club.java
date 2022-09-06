package com.example.latecheventsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link create_club#newInstance} factory method to
 * create an instance of this fragment.
 */
public class create_club extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public create_club() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment create_club.
     */
    // TODO: Rename and change types and number of parameters
    public static create_club newInstance(String param1, String param2) {
        create_club fragment = new create_club();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private EditText name, type, email;
    private Button createBtn;

    FirebaseDatabase fd;
    DatabaseReference dr;

    clubInfo ci;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_club, container, false);

        name = view.findViewById(R.id.club_name);
        type = view.findViewById(R.id.club_type);
        email = view.findViewById(R.id.officer_email);
        createBtn = view.findViewById(R.id.button);

        fd = FirebaseDatabase.getInstance();
        dr = fd.getReference("clubs");

        ci = new clubInfo();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String N = name.getText().toString();
                String T = type.getText().toString();
                String E = email.getText().toString();

                if (TextUtils.isEmpty(N) && TextUtils.isEmpty(T) && TextUtils.isEmpty(E)) {
                    Toast.makeText(getContext(), "Error !", Toast.LENGTH_SHORT).show();
                }
                else {
                    addToDatabase(N,T,E);
                }
            }
        });

        return view;
    }
    private void addToDatabase(String name, String type, String email) {
        ci.setClubName(name);
        ci.setClubType(type);
        ci.setOfficerAddress(email);
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //dr.setValue()
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}