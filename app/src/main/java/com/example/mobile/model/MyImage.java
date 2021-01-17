package com.example.mobile.model;

import android.graphics.Bitmap;

import java.util.Date;

public class MyImage {
    private int id;
    public int idNotebook;

    private String image;
    private Date lastEdit;



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(Date lastEdit) {
        this.lastEdit = lastEdit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
