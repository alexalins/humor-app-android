package com.example.humorapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Item {
    private User user;
    private Feeling feeling;
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateFormatada() {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String dateFormatada = formatador.format(date);
        return dateFormatada;
    }
}
