package com.example.mobile.model;

import java.util.Date;

public class MyImage {
    private int id;
    public int idNotebook;

    private byte[] image;
    private Date lastEdit;



    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
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
