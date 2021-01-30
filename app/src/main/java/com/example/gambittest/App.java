package com.example.gambittest;

import android.app.Application;

import com.example.gambittest.data.DataManager;
import com.example.gambittest.data.MenuApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static Retrofit retrofit;
    private static DataManager dm;
    private static MenuApi api;

    public static MenuApi getJSONApi() {
        return api;
    }

    public static DataManager getDataManager() {
        return dm;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.gambit-app.ru/category/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dm = new DataManager();
        api = retrofit.create(MenuApi.class);
    }
}
