package com.example.polyfit_app.Model;

public class Quote {
    private int id;
    private String imageUrl;
    private String title;
    private String author;

    public Quote(int id, String imageUrl, String title, String author) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
    }

    public Quote(String imageUrl, String title, String author) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
