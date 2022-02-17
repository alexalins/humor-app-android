package com.example.humorapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.humorapp.R;
import com.example.humorapp.adapter.FeelingAdapter;
import com.example.humorapp.adapter.ItemAddAdpater;
import com.example.humorapp.model.Feeling;
import com.example.humorapp.model.Item;
import com.example.humorapp.model.User;
import com.example.humorapp.util.LoginUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class ProfileActivity extends AppCompatActivity {
    private ArrayList<Item> arrayOfItem;
    private ImageView image;
    private TextView txtName, txtEmail;
    private ProgressBar progressBar;
    private User user = null;
    private static final String TAG = "HUMOR";
    FirebaseDatabase database;
    DatabaseReference ref;
    private ItemAddAdpater adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //
        database = FirebaseDatabase.getInstance("https://humor-app-7a94a-default-rtdb.firebaseio.com");
        ref = database.getReference("my-feeling");
        //
        inflateToolbar();
        init();
        initList();
    }

    private void inflateToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView txtTitleBar = findViewById(R.id.title_bar);
        txtTitleBar.setText(getResources().getString(R.string.title_profile));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        //
        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
    }
    private void initList() {
        this.arrayOfItem  = new ArrayList<>();
        showProgress(true);
        //
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Item value = data.getValue(Item.class);
                    if(value.getUser().getId().equals(user.getId())) {
                        value.setId(data.getKey());
                        arrayOfItem.add(value);
                    }
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
            }
        });
    }
    private void startAdapter() {
        adapter = new ItemAddAdpater(this, arrayOfItem);
        listView = findViewById(R.id.list_my_feeling);
        listView.setAdapter(adapter);
        remove();
    }

    private void remove() {
        final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new ListViewAdapter(listView),
                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListViewAdapter view, int position) {
                                Item itemDelete = arrayOfItem.get(position);
                                try {
                                    ref.child(itemDelete.getId()).removeValue();
                                    arrayOfItem = new ArrayList<>();
                                    Toast.makeText(ProfileActivity.this, "Sentimento deletado.", Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(ProfileActivity.this, "Erro ao deletar seu sentimento.", Toast.LENGTH_LONG).show();
                                }

                            }
                        });

        listView.setOnTouchListener(touchListener);
        listView.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (touchListener.existPendingDismisses()) {
                    touchListener.undoPendingDismiss();
                }
            }
        });
    }

    private void init() {
        txtName = findViewById(R.id.textView3);
        txtEmail = findViewById(R.id.textView4);
        image = findViewById(R.id.profile_image);
        progressBar = findViewById(R.id.progressBar4);
        //
        user = LoginUtil.getLogin(this);
        txtName.setText(user.getName());
        txtEmail.setText(user.getEmail());
        Glide.with(this).load(Uri.parse(user.getImage())).into(image);
        //
        Button btnEdit = findViewById(R.id.button2);
        btnEdit.setOnClickListener(v -> {
            next();
        });

        Button btnExit = findViewById(R.id.button3);
        btnExit.setOnClickListener(v -> {
            exitAccount();
        });
    }

    private void next() {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    private void exitAccount() {
        LoginUtil.deleteLogin(getApplicationContext());
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}