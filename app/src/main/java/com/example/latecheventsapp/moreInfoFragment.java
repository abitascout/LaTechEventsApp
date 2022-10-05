package com.example.latecheventsapp;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class moreInfoFragment extends Fragment {


    public moreInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static moreInfoFragment newInstance(String param1, String param2) {
        moreInfoFragment fragment = new moreInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_info, container, false);

        // Change Toolbar title.
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("More Information");

        return view;
    }
}