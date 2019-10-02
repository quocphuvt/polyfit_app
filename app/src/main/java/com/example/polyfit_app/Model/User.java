package com.example.polyfit_app.Model;

public class User {
    private int id;
    private String username;
    private String password;
    private String display_name;
    private Float weight;
    private Float height;
    private Float bmi;

    public User(String username, String password, String display_name) {
        this.username = username;
        this.password = password;
        this.display_name = display_name;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public User(int id, String username, String password, String display_name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.display_name = display_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(int id, String username, String password, String display_name, Float weight, Float height, Float bmi) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.display_name = display_name;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getBmi() {
        return bmi;
    }

    public void setBmi(Float bmi) {
        this.bmi = bmi;
    }
}
