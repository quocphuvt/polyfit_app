package com.example.polyfit_app.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polyfit_app.R;

public class DishesViewHolder extends RecyclerView.ViewHolder {
    public TextView title_dishes, description_dishes;
    public ImageView iv_bg_dishes;
//    public TextView  ;

    public DishesViewHolder(@NonNull View itemView) {
        super(itemView);
        title_dishes = itemView.findViewById(R.id.tv_title_item_dishes);
        iv_bg_dishes = itemView.findViewById(R.id.iv_bg_item_dishes);
        description_dishes = itemView.findViewById(R.id.tv_description_item_dishes);
//        location = itemView.findViewById(R.id.locationDishes);
    }
}