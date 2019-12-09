package com.example.polyfit_app.model.response;

import com.example.polyfit_app.model.Level;

import java.util.ArrayList;

public class LevelResponse extends BaseResponse {
    private ArrayList<Level> Response;
    private Level Object;
    public LevelResponse(int status, String message) {
        super(status, message);
    }

    public ArrayList<Level> getResponse() {
        return Response;
    }

    public void setResponse(ArrayList<Level> response) {
        Response = response;
    }

    public Level getObject() {
        return Object;
    }

    public void setObject(Level object) {
        Object = object;
    }
}
