package com.gregorio.buildingblocks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class GetStartedActivity extends AppCompatActivity {

    //Layout Variables
    private Intent intent;
    ImageView imvProfilePic;
    ImageView imvPicture;
    ImageButton backBtn;
    Button btnUpload;
    Button btnGetStarted;
    EditText edtname;
    EditText edtemail;
    TextView tvProfile;

    //Firebase Authentication
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    //SharedPreferences Variables
    public static final String MYPREFS = "USERINFO";
    final int mode = Activity.MODE_PRIVATE;
    private SharedPreferences preferences;
    private SharedPreferences.Editor myEditor;

    //Firebase Storage Variables
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstarted);
        //Layout Obj Initialization
        edtname = (EditText) findViewById(R.id.edtName);
        edtemail = (EditText) findViewById(R.id.edtEmail);
        imvProfilePic = (ImageView) findViewById(R.id.imvProfilePic);
        btnGetStarted = (Button) findViewById(R.id.btnStart);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        tvProfile = (TextView) findViewById(R.id.tvProfile);

        //Back Button
        backBtn = (ImageButton) findViewById(R.id.btnBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //SharePreferencesObj
        preferences = getSharedPreferences(MYPREFS, mode);
        myEditor = preferences.edit();

        //Firebase Obj
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        checkInfo();


        //Image Upload Upon Button Click
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUser !=null){
                    updateRecord(edtemail.getText().toString());
                }
                saveInfo();


            }
        });
    }

    protected void onPause() {
        savePreferences();
        super.onPause();
    }

    protected void savePreferences() {
        myEditor.putString("name", edtname.getText().toString());
        myEditor.commit();
    }

    public void checkInfo() {
        //User account
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        //Set Welcome Name in txtName Using SharedPreferences
        String oldName = preferences.getString("name", "");
        String oldEmail = preferences.getString("email", "");

        // If "name" is Saved in SharedPreferences & is not Logged in
        if (preferences.contains("name")&& mUser == null){
            tvProfile.setText("Let's edit your Profile");
            btnUpload.setVisibility(View.INVISIBLE);
            edtemail.setVisibility(View.INVISIBLE);
        }
        //If User is already Logged in
        else if (mUser != null) {
            edtname.setText(oldName);
            edtemail.setText(oldEmail);
            tvProfile.setText("Let's edit your Profile");
            ProgressDialog progressDialog = new ProgressDialog(this);
        }
        // If "name" is not Saved in SharedPreferences and is not Logged in
        else if ( mUser == null && !preferences.contains("name")){
                tvProfile.setText("Let's setup your Profile");
                edtemail.setVisibility(View.INVISIBLE);
                btnUpload.setVisibility(View.INVISIBLE);
        }
        }
    private void saveInfo() {
        String name = edtname.getText().toString();
        String email = edtname.getText().toString();
        //Validates if edtName is empty
        if (TextUtils.isEmpty(name)) {
            edtname.setError("Name cannot be empty");
        } else {
            //Shared Preferences to Save User Info
            myEditor.putString("name", edtname.getText().toString());
            myEditor.putString("email", edtemail.getText().toString());
            myEditor.apply();
            Toast.makeText(getApplicationContext(), "Data Successfully Saved", Toast.LENGTH_LONG).show();
            intent = new Intent(GetStartedActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }private void saveToFirebase(){
        //User account
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String oldName = preferences.getString("name", "");
        String oldEmail = preferences.getString("email", "");
            //If User is already Logged in
            if (mUser != null) {
                ProgressDialog progressDialog = new ProgressDialog(this);
                //Firebase
                database = FirebaseDatabase.getInstance("https://buildingblocks-6029f-default-rtdb.firebaseio.com/");
                myRef = database.getReference("Accounts");
                String name = edtname.getText().toString();
                String email = edtemail.getText().toString();

                //Set User Data to Firebase
                Account account = new Account(name, email);
                myRef.child(mUser.getUid()).setValue(account);
                progressDialog.setMessage("Please Wait For A While");
                progressDialog.setMessage("Saving Update");


            }
        }
    private void saveToFirebase(String uri){
        //User account
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String oldName = preferences.getString("name", "");
        String oldEmail = preferences.getString("email", "");
        //If User is already Logged in
        if (mUser != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            //Firebase
            database = FirebaseDatabase.getInstance("https://buildingblocks-6029f-default-rtdb.firebaseio.com/");
            myRef = database.getReference("Accounts");
            String name = edtname.getText().toString();
            String email = edtemail.getText().toString();

            //Set User Data to Firebase
            Account account = new Account(name, email, uri);
            myRef.child(mUser.getUid()).setValue(account);
            progressDialog.setMessage("Please Wait For A While");
            progressDialog.setMessage("Saving Update");


        }
    }
    private void updateRecord(String email) {
        // User data change listener

        mUser.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Data  Authenticated", Toast.LENGTH_LONG).show();

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data!=null) {
            imageUri = data.getData();
            myEditor.putString("imageUri", imageUri.toString());
            myEditor.apply();
            imvProfilePic.setImageURI(imageUri);
            uploadPicture(imageUri);
        }
    }


    private void uploadPicture(Uri imageUri) {
        //Displays Image Upload Process Dialog
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        String email = preferences.getString("email", "");
        StorageReference mountainsRef = storageReference.child("images/"+email);
        mountainsRef.putFile(imageUri)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    myEditor.apply();
                    Toast.makeText(getApplicationContext(),"Image Uploaded", Toast.LENGTH_LONG).show();
                    //Firebase
                    database = FirebaseDatabase.getInstance("https://buildingblocks-6029f-default-rtdb.firebaseio.com/");
                    myRef = database.getReference("Accounts");
                    //Set User Data to Firebase
                    mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final Uri downloadUrl = uri;
                            String sUrl = downloadUrl.toString();
                            saveToFirebase(sUrl);
                            myEditor.putString("imageUri", sUrl);
                            myEditor.commit();
                        }
                    });

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                    @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(),"Failed To Upload Image",Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progressPercent = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            pd.setMessage("Percentage: " +(int) progressPercent +"%");
                        }
                    });
    }
    private void choosePicture(){
        //Allows users to choose a picture from files
        intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
        }
    }
