package com.gregorio.buildingblocks;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gregorio.buildingblocks.databinding.ActivityMain2Binding;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    //SharedPreferences Variables
    public static final String MYPREFS = "USERINFO";
    final int mode = Activity.MODE_PRIVATE;

    //Nav Bar Variables
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMain2Binding binding;

    //Firebase Storage Variables
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    //Firebase Authentication & Database Var
    FirebaseDatabase database ;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    //SharedPreference Variables
    SharedPreferences preferences;
    SharedPreferences.Editor myEditor;

    //Layout Variables
    Button btnAnimals;
    Button btnColors;
    Button btnAlphabet;
    TextView tvName;
    TextView tvWelcome;
    ImageView imvPicture;

    String newName;
    String newEmail;
    String newUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Nav Bar
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain2.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_developer_page, R.id.nav_edit_profile)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Object initialization
        tvName = (TextView) findViewById(R.id.tvName);
        tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        //Button object  initialization
        btnAnimals = (Button) findViewById(R.id.btnAnimals);
        btnColors = (Button) findViewById(R.id.btnColors);
        btnAlphabet = (Button) findViewById(R.id.btnAlphabet);

        //Shared Preferences
        preferences = getSharedPreferences(MYPREFS, mode);
        myEditor = preferences.edit();

        //User account
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance("https://buildingblocks-6029f-default-rtdb.firebaseio.com/");
        myRef = database.getReference("Accounts");

        //Set Welcome Message
        if (mAuth.getCurrentUser() != null) {
            getUserDetails();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setWelcomeMessage();

                }
            }, 2000);
        }else{
            setWelcomeMessage();
        }
        //Directs to Animal Soundboard on Button Click
        btnAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AnimalActivity.class);
                startActivity(intent);
                finish();
                try {
                    setImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //Directs to Color Soundboard on Button Click
        btnColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ColorsActivity.class);
                startActivity(intent);
                finish();
                try {
                    setImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //Directs to Alphabet Flashing on Button Click
        btnAlphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlphabetActivity.class);
                startActivity(intent);
                finish();
                try {
                    setImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // perform action when user is not logged in
        NavigationView navigationView = findViewById(R.id.nav_view);
        MenuItem item;
        navigationView.getMenu().findItem(R.id.item_home).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
                return false;
            }
        });
        //If User is not Logged in
        if (mAuth.getCurrentUser() == null) {
            navigationView.getMenu().findItem(R.id.logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.login).setVisible(true);
            //Cannot Edit Profile When Not Logged In
            navigationView.getMenu().findItem(R.id.nav_edit_profile).setVisible(false);
            item = navigationView.getMenu().findItem(R.id.login);
        }
        // If User is Logged in
        else {
            navigationView.getMenu().findItem(R.id.logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
            item = navigationView.getMenu().findItem(R.id.logout);
        }
        //Menu Item Listener for Item Clicks
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                int id = item.getItemId();
                if (id == R.id.logout) {
                    mAuth.signOut();
                    onResume();
                    clearImage();
                    //Remove from SharedPreferences
                    myEditor.remove("name").commit();
                    myEditor.remove("email").commit();
                    myEditor.remove("imageUri").commit();

                    //Directs User to Login Page
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(),"Successfully Logged Out.", Toast.LENGTH_LONG).show();

                } else if (id == R.id.login) {
                    //handle event login button pressed
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void setWelcomeMessage() {
        //Set Welcome Name in txtName Using SharedPreferences
        newName = preferences.getString("name", "");
        newEmail = preferences.getString("email", "");
        //Set Welcome Message
        tvWelcome.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        tvWelcome.setTextSize(15);
        tvWelcome.setGravity(Gravity.START);
        tvName.setVisibility(View.VISIBLE);
        tvName.setText(newName);
        //Navigation View Obj
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView tvEmail = headerView.findViewById(R.id.tvEmail);
        TextView tvNameProfile = headerView.findViewById(R.id.tvNameProfile);
        imvPicture = headerView.findViewById(R.id.imvProfilePic);
        //Sets the Navigation Bar User Details
        tvNameProfile.setText(newName);
        tvEmail.setText(newEmail);
        try {
            setImage();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),e+"Error", Toast.LENGTH_LONG).show();
        }
        if(newName ==null){
            Intent intent = new Intent(getApplicationContext(), GetStartedActivity.class);
            startActivity(intent);
        }
    }

    public void setImage() throws IOException {
        //String name = preferences.getString("name", "");
        String email = preferences.getString("email", "");
        String imageUri = preferences.getString("imageUri", "");

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        imvPicture = headerView.findViewById(R.id.ivPicture);
        if (mUser != null &&  !this.isDestroyed()) {
            storageReference = FirebaseStorage.getInstance().getReference("images/" +mUser.getEmail().toString());
            Glide
                    .with(this)
                    .load(Uri.parse(imageUri))
                    .override(200, 200)
                    .centerCrop()
                    //.error(R.drawable.image_error)
                    .placeholder(R.drawable.sample)
                    .into(imvPicture);
        }
    }
    //Retrieves user data from database
    protected void getUserDetails() {
        if (mUser != null) {
            myRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    newName = (String) dataSnapshot.child("name").getValue();
                    newEmail= (String) dataSnapshot.child("email").getValue();
                    newUri= (String) dataSnapshot.child("uri").getValue();

                    myEditor.putString("name", newName);
                    myEditor.putString("email", newEmail);
                    myEditor.putString("imageUri",newUri);

                    myEditor.apply();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Email is not Registered", Toast.LENGTH_SHORT).show();
        }
    }
    //Clears Sidebar Image
    protected void clearImage(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        imvPicture = headerView.findViewById(R.id.ivPicture);
        imvPicture.setImageResource(R.drawable.sample);
    }
    protected void onDestroy() {
        super.onDestroy();

        if (!this.isFinishing ()) {
            Glide.with(getApplicationContext()).pauseRequests();
        }
    }
}