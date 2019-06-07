package com.eleganzit.amigo.session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.eleganzit.amigo.LoginActivity;
import com.eleganzit.amigo.LoginSessionActivity;
import com.eleganzit.amigo.NewsFeedActivity;

import java.util.HashMap;

/**
 * Created by eleganz on 2/11/18.
 */

public class UserLoggedInSession {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;
    Activity activity;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Amigo";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)

    public static final String FNAME= "fullname";
    public static final String PASSWORD= "password";
    public static final String EMAIL= "email_id";
    public static final String USERNAME= "username";
    public static final String USER_ID = "user_id";
    public static final String PHOTO = "photo";



    public UserLoggedInSession(Context context){
        this._context = context;
        this.activity = (Activity) context;

        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession(String email,String username,String user_id,String fname,String password,String photo){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(USER_ID, user_id);
        editor.putString(PHOTO, photo);
        editor.putString(FNAME, fname);
        editor.putString(PASSWORD, password);
        editor.putString(EMAIL, email);
        editor.putString(USERNAME, username);



        // commit changes
        editor.commit();
        Intent i = new Intent(_context, NewsFeedActivity.class);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        _context.startActivity(i);
        activity.finish();
        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);




    }

    public void updateImage(String photo){

        editor.putString(PHOTO, photo);
        editor.commit();


    }

    public void updatePassword(String password){

        editor.putString(PASSWORD, password);
        editor.commit();


    }


    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }


    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(USER_ID, pref.getString(USER_ID, null));
        user.put(FNAME, pref.getString(FNAME, null));
        user.put(PASSWORD, pref.getString(PASSWORD, null));
        user.put(PHOTO, pref.getString(PHOTO, null));
        user.put(EMAIL, pref.getString(EMAIL, null));
        user.put(USERNAME, pref.getString(USERNAME, null));





        // return user
        return user;
    }



    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginSessionActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Staring Login Activity
        _context.startActivity(i);
        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }



}
