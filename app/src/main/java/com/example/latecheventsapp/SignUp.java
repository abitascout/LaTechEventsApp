package com.example.latecheventsapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SignUp extends AppCompatActivity {
    private EditText EmailTxt, PasswordTxt1, PasswordTxt2;
    private Button SignUpBtn, BackBtn;
    private FirebaseAuth fAuth;
    private ProgressBar LoadingPB;
    private String pr;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signin);

        EmailTxt = findViewById(R.id.navUser);
        PasswordTxt1 = findViewById(R.id.password1);
        PasswordTxt2 = findViewById(R.id.password2);
        SignUpBtn = findViewById(R.id.sign_up_page_nav_button);
        BackBtn = findViewById(R.id.back);
        LoadingPB = findViewById(R.id.loading);

        fAuth = FirebaseAuth.getInstance();


        pr = "B";

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingPB.setVisibility(View.VISIBLE);

                String E = EmailTxt.getText().toString();
                String P1 = PasswordTxt1.getText().toString();
                String P2 = PasswordTxt2.getText().toString();

                if (TextUtils.isEmpty(E) && TextUtils.isEmpty(P1) && TextUtils.isEmpty(P2)) {
                    Toast.makeText(SignUp.this, "Please enter Email and Password", Toast.LENGTH_SHORT).show();
                }

                String pass = null;
                try {
                    pass = toHexString(getSHA(P2));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                fAuth.createUserWithEmailAndPassword(E, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            LoadingPB.setVisibility(View.GONE);
                            Toast.makeText(SignUp.this, "Sending verification email, check spam", Toast.LENGTH_SHORT).show();
                            user.sendEmailVerification();
                            Intent i = new Intent(SignUp.this, Login.class);
                            startActivity(i);

                        }
                    }
                });
            }
        });
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp.this, Login.class);
                startActivity(i);
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

   // private void createUser(String Email, String Privilges) {
     //   LoadingPB.setVisibility(View.VISIBLE);
       // User user = new User();
        //user.setEmail(Email);
        //user.setprivilege(Privilges);
        //userRef.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
          //  @Override
          //  public void onSuccess(DocumentReference documentReference) {
           //     FirebaseUser user = fAuth.getCurrentUser();
           //     Toast.makeText(SignUp.this, "Sending verification email, check spam", Toast.LENGTH_SHORT).show();
           //     user.sendEmailVerification();
           //     LoadingPB.setVisibility(View.GONE);
           //     Intent i = new Intent(SignUp.this, Login.class);
           //     startActivity(i);
        //    }
    //    });
  //  }
}
