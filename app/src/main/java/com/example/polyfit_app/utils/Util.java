package com.example.polyfit_app.utils;

public class Util {
    public static Float calculateBMI(Float weight, Float height) {
        return (weight / height * 2) * 100;
    }

    public static Integer getLevelId(Float BMI) {
        if(BMI < 18.5) {
            return 171; //Tang can
        } else if(BMI >= 18.5 && BMI <= 24.9) {
            return 41;
        } else {
            return 181;
        }
    }
}
