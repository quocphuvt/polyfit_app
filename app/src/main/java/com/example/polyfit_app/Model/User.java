package com.example.polyfit_app.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "polyfit_users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "passwork")
    private String password;
    @ColumnInfo(name = "display_name")
    private String display_name;
    @ColumnInfo(name = "weight")
    private Float weight;
    @ColumnInfo(name = "height")
    private Float height;
    @ColumnInfo(name = "bmi")
    private Float bmi;
    @ColumnInfo(name = "created_at")
    private String create_at;
    @ColumnInfo(name = "gender")
    private int gender;
    @ColumnInfo(name = "is_verified")
    private Boolean isVerified;
    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    public User(String username, String password, String display_name, Float weight, Float height, int gender, Boolean isVerified, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.display_name = display_name;
        this.weight = weight;
        this.height = height;
        this.bmi = weight / ( height * 2 );
        this.gender = gender;
        this.isVerified = isVerified;
        this.phoneNumber = phoneNumber;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
