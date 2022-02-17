package com.example.humorapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.humorapp.R;
import com.example.humorapp.util.LoginUtil;
import com.example.humorapp.validation.LoginValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnReset;
    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;
    //
    private FirebaseAuth mAuth;
    private String TAG = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //
        mAuth = FirebaseAuth.getInstance();
        init();
    }

    private void init() {
        inputEmail = findViewById(R.id.editTextUser);
        inputPassword = findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.progressBar2);
        showProgress(false);
        btnLogin = findViewById(R.id.button_login);
        btnLogin.setOnClickListener(v -> {
            login();
        });

        btnReset = findViewById(R.id.button_reset);
        btnReset.setOnClickListener(v -> {
            resetPassword();
        });
    }

    private void next() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void login() {
        if (LoginValidation.emailAndPasswordValidation(inputEmail.getText(), inputPassword.getText(), this)) {
            showProgress(true);
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.i(TAG, "signInWithCustomToken:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                LoginUtil.saveLogin(user, getApplicationContext());
                                showProgress(false);
                                next();
                            } else {
                                showProgress(false);
                                Log.i(TAG, "signInWithCustomToken:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Falha ao realizar o login", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void resetPassword() {
        if(LoginValidation.emailValidation(inputEmail.getText(), this)) {
            showProgress(true);
            mAuth.sendPasswordResetEmail(inputEmail.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            showProgress(false);
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Email enviado", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Email sent.");
                            } else {
                                Toast.makeText(LoginActivity.this, "Não foi possivel enviar o email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}