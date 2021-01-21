package com.example.gambittest.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MenuApi {

    @GET("39")
    Call<List<Menu>> getData(
            @Query("page") String page
    );
}
