package com.example.polyfit_app.model;

import java.util.ArrayList;

public class Meal {
    private int id;
    private String title;
    private String image_url;
    private ArrayList<Dishes> Dishes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public ArrayList<Dishes> getDishes() {
        return Dishes;
    }

    public void setDishes(ArrayList<Dishes> Dishes) {
        this.Dishes = Dishes;
    }
}
