package com.gregorio.buildingblocks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    //SharedPreferences Variables
    public static final String MYPREFS = "USERINFO";
    final int mode = Activity.MODE_PRIVATE;
    //Layout Variables
    TextView tvNewAccount;
    EditText edtEmail;
    EditText edtPassword;
    Button btnLogin;
    Button btnSkipLogin;
    ProgressDialog progressDialog;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    //Firebase Authentication Variables
    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Layout Objects Initialization
        edtEmail = (EditText) findViewById(R.id.edtLoginEmail);
        edtPassword = (EditText) findViewById(R.id.edtLoginPassword);
        tvNewAccount = (TextView) findViewById(R.id.tvNewAccount);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSkipLogin = (Button) findViewById(R.id.btnGetStarted);

        progressDialog = new ProgressDialog(this);

        //Firebase Initialization
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        //Create New Account
        tvNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
        //Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });
        //Skip Login
        btnSkipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, GetStartedActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void performLogin() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        //Email Input Vaidation
        if (!email.matches(emailPattern)) {
            edtEmail.setError("Enter Correct Email.");
        } else if (password.isEmpty() || password.length() < 6) {
            edtPassword.setError("Password Must Have 6 or more Characters.");
        } else {
            progressDialog.setMessage("Please Wait While Logging in");
            progressDialog.setMessage("Logging in");
            progressDialog.setCancelable(false);
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Login Successful", Toast.LENGTH_SHORT);
                        sendUsertoNextActivity();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUsertoNextActivity(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}