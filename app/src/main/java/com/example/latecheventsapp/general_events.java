package com.example.latecheventsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.latecheventsapp.data.model.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link general_events#newInstance} factory method to
 * create an instance of this fragment.
 */
public class general_events extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView eventView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference eventRef = db.collection("Events").document("MOb43uHSEdK54WJqydqM");

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public general_events() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment general_events.
     */
    // TODO: Rename and change types and number of parameters
    public static general_events newInstance(String param1, String param2) {
        general_events fragment = new general_events();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_events, container, false);
        TextView eventView = view.findViewById(R.id.text_view_event);
        Button load = (Button) view.findViewById(R.id.load_button);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    eventRef.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists())
                                    {
                                        String name = documentSnapshot.getString("Event_Name");
                                        String desc = documentSnapshot.getString("Event_Desc");
                                        eventView.setText("Event Name: "+ name+"\n"+ "Event_Desc: "+ desc);
                                    }
                                    else {
                                        Toast.makeText(getContext().getApplicationContext(), "No Event Listed 1", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext().getApplicationContext(), "No Event Listed 2", Toast.LENGTH_SHORT).show();
                                }
                            });
                }


        });
        return view;
    }

}