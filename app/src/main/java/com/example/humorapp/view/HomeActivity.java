package com.example.humorapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.humorapp.R;
import com.example.humorapp.adapter.FeelingAdapter;
import com.example.humorapp.adapter.ItemAddAdpater;
import com.example.humorapp.model.Feeling;
import com.example.humorapp.model.Item;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HomeActivity extends AppCompatActivity {

    private BottomAppBar appbar;
    private FloatingActionButton btnAdd;
    private ProgressBar progressBar;
    private ArrayList<Item> arrayOfItem;
    private static final String TAG = "HUMOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        arrayOfItem = new ArrayList<>();
        //
        inflateToolbar();
        startItem();
        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Click novamente para sair", Toast.LENGTH_LONG).show();
    }

    private void inflateToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView txtTitleBar = findViewById(R.id.title_bar);
        txtTitleBar.setText(getResources().getString(R.string.title_home));
        setSupportActionBar(toolbar);
    }

    private void init() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://humor-app-7a94a-default-rtdb.firebaseio.com");
        DatabaseReference ref = database.getReference("my-feeling");
        showProgress(true);
        //
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Item value = data.getValue(Item.class);
                    arrayOfItem.add(value);
                }

                //trocando a posicao para os novos ficaram como primeiros
                Collections.reverse(arrayOfItem);
                showProgress(false);
                startAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showProgress(false);
                startAdapter();
                Toast.makeText(getApplicationContext(), "Erro listar os sentimentos.", Toast.LENGTH_LONG).show();
                Log.i(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void startItem() {
        appbar = findViewById(R.id.bottomAppBar);
        btnAdd = findViewById(R.id.btn_add);
        progressBar = findViewById(R.id.progressBar);
        //
        btnAdd.setOnClickListener(v -> {
            addFelling();
        });
        appbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.perfil) {
                    myProfile();
                    return true;
                }
                return false;
            }
        });
    }

    private void startAdapter() {
        ItemAddAdpater adapter = new ItemAddAdpater(this, arrayOfItem);
        ListView listView = (ListView) findViewById(R.id.list_feeling);
        listView.setAdapter(adapter);
    }

    private void addFelling() {
        Intent intent = new Intent(this, AddFeelingActivity.class);
        startActivity(intent);
    }

    private void myProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}
