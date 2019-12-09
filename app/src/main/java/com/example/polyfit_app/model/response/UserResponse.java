package com.example.polyfit_app.model.response;

import com.example.polyfit_app.model.User;

public class UserResponse extends BaseResponse {
    private User Object;
//    private ArrayList<User> Response;

    public UserResponse(int status, String message) {
        super(status, message);
    }

    public User getObject() {
        return Object;
    }

    public void setObject(User object) {
        Object = object;
    }

//    public ArrayList<User> getResponse() {
//        return Response;
//    }
//
//    public void setResponse(ArrayList<User> response) {
//        Response = response;
//    }
}
