package com.example.gambittest.screen;

public interface OnButtonListener {

    void onFavoriteClicked(int productId, boolean isDelete);
    void onCartClicked(int productId, boolean isDelete);
}
