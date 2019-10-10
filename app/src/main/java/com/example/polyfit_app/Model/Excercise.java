package com.example.polyfit_app.Model;

public class Excercise {
    private int id;
    private String title;
    private String introduction;
    private String content;
    private String tips;
    private int sets;
    private int reps;
    private int rest;
    private String videoUrl;
    private String imageUrl;
    private int _idLevel;

    public Excercise(int id, String title, String introduction, String content, String tips, int sets, int reps, int rest, String videoUrl, String imageUrl, int _idLevel) {
        this.id = id;
        this.title = title;
        this.introduction = introduction;
        this.content = content;
        this.tips = tips;
        this.sets = sets;
        this.reps = reps;
        this.rest = rest;
        this.videoUrl = videoUrl;
        this.imageUrl = imageUrl;
        this._idLevel = _idLevel;
    }

    //This contructor use for loading excercise list
    public Excercise(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    //This contructor use for get data
    public Excercise(String title, String introduction, String content, String tips, int sets, int reps, int rest, String videoUrl, String imageUrl) {
        this.title = title;
        this.introduction = introduction;
        this.content = content;
        this.tips = tips;
        this.sets = sets;
        this.reps = reps;
        this.rest = rest;
        this.videoUrl = videoUrl;
        this.imageUrl = imageUrl;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int get_idLevel() {
        return _idLevel;
    }

    public void set_idLevel(int _idLevel) {
        this._idLevel = _idLevel;
    }
}
