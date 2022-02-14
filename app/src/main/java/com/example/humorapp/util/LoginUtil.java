package com.example.humorapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.example.humorapp.model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

public abstract class LoginUtil {
    private static final String KEY = "LOGIN";
    static SharedPreferences sharedPref = null;

    private static void initSharedPref(Context context) {
        sharedPref = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    public static void saveLogin(FirebaseUser userFirebase, Context context) {
        User user = new User();
        user.setEmail(userFirebase.getEmail());
        user.setName(userFirebase.getDisplayName());
        user.setId(userFirebase.getUid());
        if(userFirebase.getPhotoUrl() != null) {
            user.setImage(userFirebase.getPhotoUrl().toString());
        }
        //
        String jsonUser = new Gson().toJson(user);
        //
        initSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY, jsonUser);
        editor.apply();
    }

    public static void deleteLogin(Context context) {
        initSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(KEY).apply();
    }

    public static User getLogin(Context context) {
        initSharedPref(context);
        String userJson = sharedPref.getString(KEY, "");
        if(!userJson.equals("")) {
            User user = new Gson().fromJson(userJson, User.class);
            return user;
        }
        return null;
    }

    public static boolean isExist(Context context) {
        if(getLogin(context) == null) {
            return false;
        }

        return true;
    }
}
