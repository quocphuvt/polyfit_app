package com.example.polyfit_app.Model;

public class Ingredient {
    private int id;
    private String title;
    private float price;
    private String unit;

    public Ingredient(int id, String title, float price, String unit) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.unit = unit;
    }

    public Ingredient(String title, float price, String unit) {
        this.title = title;
        this.price = price;
        this.unit = unit;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
