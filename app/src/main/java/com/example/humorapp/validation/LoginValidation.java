package com.example.humorapp.validation;

import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

public abstract class LoginValidation {

    public static boolean emailAndPasswordValidation(Editable email, Editable password, Context context) {
        if(email == null || email.toString().equals("")) {
            Toast.makeText(context, "Por favor, insira um email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(password == null || password.toString().equals("")) {
            Log.i("LOGIN", "password");
            Toast.makeText(context, "Por favor, insira uma senha", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!email.toString().contains("@")) {
            Toast.makeText(context, "Por favor, insira email valido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public static boolean emailValidation(Editable email, Context context) {
        if(email == null || email.toString().equals("")) {
            Toast.makeText(context, "Por favor, insira um email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!email.toString().contains("@")) {
            Toast.makeText(context, "Por favor, insira email valido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
