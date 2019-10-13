package com.example.polyfit_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polyfit_app.Activity.ExerciseActivity;
import com.example.polyfit_app.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{
    private List<String> listString;

    public ContactAdapter(List<String> listString) {
        this.listString = listString;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_cardview, parent, false);
        return  new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        holder.vName.setText(listString.get(position));

    }

    @Override
    public int getItemCount() {
        return  listString.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName;

        public ContactViewHolder(View itemView) {
            super(itemView);
            vName = (TextView) itemView.findViewById(R.id.name_textview);

        }

    }


}
