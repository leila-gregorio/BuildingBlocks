package com.gregorio.buildingblocks;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class AlphabetActivity extends Activity {
    GridView grid;
    static final String[] letters = new String[] {
            "Aa", "Bb", "Cc", "Dd",
            "Ee", "Ff", "Gg", "Hh",
            "Ii", "Jj", "Kk", "Ll",
            "Mm", "Nn", "Oo", "Pp",
            "Qq", "Rr", "Ss", "Tt",
            "Uu", "Vv", "Ww", "Xx",
            "Yy", "Zz"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);
        ImageView img = (ImageView) findViewById(R.id.imageView);
        img.setImageResource(R.drawable.alphabet);
        grid = (GridView) findViewById(R.id.gridView);

        //Back Button
        ImageButton backBtn = (ImageButton) findViewById(R.id.btnOther1);
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(AlphabetActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.adapter_textview, letters);


        grid.setAdapter(adapter);
        grid.setOnItemClickListener((parent, v, position, id) -> {
            String letters = (String) parent.getItemAtPosition(position);
            Toast toast = new Toast(getApplicationContext());
            ImageView view = new ImageView(getApplicationContext());
            if (letters.equals("Aa")) {
                view.setImageResource(R.drawable.a);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Bb")) {
                view.setImageResource(R.drawable.b);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Cc")) {
                view.setImageResource(R.drawable.c);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Dd")) {
                view.setImageResource(R.drawable.d);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Ee")) {
                view.setImageResource(R.drawable.e);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Ff")) {
                view.setImageResource(R.drawable.f);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Gg")) {
                view.setImageResource(R.drawable.g);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Hh")) {
                view.setImageResource(R.drawable.h);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Ii")) {
                view.setImageResource(R.drawable.i);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Jj")) {
                view.setImageResource(R.drawable.j);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Kk")) {
                view.setImageResource(R.drawable.k);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Ll")) {
                view.setImageResource(R.drawable.l);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Mm")) {
                view.setImageResource(R.drawable.m);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Nn")) {
                view.setImageResource(R.drawable.n);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Oo")) {
                view.setImageResource(R.drawable.o);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Pp")) {
                view.setImageResource(R.drawable.p);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Qq")) {
                view.setImageResource(R.drawable.q);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Rr")) {
                view.setImageResource(R.drawable.r);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Ss")) {
                view.setImageResource(R.drawable.s);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Tt")) {
                view.setImageResource(R.drawable.t);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Uu")) {
                view.setImageResource(R.drawable.u);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Vv")) {
                view.setImageResource(R.drawable.v);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Ww")) {
                view.setImageResource(R.drawable.w);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Xx")) {
                view.setImageResource(R.drawable.x);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Yy")) {
                view.setImageResource(R.drawable.y);
                toast.setView(view);
                toast.show();
            }
            else if (letters.equals("Zz")) {
                view.setImageResource(R.drawable.z);
                toast.setView(view);
                toast.show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Please select a letter.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}