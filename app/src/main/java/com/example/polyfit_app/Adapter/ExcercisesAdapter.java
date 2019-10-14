package com.example.polyfit_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polyfit_app.Activity.ExerciseDetailsActivity;
import com.example.polyfit_app.Interface.ItemClickListener;
import com.example.polyfit_app.Model.Excercise;
import com.example.polyfit_app.R;

import java.util.ArrayList;

public class ExcercisesAdapter extends RecyclerView.Adapter<ExercisesViewHolder> {
    private ArrayList<Excercise> excercises;
    private Context context;
    private ItemClickListener itemClickListener;

    public ExcercisesAdapter(ArrayList<Excercise> excercises, Context context, ItemClickListener itemClickListener) {
        this.excercises = excercises;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ExercisesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item_exercise, parent, false);
        return new ExercisesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesViewHolder holder, int position) {
        Excercise excercise = excercises.get(position);
        holder.tv_title.setText(excercise.getTitle());
        //TODO: holder.iv_image use for set image for lat
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                itemClickListener.onClickItem(1); //TODO: PASS ITEMID FOR CONTEXT
                context.startActivity(new Intent(context, ExerciseDetailsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return excercises.size();
    }
}
