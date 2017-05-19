package com.example.hindahmed.anti_cancer;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hindahmed on 19/05/17.
 */

public class PrefManager {



    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setPref_Name(String pref_Name,String pref_Uid,String pref_email) {
        editor.putString("Name",pref_Name);
        editor.putString("Uid",pref_Uid);
        editor.putString("Email",pref_email);
        editor.commit();
    }
    public String getname (){
        return pref.getString("Name","");
    }
    public String getuid (){
        return pref.getString("Uid","");
    }
    public String getemail (){
        return pref.getString("Email","");
    }



}
