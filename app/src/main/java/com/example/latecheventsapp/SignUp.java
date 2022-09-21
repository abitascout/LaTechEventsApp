package com.example.latecheventsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private EditText EmailTxt, PasswordTxt1, PasswordTxt2;
    private Button SignUpBtn;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signin);

        EmailTxt = findViewById(R.id.username);
        PasswordTxt1 = findViewById(R.id.password1);
        PasswordTxt2 = findViewById(R.id.password2);
        SignUpBtn = findViewById(R.id.sign_up_page_nav_button);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String E = EmailTxt.getText().toString();
                String P1 = PasswordTxt1.getText().toString();
                String P2 = PasswordTxt2.getText().toString();

                if (TextUtils.isEmpty(E) && TextUtils.isEmpty(P1) && TextUtils.isEmpty(P2)) {
                    Toast.makeText(SignUp.this, "Please enter Email and Password", Toast.LENGTH_SHORT).show();
                }
                fAuth.createUserWithEmailAndPassword(E,P2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(SignUp.this, "User Created.", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fstore.collection("users").document(user.getUid());
                            Map<String,Object> userInfo = new HashMap<>();
                            userInfo.put("Email",EmailTxt.getText().toString());
                            userInfo.put("Password",PasswordTxt2.getText().toString());
                            userInfo.put("privilege","A");

                            df.set(userInfo);

                            Intent i = new Intent(SignUp.this, Login.class);
                            startActivity(i);

                        }
                        else {
                            Toast.makeText(SignUp.this, "Error ! "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
