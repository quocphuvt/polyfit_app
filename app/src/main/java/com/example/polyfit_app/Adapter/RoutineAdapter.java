package com.example.polyfit_app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polyfit_app.Activity.ReminderActivity;
import com.example.polyfit_app.Model.Reminder;
import com.example.polyfit_app.Model.Routine;
import com.example.polyfit_app.R;
import com.example.polyfit_app.Service.local.PolyfitDatabase;
import com.suke.widget.SwitchButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeSet;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hades on 07,November,2019
 **/
public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ViewHolder> {
    List<Routine> listRoutine;
    Context context;

    public RoutineAdapter(List<Routine> listRoutine, Context context) {
        this.listRoutine = listRoutine;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.one_item_routine, parent, false);


        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RoutineAdapter.ViewHolder holder, final int position) {
//        removeDuplicate(listRoutine);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            holder.tvDateRoutine.setText(simpleDateFormat.format(simpleDateFormat.parse(listRoutine.get(position).getCreate_date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvCaloriesBurned.setText(listRoutine.get(position).getCaloriesConsumed());
        holder.tvStepCount.setText(String.valueOf(listRoutine.get(position).getStepCount()));
        holder.tvPracticeTime.setText(listRoutine.get(position).getTimePractice());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int posittion) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return listRoutine.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemClickListener itemClickListener;
        TextView tvDateRoutine, tvStepCount, tvCaloriesBurned, tvPracticeTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvDateRoutine = itemView.findViewById(R.id.tvDateRoutine);
            tvStepCount = itemView.findViewById(R.id.tvStepCount);
            tvCaloriesBurned = itemView.findViewById(R.id.tvCaloriesBurned);
            tvPracticeTime = itemView.findViewById(R.id.tvPracticeTime);

        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition());

        }
    }

    /*private List<Routine> removeDuplicate(List<Routine> listRoutine) {
        LinkedHashSet<Routine> hashSet = new LinkedHashSet<>(listRoutine);
        List<Routine> listWithoutDuplicates = new ArrayList<>(hashSet);
        return listWithoutDuplicates;
    }*/


}