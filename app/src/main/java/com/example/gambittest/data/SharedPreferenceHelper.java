package com.example.gambittest.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class SharedPreferenceHelper {

    private static final String PREFS_APP = "APP_PREF";
    private static final String PREFS_CART = "PREFS_CART";
    private static final String PREFS_FAVORITES = "PREFS_FAVORITES";

    private static SharedPreferences prefs;
    private Context context;

    public SharedPreferenceHelper(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREFS_APP, Context.MODE_PRIVATE);
    }

    public int quantityInBasket(String productId) {

        Gson gson = new Gson();
        HashMap<String, Integer> cart;

        String json = prefs.getString(PREFS_CART, "");
        Type type = new TypeToken<HashMap<String, Integer>>() {
        }.getType();
        cart = gson.fromJson(json, type);

        if (cart != null && !cart.isEmpty()) {
            for (HashMap.Entry<String, Integer> entry : cart.entrySet()) {
                String key = entry.getKey();
                if (key.equals(productId)) {
                    return entry.getValue();
                }
            }
        }
        return 0;
    }


    public void quantityAdd(String productId) {
        Gson gson = new Gson();
        HashMap<String, Integer> cart;

        String json = prefs.getString(PREFS_CART, "");
        Type type = new TypeToken<HashMap<String, Integer>>() {
        }.getType();
        cart = gson.fromJson(json, type);
        if (cart != null && !cart.isEmpty()) {

            boolean newFood = true;

            for (HashMap.Entry<String, Integer> entry : cart.entrySet()) {
                if (entry.getKey().equals(productId)) {
                    newFood = false;
                    entry.setValue(entry.getValue() + 1);
                }
            }

            if (newFood) {
                cart.put(productId, 1);
            }
        } else {
            cart = new HashMap<>();
            cart.put(productId, 1);
        }

        prefs.edit().putString(PREFS_CART, gson.toJson(cart)).apply();
    }

    public void quantityRemove(String productId) {

        Gson gson = new Gson();
        ConcurrentHashMap<String, Integer> cart;

        String json = prefs.getString(PREFS_CART, "");
        Type type = new TypeToken<ConcurrentHashMap<String, Integer>>() {
        }.getType();
        cart = gson.fromJson(json, type);

        for (ConcurrentHashMap.Entry<String, Integer> entry : cart.entrySet()) {
            if (entry.getKey().equals(productId)) {
                if (entry.getValue() > 1) {
                    entry.setValue(entry.getValue() - 1);
                } else {
                    cart.remove(entry.getKey());
                }
            }
        }

        prefs.edit().putString(PREFS_CART, gson.toJson(cart)).apply();
    }

    public HashMap<String, Integer> getQuantity() {
        Gson gson = new Gson();
        String json = prefs.getString(PREFS_CART, "");
        Type type = new TypeToken<HashMap<String, Integer>>() {
        }.getType();

        return gson.fromJson(json, type);
    }

    public boolean isFavorite(int productId) {

        ArrayList<Integer> cart;
        Gson gson = new Gson();

        String json = prefs.getString(PREFS_FAVORITES, "");
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        cart = gson.fromJson(json, type);

        if (cart != null && !cart.isEmpty()) {
            for (int i = 0; i < cart.size(); i++) {
                if (cart.get(i).equals(productId)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void addToFavorites(int productId) {

        ArrayList<Integer> cart;
        Gson gson = new Gson();

        String json = prefs.getString(PREFS_FAVORITES, "");
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();

        cart = gson.fromJson(json, type);

        if (cart == null) {
            cart = new ArrayList<>();
        }

        cart.add(productId);

        prefs.edit().putString(PREFS_FAVORITES, gson.toJson(cart)).apply();
    }

    public void removeFromFavorites(int productId) {

        ArrayList<Integer> cart;
        Gson gson = new Gson();

        String json = prefs.getString(PREFS_FAVORITES, "");
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        cart = gson.fromJson(json, type);

        if (cart != null) {
            for (int i = 0; i < cart.size(); i++) {
                if (cart.get(i) == productId) {
                    cart.remove(i);
                }
            }
        }

        prefs.edit().putString(PREFS_FAVORITES, gson.toJson(cart)).apply();
    }
}

