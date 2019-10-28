package com.example.polyfit_app.Model;

import com.example.polyfit_app.Model.Excercise;

import java.util.ArrayList;

public class Level {
    private int id;
    private String title;
    private String image;
    private String description;
    private ArrayList<Excercise> excercises;
    private ArrayList<Diet> diets;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Excercise> getExcercises() {
        return excercises;
    }

    public void setExcercises(ArrayList<Excercise> excercises) {
        this.excercises = excercises;
    }

    public ArrayList<Diet> getDiets() {
        return diets;
    }

    public void setDiets(ArrayList<Diet> diets) {
        this.diets = diets;
    }
}
