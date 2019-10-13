package com.example.polyfit_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polyfit_app.Model.Dishes;
import com.example.polyfit_app.R;

import java.util.ArrayList;

public class DishesAdapter  extends RecyclerView.Adapter<DishesViewHolder> {
    private ArrayList<Dishes> dishesArrayList;
    private Context context;

    public DishesAdapter(ArrayList<Dishes> dishesArrayList, Context context) {
        this.dishesArrayList = dishesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DishesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item_dishes, parent, false);
        return new DishesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishesViewHolder holder, int position) {
        Dishes dishes = dishesArrayList.get(position);
        holder.title.setText(dishesArrayList.get(position).getTitle());
        holder.location.setText(dishesArrayList.get(position).getLocation());
        //TODO: holder.iv_image use for set image for lat
    }

    @Override
    public int getItemCount() {
        return dishesArrayList.size();
    }
}