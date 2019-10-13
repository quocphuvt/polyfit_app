package com.example.polyfit_app.Activity;

import android.content.Context;
import android.os.Bundle;

import com.example.polyfit_app.Adapter.ExcercisesAdapter;
import com.example.polyfit_app.Interface.ItemClickListener;
import com.example.polyfit_app.Model.Excercise;
import com.example.polyfit_app.Model.Ingredient;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.polyfit_app.R;

import java.util.ArrayList;

public class ExercisesActivity extends AppCompatActivity implements ItemClickListener {
    private ArrayList<Excercise> excercises;
    private RecyclerView rv_exercises;
    //TODO: Bottom Sheet test for DishesActivity but not for this. We'll move on later;
    private BottomSheetLayout sheet_dish;
    private ListView lv_ingredients;
    private ArrayList<Ingredient> ingredients;
    private View bottomSheetIngredientView;

    private void initView() {
        rv_exercises = findViewById(R.id.rv_exercises);
        sheet_dish = findViewById(R.id.bottom_sheet_dish_details);
        bottomSheetIngredientView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dish_details, sheet_dish, false);
        lv_ingredients = bottomSheetIngredientView.findViewById(R.id.lv_ingredients_dish_details);
    }

    private void setSampleExercises() {
        excercises = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            excercises.add(new Excercise("Fit Arms " + i, ""));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Arms");

        initView();
        setSampleExercises();
        rv_exercises.setHasFixedSize(true);
        rv_exercises.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        setDataForExcerciseList(excercises);
    }

    private void showDishBottomSheet() {
        sheet_dish.showWithSheetView(bottomSheetIngredientView);
        setSampleIngredientData();
    }

    private void setSampleIngredientData() {
        ingredients = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            ingredients.add(new Ingredient("Thit bo" + i, 1000, "100g"));
        }

        IngredientAdapter ingredientAdapter = new IngredientAdapter(ingredients, this);
        lv_ingredients.setAdapter(ingredientAdapter);
    }

    private void setDataForExcerciseList(ArrayList<Excercise> excercises) {
        ExcercisesAdapter excercisesAdapter = new ExcercisesAdapter(excercises, this, this);
        rv_exercises.setAdapter(excercisesAdapter);
    }

    @Override
    public void onClickItem(int id) {
        showDishBottomSheet();
    }
}

class IngredientAdapter extends BaseAdapter {
    private ArrayList<Ingredient> ingredients;
    private Context context;

    public IngredientAdapter(ArrayList<Ingredient> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.one_item_ingredient, viewGroup, false);
        Ingredient ingredient = ingredients.get(i);
        TextView tv_title = view.findViewById(R.id.tv_item_title_ingredient);
        TextView tv_unit = view.findViewById(R.id.tv_item_quantity_ingredient);
        tv_title.setText(ingredient.getTitle());
        tv_unit.setText(ingredient.getUnit());
        return view;
    }
}
