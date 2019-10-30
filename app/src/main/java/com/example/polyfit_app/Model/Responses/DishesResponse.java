package com.example.polyfit_app.Model.Responses;

import com.example.polyfit_app.Model.Dishes;

import java.util.ArrayList;

public class DishesResponse extends BaseResponse {
    private ArrayList<Dishes> Response;
    public DishesResponse(int status, String message) {
        super(status, message);
    }

    public ArrayList<Dishes> getResponse() {
        return Response;
    }

    public void setResponse(ArrayList<Dishes> response) {
        Response = response;
    }
}
