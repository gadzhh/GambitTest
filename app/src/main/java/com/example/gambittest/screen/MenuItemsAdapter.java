package com.example.gambittest.screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gambittest.R;
import com.example.gambittest.data.Menu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.rambler.libs.swipe_layout.SwipeLayout;

public class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemsAdapter.MenuItemsHolder> {

    private ArrayList<Menu> items = new ArrayList<>();
    private OnButtonListener onButtonListener;
    private SwipeLayout swipeLayout;

    private int swipedItemPosition = -1;

    public MenuItemsAdapter(OnButtonListener listener) {
        onButtonListener = listener;
    }

    void setItems(ArrayList<Menu> items) {

        this.items.clear();
        this.items.addAll(items);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new MenuItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemsHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MenuItemsHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewDish;
        private TextView nameOfFood, priceOfFood, quantityOfFood;
        private Button buttonMinus, buttonPlus, buttonAddToCart, buttonHeart;

        private int quantity = 0;

        public MenuItemsHolder(@NonNull View itemView) {
            super(itemView);

            imageViewDish = itemView.findViewById(R.id.iv_dish);
            nameOfFood = itemView.findViewById(R.id.tv_dish_name);
            priceOfFood = itemView.findViewById(R.id.tv_dish_price);
            quantityOfFood = itemView.findViewById(R.id.tv_quantity);
            buttonMinus = itemView.findViewById(R.id.btn_minus);
            buttonPlus = itemView.findViewById(R.id.btn_plus);
            buttonAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
            buttonHeart = itemView.findViewById(R.id.btn_heart);
            swipeLayout = itemView.findViewById(R.id.swipe_layout);
        }

        private void changePrice(int number) {
            quantityOfFood.setText(String.valueOf(number));
        }

        void bind(Menu item) {

            swipeLayout.setOnSwipeListener(new SwipeLayout.OnSwipeListener() {
                @Override
                public void onBeginSwipe(SwipeLayout swipeLayout, boolean moveToRight) {
                    swipedItemPosition = getAdapterPosition();
                }

                @Override
                public void onSwipeClampReached(SwipeLayout swipeLayout, boolean moveToRight) {
                    onButtonListener.onFavoriteClicked(item.getId(), item.getFavorite());
                    item.setFavorite(!item.getFavorite());
                    swipeLayout.animateReset();
                    swipedItemPosition = -1;
                    notifyItemChanged(getAdapterPosition());
                }

                @Override
                public void onLeftStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {

                }

                @Override
                public void onRightStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {

                }
            });

            if (swipedItemPosition != getAdapterPosition()) {
                swipeLayout.reset();
            }

            Picasso
                    .get()
                    .load(item.getImage())
                    .into(imageViewDish);

            nameOfFood.setText(item.getName());
            priceOfFood.setText(itemView.getContext().getString(R.string.price_text, item.getPrice()));
            quantity = item.getQuantity();
            buttonHeart.setSelected(item.getFavorite());

            buttonAddToCart.setOnClickListener(view -> {
                quantity += 1;
                changePrice(quantity);
                item.setQuantity(quantity);

                buttonAddToCart.setVisibility(View.INVISIBLE);
                quantityOfFood.setVisibility(View.VISIBLE);
                buttonPlus.setVisibility(View.VISIBLE);
                buttonMinus.setVisibility(View.VISIBLE);

                onButtonListener.onCartClicked(item.getId(), false);
            });

            buttonMinus.setOnClickListener(view -> {
                quantity -= 1;
                changePrice(quantity);
                item.setQuantity(quantity);

                if (quantity == 0) {
                    quantityOfFood.setVisibility(View.INVISIBLE);
                    buttonPlus.setVisibility(View.INVISIBLE);
                    buttonMinus.setVisibility(View.INVISIBLE);
                    buttonAddToCart.setVisibility(View.VISIBLE);
                }

                onButtonListener.onCartClicked(item.getId(), true);
            });


            buttonPlus.setOnClickListener(view -> {
                quantity += 1;
                changePrice(quantity);
                item.setQuantity(quantity);

                onButtonListener.onCartClicked(item.getId(), false);
            });


            if (item.getQuantity() > 0) {
                buttonMinus.setVisibility(View.VISIBLE);
                buttonPlus.setVisibility(View.VISIBLE);
                quantityOfFood.setText(String.valueOf(item.getQuantity()));
                quantityOfFood.setVisibility(View.VISIBLE);
                buttonAddToCart.setVisibility(View.INVISIBLE);
            } else if (item.getQuantity() == 0) {
                buttonMinus.setVisibility(View.INVISIBLE);
                buttonPlus.setVisibility(View.INVISIBLE);
                quantityOfFood.setVisibility(View.INVISIBLE);
                buttonAddToCart.setVisibility(View.VISIBLE);
            }

            buttonHeart.setOnClickListener(view -> {
                onButtonListener.onFavoriteClicked(item.getId(), item.getFavorite());
                item.setFavorite(!item.getFavorite());
                swipeLayout.animateReset();
                swipedItemPosition = -1;
                notifyItemChanged(getAdapterPosition());
            });
        }
    }
}