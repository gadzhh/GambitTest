package com.example.gambittest.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menu {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("price")
    @Expose
    private int price;

    @SerializedName("description")
    @Expose
    private String description;

    private int quantity;

    private boolean isFavorite;


    public boolean getFavorite() {
        return isFavorite;
    }


    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }


    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getImage() {
        return image;
    }


    public int getPrice() {
        return price;
    }


    public String getDescription() {
        return description;
    }
}
