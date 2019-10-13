package com.example.polyfit_app.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polyfit_app.R;

public class ExerciseViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_title;
    public ImageView iv_image;

    public ExerciseViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_title = itemView.findViewById(R.id.tv_item_title_ex);
        iv_image = itemView.findViewById(R.id.iv_item_image_ex);
    }
}
