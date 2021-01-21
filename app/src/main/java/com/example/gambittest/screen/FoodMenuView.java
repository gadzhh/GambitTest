package com.example.gambittest.screen;

import com.example.gambittest.data.Menu;

import java.util.ArrayList;

public interface FoodMenuView {

    void showProgress();

    void hideProgress();

    void showError(String error);

    void showData(ArrayList<Menu> menu);
}
