package com.example.humorapp.model;

public class Feeling {
    private Long id;
    private String name;
    private String image;

    public Feeling() {
    }

    public Feeling(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Feeling{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
