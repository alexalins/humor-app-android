package com.example.humorapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
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
import com.example.humorapp.adapter.FeelingAdapter;
import com.example.humorapp.adapter.ItemAddAdpater;
import com.example.humorapp.model.Feeling;
import com.example.humorapp.model.User;
import com.example.humorapp.util.LoginUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

    private void updateToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        String token = task.getResult();
                        Log.d(TAG, "Pegou Token");
                        String uid = FirebaseAuth.getInstance().getUid();
                        
                    }
                });
    }

    private void inflateToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView txtTitleBar = findViewById(R.id.title_bar);
        txtTitleBar.setText(getResources().getString(R.string.title_add_feeling));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle("");
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
        FeelingAdapter adapter = new FeelingAdapter(this, arrayOfFeeling);
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
            //
            String dateStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
            String timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
            //
            ref.child(timeStamp).child("user").setValue(user);
            ref.child(timeStamp).child("feeling").setValue(feeling);
            ref.child(timeStamp).child("date").setValue(dateStamp);
            Toast.makeText(this, "Sentimento Salvo.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, HomeActivity.class));
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao salvar seu sentimento.", Toast.LENGTH_LONG).show();
        }
    }

}