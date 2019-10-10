package com.example.polyfit_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.polyfit_app.Adapter.ContactAdapter;
import com.example.polyfit_app.R;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {
    List<String> listName;
    RecyclerView cardList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        cardList=findViewById(R.id.cardList);
        listName=new ArrayList<>();
        listName.add("sup1");
        listName.add("sup2");
        listName.add("sup3");
        @SuppressLint("CutPasteId") RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ContactAdapter contactAdapter=new ContactAdapter(listName);
        cardList.setAdapter(contactAdapter);

    }


}
