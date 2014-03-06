package com.app.MoodApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by PsichO on 17.02.14.
 */
public class Account {
    public String access_token;
    public long user_id;

    public void save(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("access_token", access_token);
        editor.putLong("user_id", user_id);
        editor.commit();
    }

    public void restore(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        access_token = preferences.getString("access_token",null);
        user_id = preferences.getLong("user_id", 0);
    }
}
