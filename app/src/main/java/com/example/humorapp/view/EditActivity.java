package com.example.humorapp.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.humorapp.R;
import com.example.humorapp.model.User;
import com.example.humorapp.util.LoginUtil;
import com.example.humorapp.validation.LoginValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditActivity extends AppCompatActivity {
    User user = null;
    Button btnEdit, btnReset;
    TextView txtEmail;
    TextInputEditText txtUser;
    CircleImageView image;
    private FirebaseAuth mAuth;
    private String TAG = "LOGIN";
    public static final int PICK_IMAGE = 1;
    StorageReference storageRef;
    FirebaseStorage storage;
    Uri uriImageNew = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //
        storage = FirebaseStorage.getInstance("gs://humor-app-7a94a.appspot.com");
        storageRef = storage.getReference("images");
        //
        inflateToolbar();
        init();
    }

    private void inflateToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView txtTitleBar = findViewById(R.id.title_bar);
        txtTitleBar.setText(getResources().getString(R.string.title_edit_profile));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //
        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, ProfileActivity.class));
    }

    private void init() {
        user = LoginUtil.getLogin(this);
        txtEmail = findViewById(R.id.textEmail);
        txtEmail.setText(user.getEmail());
        txtUser = findViewById(R.id.editUser);
        txtUser.setText(user.getName());
        image = findViewById(R.id.profile_image_edit);
        Glide.with(this).load(Uri.parse(user.getImage())).into(image);
        //Picasso.get().load(Uri.parse(user.getImage())).placeholder(R.drawable.ic_icone_fofa_cor).into(image);
        btnEdit = findViewById(R.id.button_editar);
        btnReset = findViewById(R.id.button_reset2);
        btnReset.setOnClickListener(v -> {
            resetPassword();
        });
        image.setOnClickListener(v -> {
            getImage();
        });
        btnEdit.setOnClickListener(v -> {
            update();
        });
    }

    private void  update() {
        if(txtUser.getText() == null || txtUser.getText().equals("")) {
            Toast.makeText(EditActivity.this, "Nome não pode ser vazio", Toast.LENGTH_SHORT).show();
            return;
        }
        if(this.user.getImage() == null) {
            Toast.makeText(EditActivity.this, "Imagem não pode ser vazia", Toast.LENGTH_SHORT).show();
            return;
        }

       updateUser();
    }
    private void updateUser() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(txtUser.getText().toString())
                .setPhotoUri(Uri.parse(this.user.getImage()))
                .build();

        mUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditActivity.this, "Usuário atualizado", Toast.LENGTH_SHORT).show();
                            LoginUtil.saveLogin(mUser, getApplicationContext());
                            Intent intent = new Intent(EditActivity.this, ProfileActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(EditActivity.this, "Erro ao atualizar usuário", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveImage(Uri uriImage) {
        StorageReference fileRef = storageRef.child(uriImage.getLastPathSegment());
        //
        fileRef.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                user.setImage(taskSnapshot.getUploadSessionUri().toString());
                updateUser();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditActivity.this, "Erro ao atualizar a foto do usuário", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetPassword() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(user.getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditActivity.this, "Email enviado", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Email sent.");
                            LoginUtil.deleteLogin(getApplicationContext());
                            Intent intent = new Intent(EditActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(EditActivity.this, "Não foi possivel enviar o email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getImage() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            //Picasso.get().load(data.getData()).into(image);
            Glide.with(this).load(data.getData()).into(image);
            this.user.setImage(data.getData().toString());
        }
    }
}