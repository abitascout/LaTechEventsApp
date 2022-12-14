package com.example.latecheventsapp;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.auth.FirebaseUser;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {
    private EditText EmailTxt, PasswordTxt;
    private Button LoginBtn, SignUpBtn;
    private ProgressBar LoadingPB;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;




    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        EmailTxt = findViewById(R.id.navUser);
        PasswordTxt = findViewById(R.id.password);
        LoginBtn = findViewById(R.id.loginbutton);
        SignUpBtn = findViewById(R.id.sign_up_page_nav_button);
        LoadingPB = findViewById(R.id.loading);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String E = EmailTxt.getText().toString();
                String P = PasswordTxt.getText().toString();
                if (TextUtils.isEmpty(E) && TextUtils.isEmpty(P)) {
                    Toast.makeText(Login.this, "Please enter Email and Password", Toast.LENGTH_SHORT).show();
                }
                String pass = null;
                try {
                    pass = toHexString(getSHA(P));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                LoadingPB.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(E,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "checking to see if email is verified", Toast.LENGTH_SHORT).show();
                            checkEmailVerification();
                        }
                        else {
                            Toast.makeText(Login.this, "Error !", Toast.LENGTH_SHORT).show();
                            LoadingPB.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
            }
        });

    }

    private void checkEmailVerification() {
        FirebaseUser user = fAuth.getInstance().getCurrentUser();
        if (user.isEmailVerified()) {
            Toast.makeText(Login.this, "Checking to see if you are admin", Toast.LENGTH_SHORT).show();
            LoadingPB.setVisibility(View.GONE);
            Intent i = new Intent(Login.this, MainActivity.class);
            startActivity(i);
        }
        else {
            Toast.makeText(Login.this, "Email isn't verified", Toast.LENGTH_SHORT).show();
            LoadingPB.setVisibility(View.GONE);
        }
    }

    private void checkIfAdmin(String id) {
        DocumentReference df = fstore.collection("users").document(id);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess: "+ documentSnapshot.getData());
                if(documentSnapshot.getString("privileges")=="B") {
                    Toast.makeText(Login.this, "Normal Log in Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(Login.this, "Admin Log in Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });

    }
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1,hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length()<64) {
            hexString.insert(0,"0");
        }
        return hexString.toString();
    }

}
