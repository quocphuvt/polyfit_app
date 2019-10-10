package com.example.polyfit_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polyfit_app.Model.Excercise;
import com.example.polyfit_app.R;

import java.util.ArrayList;
import java.util.List;

public class ExcerciseAdapter extends RecyclerView.Adapter<ExerciseViewHolder> {
    private ArrayList<Excercise> excercises;
    private Context context;

    public ExcerciseAdapter(ArrayList<Excercise> excercises, Context context) {
        this.excercises = excercises;
        this.context = context;
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
        //TODO: holder.iv_image use for set image;
    }

    @Override
    public int getItemCount() {
        return excercises.size();
    }
}

class ExerciseViewHolder extends RecyclerView.ViewHolder {
    protected TextView tv_title;
    protected ImageView iv_image;

    public ExerciseViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_title = itemView.findViewById(R.id.tv_item_title_ex);
        iv_image = itemView.findViewById(R.id.iv_item_image_ex);
    }
}
