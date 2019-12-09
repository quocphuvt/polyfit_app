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
public class NoonFragment extends Fragment implements ItemClickListener {
    private RecyclerView rv_dishes_noon;
    private DishesAPI dishesAPI;
    private BottomSheetLayout bottomSheetLayout;
    private ArrayList<Dishes> dishes;

    public NoonFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        rv_dishes_noon = view.findViewById(R.id.rv_dishes_noon);
        bottomSheetLayout = view.findViewById(R.id.bottom_sheet_noon_dishes_details);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Retrofit retrofit = RetrofitClient.getInstance();
        dishesAPI = retrofit.create(DishesAPI.class);
        View view =inflater.inflate(R.layout.fragment_noon, container, false);
        initView(view);
        rv_dishes_noon.setHasFixedSize(true);
        rv_dishes_noon.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        // Inflate the layout for this fragment
        setDishesData();
        return view;
    }

    private void setDishesData() {
        Call<DishesResponse> dishesResponseCall = dishesAPI.getDishesByMeal(171); //TODO: LOAD DYNAMIC ID
        dishesResponseCall.enqueue(new Callback<DishesResponse>() {
            @Override
            public void onResponse(Call<DishesResponse> call, Response<DishesResponse> response) {
                if(response.isSuccessful()) {
                    DishesResponse dishesResponse = response.body();
                    if(dishesResponse.getStatus() == 0) {
                        dishes = dishesResponse.getResponse();
                        DishesAdapter dishesAdapter = new DishesAdapter(dishesResponse.getResponse(), getContext(), NoonFragment.this);
                        rv_dishes_noon.setAdapter(dishesAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<DishesResponse> call, Throwable t) {

            }
        });
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
