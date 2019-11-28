package com.example.polyfit_app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.polyfit_app.models.User;
import com.google.gson.Gson;

public class Helpers {
    public static void putUserIntoPreferences(Context context, User user) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("user", json);
        editor.commit();
    }

    public static User getUserFromPreferences(Context context) {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String json  = sharedPreferences.getString("user", "");
        User user = gson.fromJson(json, User.class);
        return user;
    }
}
