package com.example.polyfit_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.polyfit_app.Activity.ExerciseDetailsActivity;
import com.example.polyfit_app.Interface.ItemClickListener;
import com.example.polyfit_app.Model.Exercise;
import com.example.polyfit_app.R;

import java.util.ArrayList;

public class ExcercisesAdapter extends RecyclerView.Adapter<ExercisesViewHolder> {
    private ArrayList<Exercise> exercises;
    private Context context;
    private ItemClickListener itemClickListener;

    public ExcercisesAdapter(ArrayList<Exercise> exercises, Context context, ItemClickListener itemClickListener) {
        this.exercises = exercises;
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
        Exercise exercise = exercises.get(position);
        holder.tv_title.setText(exercise.getTitle());
        if(!exercise.getImage_url().equals("null")) {
            Glide.with(context).load(exercise.getImage_url()).centerCrop().into(holder.iv_image);
        } else {
            holder.tv_placeholder.setVisibility(View.VISIBLE);
        }

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
        return exercises.size();
    }
}
