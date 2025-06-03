package com.example.viber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registration extends AppCompatActivity {
    TextView text_gotologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        text_gotologin = findViewById(R.id.text_gotologin);
        text_gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent class is used to redirect from one
                //activity to another actvity
                Intent in = new Intent(Registration.this, MainActivity.class);
                startActivity(in);
            }
        });
        //save data in database
        //---get data of form to store in database ---
        EditText txt_name, txt_email, txt_mobno, txt_password;
        txt_name = findViewById(R.id.txt_name);
        txt_email = findViewById(R.id.txt_email);
        txt_mobno = findViewById(R.id.txt_mobno);
        txt_password = findViewById(R.id.txt_password);
        MaterialButton btn_signup;
        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, email, mobno, password;
                name = txt_name.getText().toString();
                email = txt_email.getText().toString();
                mobno = txt_mobno.getText().toString();
                password = txt_password.getText().toString();
                if (name.isEmpty() || email.isEmpty() || mobno.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Registration.this, "please fill all fields properly",
                            Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = task.getResult().getUser().getUid();
                                HashMap<String, String> user = new HashMap<>();
                                user.put("name", name);
                                user.put("mobno", mobno);
                                user.put("emailid", email);
                                user.put("password", password);
                                user.put("gender", "NA");
                                FirebaseDatabase.getInstance().getReference().
                                        child("users").child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(Registration.this, "Registration Successful",
                                                        Toast.LENGTH_LONG).show();
                                                Intent in = new Intent(Registration.this, MainActivity.class);
                                                startActivity(in);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Registration.this, e.getMessage().toString()
                                                        , Toast.LENGTH_LONG).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(Registration.this, task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registration.this, e.getMessage().toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}