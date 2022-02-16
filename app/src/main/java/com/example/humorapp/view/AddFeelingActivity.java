package com.example.humorapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.humorapp.R;
import com.example.humorapp.adapter.FeelingAdapter;
import com.example.humorapp.adapter.ItemAddAdpater;
import com.example.humorapp.model.Feeling;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddFeelingActivity extends AppCompatActivity {
    ArrayList<Feeling> arrayOfFeeling = new ArrayList<>();
    ProgressBar progressBar;
    private static final String TAG = "HUMOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feeling);
        progressBar = findViewById(R.id.progressBar3);
        progressBar = findViewById(R.id.progressBar3);
        //
        inflateToolbar();
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

    private void init() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://humor-app-7a94a-default-rtdb.firebaseio.com");
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
                v.setSelected(true);
            }
        });
    }

    private void resetSelect(View v) {
        v.setBackground(v.getResources().getDrawable(R.drawable.rounded_corner_add_item));
    }
}