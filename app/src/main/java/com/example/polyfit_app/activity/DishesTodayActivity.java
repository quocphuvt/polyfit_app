package com.example.polyfit_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.polyfit_app.activity.login.DishesDetails;
import com.example.polyfit_app.adapter.DishesTodayAdapter;
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

public class DishesTodayActivity extends AppCompatActivity implements ItemClickListener {
    private Toolbar toolbar;
    private RecyclerView rv_dishes_today;
    private DishesAPI dishesAPI;
    private ArrayList<Dishes> dishes;
    private BottomSheetLayout bottom_dishes_today;

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        rv_dishes_today = findViewById(R.id.rv_dishes_today);
        bottom_dishes_today = findViewById(R.id.bottom_dishes_today);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes_today);
        initView();

        Retrofit retrofit = RetrofitClient.getInstance();
        dishesAPI = retrofit.create(DishesAPI.class);
        Intent i = getIntent();
        int mealId = i.getIntExtra("mealId", 0);
        String titleId = i.getStringExtra("title");
        String title = "";
        switch (titleId) {
            case "sang":
                title = "sáng";
                break;
            case "trua":
                title = "trưa";
                break;
            default:
                title = "chiều tối";
        }
        toolbar.setTitle("Thực đơn cho buổi " + title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setDishesData(titleId);
    }

    private void setDishesData(String title) {
        Call<DishesResponse> dishesResponseCall = dishesAPI.getAllDishesInMeal(title); //TODO: LOAD DYNAMIC ID
        dishesResponseCall.enqueue(new Callback<DishesResponse>() {
            @Override
            public void onResponse(Call<DishesResponse> call, Response<DishesResponse> response) {
                if (response.isSuccessful()) {
                    DishesResponse dishesResponse = response.body();
                    if (dishesResponse.getStatus() == 0) {
                        dishes = dishesResponse.getResponse();
                        DishesTodayAdapter dishesAdapter = new DishesTodayAdapter(dishesResponse.getResponse(), DishesTodayActivity.this, DishesTodayActivity.this);
                        rv_dishes_today.setHasFixedSize(true);
                        rv_dishes_today.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                        rv_dishes_today.setAdapter(dishesAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<DishesResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClickItem(int id) {
        Dishes dish = dishes.get(id);
        ArrayList<Ingredient> ingredients = dish.getIngredients();
        DishesDetails dishesDetails = new DishesDetails(this, dish, ingredients, bottom_dishes_today);
        bottom_dishes_today.showWithSheetView(dishesDetails.getView());
    }

    @Override
    public void onClick(View view, int posittion) {

    }
}
