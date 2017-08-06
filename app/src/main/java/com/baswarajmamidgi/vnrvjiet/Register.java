package com.baswarajmamidgi.vnrvjiet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText username;
    private EditText email;
    private EditText password;
    private TextView signin;
    private ImageView back;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.mail);
        signin = (TextView) findViewById(R.id.signin);
        back = (ImageView) findViewById(R.id.back);
        progressDialog = new ProgressDialog(Register.this);
        progressDialog.setMessage("Registering...");
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createuser();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void createuser() {

        final String name = username.getText().toString();
        String pass = password.getText().toString();
        String mail = email.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "enter Login id", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Password length should be atlest 6 chars", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(this, "enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            emailVerification();



                        }
                        if (!task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "Registration Failed",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }




                    }
                });
    }

    private void emailVerification() {


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();


                        } else {
                            Log.e("log", "sendEmailVerification", task.getException());
                            Toast.makeText(Register.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
        mAuth.signOut();
        startActivity(new Intent(Register.this, Loginactivity.class));


        return;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Register.this, Loginactivity.class));


    }
}
