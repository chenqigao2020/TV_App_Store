package com.app.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

    private Context context;

    private String name = "data";

    private SharedPreferences sharedPreferences;

    public SharedPreferencesUtils(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void put(String key, int value) {
        sharedPreferences
                .edit()
                .putInt(key, value)
                .apply();
    }

    public int get(String key) {
        return sharedPreferences
                .getInt(key, 0);
    }

    public void put(String key, String value) {
        sharedPreferences
                .edit()
                .putString(key, value)
                .apply();
    }

    public String get(String key, String defaultValue) {
        return sharedPreferences
                .getString(key, defaultValue);
    }
}
