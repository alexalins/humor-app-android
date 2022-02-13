package com.example.humorapp.model;

import java.util.Date;

public class Feeling {
    private String name;
    private String image;
    private Date date;
    private User user;

    public Feeling() {
    }

    public Feeling(String name, String image, Date date) {
        this.name = name;
        this.image = image;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Feeling{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", date=" + date +
                '}';
    }
}
