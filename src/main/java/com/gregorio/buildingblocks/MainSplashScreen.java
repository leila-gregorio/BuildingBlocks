package com.gregorio.buildingblocks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainSplashScreen extends AppCompatActivity {
    public static final String MYPREFS = "USERINFO";
    final int mode = Activity.MODE_PRIVATE;
    private SharedPreferences preferences;
    Handler handler;
    Intent intent;
    Animation anim;
    ImageView imageView;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView=(ImageView)findViewById(R.id.imgLogo); // Declare an imageView to show the animation.
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAuth = FirebaseAuth.getInstance();
                        mUser = mAuth.getCurrentUser();
                        preferences = getSharedPreferences(MYPREFS, mode);
                        String name = preferences.getString("name", "");

                        //If user is logged in or a guest name is saved in SharedPreferences
                        if (mUser != null || (preferences.contains("name") && !name.equals(""))) {
                            intent = new Intent(MainSplashScreen.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if (!preferences.contains("name") ||(preferences.contains("name") && name.equals("")) ) {
                            intent = new Intent(MainSplashScreen.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                },2000);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        imageView.startAnimation(anim);
    }

}