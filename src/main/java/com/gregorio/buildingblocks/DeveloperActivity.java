package com.gregorio.buildingblocks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;


public class DeveloperActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        String citation1 = this.getString(R.string.citation_1);
        String citation2 =  this.getString(R.string.citation_2);
        String citation3 =  this.getString(R.string.citation_3);
        String citation4 =  this.getString(R.string.citation_4);
        String citation5 =  this.getString(R.string.citation_5);
        String citation6 =  this.getString(R.string.citation_6);
        String citation7 =  this.getString(R.string.citation_7);
        String citation8 =  this.getString(R.string.citation_8);
        String citation9 =  this.getString(R.string.citation_9);
        String citation10 =  this.getString(R.string.citation_10);
        String citation11 =  this.getString(R.string.citation_11);
        String citation12 =  this.getString(R.string.citation_12);
        String citation13 =  this.getString(R.string.citation_13);
        String citation14 =  this.getString(R.string.citation_14);
        String citation15 =  this.getString(R.string.citation_15);
        String citation16 =  this.getString(R.string.citation_16);


        //Back Button
        ImageButton backBtn = (ImageButton) findViewById(R.id.btnBack2);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeveloperActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        String[] resources = {"Amethyst Studio","Ben OBrien","Color Vectors","DAPA Images",
                "Drawcee","Icons8","Mybeautifulfiles","Printable Pretty","Printablee",
                "Sketchify Philippines","Sketchify","Tutorialspoint","The Visual Team"};

        String[] citation = {citation1,citation2,citation3,citation4+"\n"+citation5,
                citation6,citation7, citation8,citation9,citation10, citation11, citation12+"\n"+
                citation13+"\n"+citation14,citation15,citation16};

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.list_view, resources);
        listView.setDivider(new ColorDrawable(Color.parseColor("#C4F0DB")));
        listView.setDividerHeight(2);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar snackbar = Snackbar.make(view,""+citation[position],Snackbar.LENGTH_SHORT);
                View snackbarView = snackbar.getView();
                TextView snackTextView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                snackbarView.setBackgroundColor(Color.parseColor("#6256AF"));
                snackTextView.setTextColor(Color.parseColor("#FFFFFF"));
                snackTextView.setMaxLines(9);
                snackbar.show();


            }
        });

    }


}