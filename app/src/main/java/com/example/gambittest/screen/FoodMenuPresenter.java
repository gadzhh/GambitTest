package com.example.gambittest.screen;

import com.example.gambittest.data.DataManager;
import com.example.gambittest.data.Menu;
import com.example.gambittest.data.RequestListener;

import java.util.ArrayList;

public class FoodMenuPresenter {

    private DataManager dataManager;
    private FoodMenuView foodMenuView;

    public FoodMenuPresenter(FoodMenuView view) {
        foodMenuView = view;
        initRequest();
    }

    private void initRequest() {
        dataManager = new DataManager();

        dataManager.getMenuItems(new RequestListener() {
            @Override
            public void onSuccess(ArrayList<Menu> menu) {
                foodMenuView.hideProgress();
                foodMenuView.showData(menu);
            }

            @Override
            public void onError(String error) {
                foodMenuView.hideProgress();
                foodMenuView.showError(error);
            }
        });
    }
}
