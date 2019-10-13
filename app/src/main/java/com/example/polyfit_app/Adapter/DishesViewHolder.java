package com.example.polyfit_app.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polyfit_app.R;

public class DishesViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView location;

    public DishesViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.titleDishes);
        location = itemView.findViewById(R.id.locationDishes);
    }
}