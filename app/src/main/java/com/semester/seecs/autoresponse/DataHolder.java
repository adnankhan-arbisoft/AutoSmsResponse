package com.semester.seecs.autoresponse;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DataHolder {

    private static final String PREFS_NAME = "my_prefs";

    private static final String PREFS_KEY_MODELS_LIST = "models_list";

    private SharedPreferences preferences;

    private static DataHolder instance;

    private Gson gson;

    private DataHolder (Context context){
        preferences = context.getSharedPreferences(PREFS_NAME, 0);
        gson = new Gson();
    }

   public static DataHolder getInstance(Context context) {
        if (instance == null) {
            instance = new DataHolder(context);
        }

        return instance;
    }

    public List<Model> getModels() {

        Type listType = new TypeToken<List<Model>>() {}.getType();

        String string = preferences.getString(PREFS_KEY_MODELS_LIST, "");

        List<Model> modelList = gson.fromJson(string, listType);

        return modelList != null ? modelList : new ArrayList<Model>();
    }

    public void saveModels(Set<Model> models) {

        String json = gson.toJson(models);

        preferences.edit().putString(PREFS_KEY_MODELS_LIST, json).apply();
    }


}
