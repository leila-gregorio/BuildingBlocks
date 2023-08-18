package com.gregorio.buildingblocks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {
    //Layout Object
    TextView tvHaveAccount;
    EditText edtEmail;
    EditText edtName;
    EditText edtPassword;
    EditText edtConfirmPassword;
    Button btnRegister;
    //ShredPrefrences Variables
    public static final String MYPREFS = "USERINFO";
    final int mode = Activity.MODE_PRIVATE;
    private SharedPreferences preferences;
    private SharedPreferences.Editor myEditor;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Layout Objects Initialization
        edtEmail = (EditText) findViewById(R.id.edtRegisterEmail);
        edtPassword = (EditText) findViewById(R.id.edtRegisterPassword);
        edtName = (EditText) findViewById(R.id.edtRegisterName);
        edtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
        tvHaveAccount = (TextView)findViewById(R.id.tvHaveAccount);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //Firebase
        database = FirebaseDatabase.getInstance("https://buildingblocks-6029f-default-rtdb.firebaseio.com/");
         myRef = database.getReference("Accounts");
        //SharePreferencesObj
        preferences = getSharedPreferences(MYPREFS, mode);
        myEditor = preferences.edit();

        //Directs the user to login activity
        tvHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        //
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }
        });

    }

    private void performAuth() {
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();



        if (!email.matches(emailPattern)) {
            edtEmail.setError("Enter Correct Email.");
        } else if (password.isEmpty() || password.length() < 6) {
            edtPassword.setError("Password Must Have 6 or more Characters.");
        } else if (!password.equals(confirmPassword)) {
            edtConfirmPassword.setError("Password Does Not Match.");
        } else {
            progressDialog.setMessage("Please Wait While Registration");
            progressDialog.setMessage("Registration");
            progressDialog.setCancelable(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,"Registration Successful", Toast.LENGTH_SHORT);
                        // Get Account UID from Firebase Authentication
                        savePreferences();
                        sendUsertoNextActivity();

                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    protected void savePreferences() {
        SharedPreferences mySharedPreferences = getSharedPreferences(MYPREFS, mode);
        SharedPreferences.Editor myEditor = mySharedPreferences.edit();
        myEditor.putString("name", edtName.getText().toString());
        myEditor.putString("email", edtEmail.getText().toString());

        myEditor.commit();
    }
    private void sendUsertoNextActivity() {
        SharedPreferences mySharedPreferences = getSharedPreferences(MYPREFS, mode);
        String newName = mySharedPreferences.getString("name", "");
        String newEmail = mySharedPreferences.getString("email", "");
        //Set User Data to Firebase
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAuth = FirebaseAuth.getInstance();
                mUser = mAuth.getCurrentUser();
                //String newName = preferences.getString("name", null);
                preferences = getSharedPreferences(MYPREFS, mode);
                String name = preferences.getString("name", "");
                String uri = "";
                Account account = new Account(newName, newEmail, mUser.getUid(),uri);
                myRef.child(mUser.getUid()).setValue(account);
                progressDialog.setMessage("Please Wait For A While");
                progressDialog.setMessage("Registration");

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        }, 2000);

    }

}