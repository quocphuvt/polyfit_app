package com.example.polyfit_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polyfit_app.Interface.ItemClickListener;
import com.example.polyfit_app.Model.Excercise;
import com.example.polyfit_app.R;

import java.util.ArrayList;
import java.util.List;

public class ExcerciseAdapter extends RecyclerView.Adapter<ExerciseViewHolder> {
    private ArrayList<Excercise> excercises;
    private Context context;
    private ItemClickListener itemClickListener;

    public ExcerciseAdapter(ArrayList<Excercise> excercises, Context context, ItemClickListener itemClickListener) {
        this.excercises = excercises;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Excercise excercise = excercises.get(position);
        holder.tv_title.setText(excercise.getTitle());
        //TODO: holder.iv_image use for set image for lat
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onClickItem(1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return excercises.size();
    }
}
