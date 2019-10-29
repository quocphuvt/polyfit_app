package com.example.polyfit_app.Model.Responses;

import com.example.polyfit_app.Model.Exercise;

import java.util.ArrayList;

public class ExerciseResponse extends BaseResponse {
    private ArrayList<Exercise> Response;
    public ExerciseResponse(int status, String message) {
        super(status, message);
    }

    public ArrayList<Exercise> getResponse() {
        return Response;
    }

    public void setResponse(ArrayList<Exercise> response) {
        Response = response;
    }
}
