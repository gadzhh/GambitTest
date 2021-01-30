package com.example.gambittest.data;


import com.example.gambittest.App;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataManager {

    public void getMenuItems(RequestListener requestListener) {

        App.getJSONApi().getData("1")
                .enqueue(new Callback<List<Menu>>() {
                    @Override
                    public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                        if (response.body() != null) {
                            requestListener.onSuccess((ArrayList<Menu>) response.body());
                        } else {
                            requestListener.onError("Data is empty");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Menu>> call, Throwable tth) {
                        requestListener.onError("" + tth.getLocalizedMessage());
                    }
                });
    }
}
