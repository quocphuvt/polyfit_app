package com.example.polyfit_app.Model.Responses;

import com.example.polyfit_app.Model.BodyParts;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.http.Body;

public class BodypartResponse extends BaseResponse {
    private ArrayList<BodyParts> Response;
    public BodypartResponse(int status, String message) {
        super(status, message);
    }

    public ArrayList<BodyParts> getResponse() {
        return Response;
    }

    public void setResponse(ArrayList<BodyParts> response) {
        Response = response;
    }
}
