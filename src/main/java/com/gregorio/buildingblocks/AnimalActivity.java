package com.gregorio.buildingblocks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AnimalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);


        //Back Button
        ImageButton backBtn = (ImageButton) findViewById(R.id.btnOther);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnimalActivity.this, MainActivity.class);
                startActivity(intent);
                //onBackPressed();
            }
        });
        //Button Obj Initialization
        Button btnCat = (Button) findViewById(R.id.btnCat);
        Button btnDog = (Button) findViewById(R.id.btnDog);
        Button btnSnake = (Button) findViewById(R.id.btnSnake);
        Button btnFrog = (Button) findViewById(R.id.btnFrog);
        Button btnMonkey = (Button) findViewById(R.id.btnMonkey);
        Button btnGoat = (Button) findViewById(R.id.btnGoat);
        Button btnPig = (Button) findViewById(R.id.btnPig);
        Button btnChicken = (Button) findViewById(R.id.btnChicken);

        final MediaPlayer catSound = MediaPlayer.create(getApplicationContext(), R.raw.cat_sound);
        final MediaPlayer dogSound = MediaPlayer.create(getApplicationContext(), R.raw.dog_sound);
        final MediaPlayer snakeSound = MediaPlayer.create(getApplicationContext(), R.raw.snake_sound);
        final MediaPlayer frogSound = MediaPlayer.create(getApplicationContext(), R.raw.frog_sound);
        final MediaPlayer monkeySound = MediaPlayer.create(getApplicationContext(), R.raw.monkey_sound);
        final MediaPlayer goatSound = MediaPlayer.create(getApplicationContext(), R.raw.goat_sound);
        final MediaPlayer pigSound = MediaPlayer.create(getApplicationContext(), R.raw.pig_sound);
        final MediaPlayer chickenSound = MediaPlayer.create(getApplicationContext(), R.raw.chicken_sound);


        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnCat:
                        catSound.start();
                        break;
                    case R.id.btnDog:
                        dogSound.start();
                        break;
                    case R.id.btnSnake:
                        snakeSound.start();
                        break;

                    case R.id.btnFrog:
                        frogSound.start();
                        break;
                    case R.id.btnMonkey:
                        monkeySound.start();
                        break;
                    case R.id.btnGoat:
                        goatSound.start();
                        break;

                    case R.id.btnPig:
                        pigSound.start();
                        break;
                    case R.id.btnChicken:
                        chickenSound.start();
                        break;
                }
            }
        };
        btnCat.setOnClickListener(v);
        btnDog.setOnClickListener(v);
        btnSnake.setOnClickListener(v);
        btnFrog.setOnClickListener(v);
        btnMonkey.setOnClickListener(v);
        btnGoat.setOnClickListener(v);
        btnPig.setOnClickListener(v);
        btnChicken.setOnClickListener(v);

    }
}