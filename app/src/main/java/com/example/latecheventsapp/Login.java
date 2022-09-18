package com.example.latecheventsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class Login extends Fragment {
    private EditText EmailTxt, PasswordTxt;
    private Button LoginBtn, SignUpBtn;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;

    boolean valid = true;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);

        EmailTxt = view.findViewById(R.id.username);
        PasswordTxt = view.findViewById(R.id.password);
        LoginBtn = view.findViewById(R.id.loginbutton);
        SignUpBtn = view.findViewById(R.id.sign_up_page_nav_button);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String E = EmailTxt.getText().toString();
                String P = PasswordTxt.getText().toString();
                if (TextUtils.isEmpty(E) && TextUtils.isEmpty(P)) {
                    Toast.makeText(getContext(), "Please enter Email and Password", Toast.LENGTH_SHORT).show();
                }
                fAuth.signInWithEmailAndPassword(E,P).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = fAuth.getCurrentUser();
                            checkIfAdmin(user.getUid());

                        }
                        else {
                            Toast.makeText(getContext(), "Error !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,new SignUp());
                ft.commit();
            }
        });

        return view;
    }
    private void checkIfAdmin(String id) {
        DocumentReference df = fstore.collection("users").document(id);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess: "+ documentSnapshot.getData());
                if(documentSnapshot.getString("privileges")=="B") {
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    ((Activity) getActivity()).overridePendingTransition(0,0);
                }
                else {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container,new general_events());
                    ft.commit();
                }
            }
        });

    }

}
