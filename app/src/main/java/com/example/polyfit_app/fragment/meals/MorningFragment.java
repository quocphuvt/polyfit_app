package com.example.polyfit_app.fragment.meals;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.polyfit_app.activity.login.DishesDetails;
import com.example.polyfit_app.adapter.DishesAdapter;
import com.example.polyfit_app.interfaces.ItemClickListener;
import com.example.polyfit_app.model.Dishes;
import com.example.polyfit_app.model.Ingredient;
import com.example.polyfit_app.model.response.DishesResponse;
import com.example.polyfit_app.R;
import com.example.polyfit_app.service.remote.DishesAPI;
import com.example.polyfit_app.service.remote.RetrofitClient;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class MorningFragment extends Fragment implements ItemClickListener {
    private RecyclerView rv_dishes_morning;
    private BottomSheetLayout bottomSheetLayout;
    private ArrayList<Dishes> dishes;

    public MorningFragment() {
        // Required empty public constructor
    }

    public MorningFragment(ArrayList<Dishes> dishes) {
        this.dishes = dishes;
    }

    private void initView(View view) {
        rv_dishes_morning = view.findViewById(R.id.rv_dishes_morning);
        bottomSheetLayout = view.findViewById(R.id.bottom_sheet_morning_dishes_details);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_morning, container, false);
        initView(view);
        rv_dishes_morning.setHasFixedSize(true);
        rv_dishes_morning.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        setDishesData(dishes);
        return view;
    }

    private void setDishesData(ArrayList<Dishes> dishes) {
        DishesAdapter dishesAdapter = new DishesAdapter(dishes, getContext(), MorningFragment.this);
        rv_dishes_morning.setAdapter(dishesAdapter);
    }

    @Override
    public void onClickItem(int id) { //This's item's position
        Dishes dish = dishes.get(id);
        ArrayList<Ingredient> ingredients = dish.getIngredients();
        DishesDetails dishesDetails = new DishesDetails(getContext(), dish, ingredients, bottomSheetLayout);
        bottomSheetLayout.showWithSheetView(dishesDetails.getView());
    }

    @Override
    public void onClick(View view, int posittion) {

    }
}
