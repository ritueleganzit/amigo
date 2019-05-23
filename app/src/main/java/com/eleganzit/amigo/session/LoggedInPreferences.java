package com.eleganzit.amigo.session;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoggedInPreferences {

    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;


    Activity activity;
    private static final String PREF_NAME = "AmigoLoggedUsers";


    public LoggedInPreferences(Context _context) {
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
        this._context = _context;
    }

    public void createSession(String user_key, JSONObject jsonObject)
    {

        // Storing name in pref

            editor.putString(user_key, ""+jsonObject.toString());



        // commit changes
        editor.commit();
    }


    public Map<String,?> getAll()
    {

       return pref.getAll();
    }





}
