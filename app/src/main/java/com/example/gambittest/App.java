package com.example.gambittest;

import com.example.gambittest.data.MenuApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App {

    private static final String BASE_URL = "https://api.gambit-app.ru/category/";
    private static App networkFoodApi;
    private Retrofit retrofit;

    private App() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static App getNetworkFoodApi() {
        if (networkFoodApi == null) {
            networkFoodApi = new App();
        }
        return networkFoodApi;
    }

    public MenuApi getJSONApi() {
        return retrofit.create(MenuApi.class);
    }
}
