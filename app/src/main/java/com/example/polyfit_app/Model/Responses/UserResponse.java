package com.example.polyfit_app.Model.Responses;

import com.example.polyfit_app.Model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserResponse extends BaseResponse {
    private User user;
    private ArrayList<User> users;
    private String Response;

    public UserResponse(int status, String message) {
        super(status, message);
    }

    public UserResponse(int status, String message, User user, ArrayList<User> users) {
        super(status, message);
        this.user = user;
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
