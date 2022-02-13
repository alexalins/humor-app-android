package com.example.humorapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.humorapp.R;
import com.example.humorapp.adapter.FeelingAdapter;
import com.example.humorapp.model.Feeling;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    ArrayList<Feeling> arrayOfFeeling = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //
        inflateToolbar();
        startAdapter();
        //
        Button btnEdit = findViewById(R.id.button2);
        btnEdit.setOnClickListener(v -> {
            next();
        });
    }

    private void inflateToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView txtTitleBar = findViewById(R.id.title_bar);
        txtTitleBar.setText(getResources().getString(R.string.title_profile));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        //
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void startAdapter() {
        FeelingAdapter adapter = new FeelingAdapter(this, arrayOfFeeling);
        ListView listView = (ListView) findViewById(R.id.list_my_feeling);
        listView.setAdapter(adapter);
        mockFeeling();
    }

    private void next() {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    private void mockFeeling() {
        Feeling feeling1 = new Feeling();
        feeling1.setName("Sentimento 1");
        Feeling feeling2 = new Feeling();
        feeling2.setName("Sentimento 2");
        Feeling feeling3 = new Feeling();
        feeling3.setName("Sentimento 3");
        Feeling feeling4 = new Feeling();
        feeling4.setName("Sentimento 4");
        Feeling feeling5 = new Feeling();
        feeling5.setName("Sentimento 5");
        Feeling feeling6 = new Feeling();
        feeling6.setName("Sentimento 6");
        //
        arrayOfFeeling.add(feeling1);
        arrayOfFeeling.add(feeling2);
        arrayOfFeeling.add(feeling3);
        arrayOfFeeling.add(feeling4);
        arrayOfFeeling.add(feeling5);
        arrayOfFeeling.add(feeling6);
        arrayOfFeeling.addAll(arrayOfFeeling);
        arrayOfFeeling.addAll(arrayOfFeeling);
    }
}