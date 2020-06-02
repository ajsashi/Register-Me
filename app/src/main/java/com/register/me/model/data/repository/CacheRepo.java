package com.register.me.model.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Android on 7/13/2018.
 */

public class CacheRepo {

    private static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_FILE_NAME = "RegisterME_CacheRepository";

    public CacheRepo(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void storeData(String Key, String s) {
        editor.putString(Key, s);
        editor.apply();
        editor.commit();

    }

    public String getData(String Key) {
        return sharedPreferences.getString(Key, null);
    }

    public void storeBoolsData(String Key, boolean s) {
        editor.putBoolean(Key, s);
        editor.apply();
        editor.commit();
    }

    public Boolean getBoolsData(String Key) {
        return sharedPreferences.getBoolean(Key, false);
    }

    public void storeBulkData(HashMap<String, String> map) {
        Set entrySet = map.entrySet();
        Iterator it =entrySet.iterator();
        while (it.hasNext()){
            Map.Entry me = (Map.Entry)it.next();
            editor.putString(me.getKey().toString(),me.getValue().toString());
//            Log.d("####CACHE DATA####",me.getKey()+"-"+me.getValue());
        }
        editor.apply();
        editor.commit();
    }
}


