package com.example.humorapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.humorapp.R;
import com.example.humorapp.util.LoginUtil;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // 2 segundo carregando
        Handler handler=new Handler();
        Runnable r = new Runnable() {
            public void run() {
                //se tiver usuario salvo vai pro home, se nao pra tela de login
                if(LoginUtil.isExist(SplashActivity.this)) {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }
        };
        handler.postDelayed(r, 2000);
    }
}