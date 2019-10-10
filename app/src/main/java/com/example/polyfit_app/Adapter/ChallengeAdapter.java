package com.example.polyfit_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polyfit_app.Model.Challenge;
import com.example.polyfit_app.R;

import java.util.ArrayList;

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeViewHolder> {
    private ArrayList<Challenge> challenges;
    private Context context;

    public ChallengeAdapter(ArrayList<Challenge> challenges, Context context) {
        this.challenges = challenges;
        this.context = context;
    }

    @NonNull
    @Override
    public ChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item_challenge, parent, false);
        return new ChallengeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeViewHolder holder, int position) {
        Challenge challenge = challenges.get(position);
        holder.iv_challenge.setImageResource(R.drawable.ic_abs);
        holder.tv_title.setText(challenge.getTitle());
        holder.tv_content.setText(challenge.getContent());
    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }
}

class ChallengeViewHolder extends RecyclerView.ViewHolder {
    protected ImageView iv_challenge;
    protected TextView tv_title, tv_content;
    public ChallengeViewHolder(@NonNull View itemView) {
        super(itemView);
        iv_challenge = itemView.findViewById(R.id.iv_item_challenge);
        tv_title = itemView.findViewById(R.id.tv_item_challenge_title);
        tv_content = itemView.findViewById(R.id.tv_item_challenge_content);
    }
}
