package com.example.humorapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Item {
    private String id;
    private User user;
    private Feeling feeling;
    private String date;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Feeling getFeeling() {
        return feeling;
    }

    public void setFeeling(Feeling feeling) {
        this.feeling = feeling;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", user=" + user +
                ", feeling=" + feeling +
                ", date='" + date + '\'' +
                '}';
    }
}
