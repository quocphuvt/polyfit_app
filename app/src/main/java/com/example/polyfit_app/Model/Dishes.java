package com.example.polyfit_app.Model;

public class Dishes {
    private int id;
    private String title;
    private String imageUrl;
    private float protein;
    private float fat;
    private float carb;
    private float calories;
    private int _idMeals;

    public Dishes(int id, String title, String imageUrl, float protein, float fat, float carb, float calories, int _idMeals) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.protein = protein;
        this.fat = fat;
        this.carb = carb;
        this.calories = calories;
        this._idMeals = _idMeals;
    }

    public Dishes(String title, String imageUrl, float protein, float fat, float carb, float calories, int _idMeals) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.protein = protein;
        this.fat = fat;
        this.carb = carb;
        this.calories = calories;
        this._idMeals = _idMeals;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getCarb() {
        return carb;
    }

    public void setCarb(float carb) {
        this.carb = carb;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public int get_idMeals() {
        return _idMeals;
    }

    public void set_idMeals(int _idMeals) {
        this._idMeals = _idMeals;
    }
}
