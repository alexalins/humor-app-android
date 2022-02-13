package com.example.humorapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.humorapp.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.button_login);
        btnLogin.setOnClickListener(v -> {
            next();
        });
    }

    private void next() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}