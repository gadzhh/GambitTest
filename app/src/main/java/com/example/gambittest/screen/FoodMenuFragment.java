package com.example.gambittest.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gambittest.R;
import com.example.gambittest.data.Menu;
import com.example.gambittest.data.SharedPreferenceHelper;

import java.util.ArrayList;

public class FoodMenuFragment extends Fragment implements FoodMenuView, OnButtonListener {

    private ProgressBar progressBar;
    private MenuItemsAdapter menuItemsAdapter;
    private FoodMenuPresenter foodMenuPresenter;
    private SharedPreferenceHelper prefsHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initPresenter();
        initPrefs();
    }

    private void initViews(View view) {

        progressBar = view.findViewById(R.id.progress);

        RecyclerView foodList;

        foodList = view.findViewById(R.id.recycler_view_for_food);

        menuItemsAdapter = new MenuItemsAdapter(this);
        foodList.setAdapter(menuItemsAdapter);
    }

    private void initPresenter() {
        foodMenuPresenter = new FoodMenuPresenter(this);
    }

    private void initPrefs() {
        if (getActivity().getBaseContext() != null) {
            prefsHelper = new SharedPreferenceHelper(getActivity().getBaseContext());
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showData(ArrayList<Menu> menu) {
        menuItemsAdapter.setItems(menu);

        for (int i = 0; i < menu.size(); i++) {
            int index = prefsHelper.quantityInBasket(String.valueOf(menu.get(i).getId()));
            menu.get(i).setQuantity(index);
        }

        for (int i = 0; i < menu.size(); i++) {
            menu.get(i).setFavorite(prefsHelper.isFavorite(menu.get(i).getId()));
        }
    }


    @Override
    public void onFavoriteClicked(int productId, boolean isDelete) {
        if (isDelete) {
            prefsHelper.removeFromFavorites(productId);
        } else {
            prefsHelper.addToFavorites(productId);
        }
    }

    @Override
    public void onCartClicked(int productId, boolean isDelete) {
        if (isDelete) {
            prefsHelper.quantityRemove(String.valueOf(productId));
        } else {
            prefsHelper.quantityAdd(String.valueOf(productId));
        }
    }
}
