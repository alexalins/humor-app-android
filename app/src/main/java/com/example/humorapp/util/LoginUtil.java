package com.example.humorapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.humorapp.model.User;
import com.google.gson.Gson;

public abstract class LoginUtil {
    private static final String KEY = "LOGIN";
    static SharedPreferences sharedPref = null;

    private static void initSharedPref(Context context) {
        sharedPref = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    public static void saveLogin(String email, Context context) {
        User user = new User();
        user.setEmail(email);
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

    public static String getLogin(Context context) {
        initSharedPref(context);
        return sharedPref.getString(KEY, "");
    }

    public static boolean isExist(Context context) {
        if(getLogin(context) == null || getLogin(context).equals("")) {
            return false;
        }

        return true;
    }
}
