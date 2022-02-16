package com.example.humorapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.humorapp.R;
import com.example.humorapp.adapter.ItemAddAdpater;
import com.example.humorapp.model.Feeling;
import com.example.humorapp.model.User;
import com.example.humorapp.util.LoginUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class AddFeelingActivity extends AppCompatActivity {
    private ArrayList<Feeling> arrayOfFeeling = new ArrayList<>();
    private ProgressBar progressBar;
    private Button btnSave;
    private FirebaseDatabase database;
    private static final String TAG = "HUMOR";
    private User user;
    private Feeling feeling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feeling);
        database = FirebaseDatabase.getInstance("https://humor-app-7a94a-default-rtdb.firebaseio.com");
        user = LoginUtil.getLogin(this);
        //
        inflateToolbar();
        startItem();
        init();
    }

    private void inflateToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView txtTitleBar = findViewById(R.id.title_bar);
        txtTitleBar.setText(getResources().getString(R.string.title_add_feeling));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        //
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void startItem() {
        progressBar = findViewById(R.id.progressBar3);
        btnSave = findViewById(R.id.button_save);
        btnSave.setOnClickListener(v -> {
            save();
        });
    }

    private void init() {
        DatabaseReference ref = database.getReference("feeling");
        showProgress(true);
        //
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Feeling value = data.getValue(Feeling.class);
                    arrayOfFeeling.add(value);
                }
                showProgress(false);
                startAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showProgress(false);
                Toast.makeText(getApplicationContext(), "Erro listar os sentimentos.", Toast.LENGTH_LONG).show();
                Log.i(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void startAdapter() {
        ItemAddAdpater adapter = new ItemAddAdpater(this, arrayOfFeeling);
        GridView gridView = findViewById(R.id.list_add_feeling);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                feeling = arrayOfFeeling.get(position);
                v.setSelected(true);
            }
        });
    }

    private void save() {
        try {
            DatabaseReference ref = database.getReference("my-feeling");
            ref.child("userId").setValue(user.getId());
            ref.child("feelingId").setValue(feeling.getId());
            ref.child("date").setValue(new Date());
            Toast.makeText(this, "Sentimento Salvo.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, HomeActivity.class));
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao salvar seu sentimento.", Toast.LENGTH_LONG).show();
        }
    }

}