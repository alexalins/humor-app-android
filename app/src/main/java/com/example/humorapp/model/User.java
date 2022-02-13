package com.example.humorapp.model;

import java.util.List;

public class User {
    private String name;
    private String email;
    private String password;
    private String image;
    private List<Feeling> feelingList;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Feeling> getFeelingList() {
        return feelingList;
    }

    public void setFeelingList(List<Feeling> feelingList) {
        this.feelingList = feelingList;
    }
}
