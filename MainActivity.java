package com.example.viber;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
TextView text_gotoregister;
Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //hide action bar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
        AppCompatEditText txt_loginemail, txt_loginpassword;
        txt_loginemail=findViewById(R.id.txt_loginemail);
        txt_loginpassword=findViewById(R.id.txt_loginpassword);

        text_gotoregister = findViewById(R.id.text_gotoregister);
        text_gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent class is used to redirect from one
                //activity to another actvity
                Intent in = new Intent(MainActivity.this, Registration.class);
                startActivity(in);
            }
        });
        //end of onclicklistener of TextView
        // second intent
        signin=findViewById(R.id.sign);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;
                email=txt_loginemail.getText().toString();
                password=txt_loginpassword.getText().toString();
                if(email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Please Input email and Password",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Welcome To VIBER",
                                                Toast.LENGTH_LONG).show();
                                        Intent in = new Intent(MainActivity.this, Home.class);
                                        startActivity(in);
                                    } else {
                                        Toast.makeText(MainActivity.this,
                                                "Invalid Credentials",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent in=new Intent(this,Home.class);
            startActivity(in);
        }
    }
}