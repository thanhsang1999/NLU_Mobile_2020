package com.example.mobile.model;

import java.util.Date;

public class Package {
    
    String color;
    String name;

    Date dateCreate;
    Date lastEdit;
    public Package(String color, String name){
        this.color = color;
        this.name=name;

    }
    public Package(String color, String name, Date dateCreate, Date lastEdit) {
        this.color = color;
        this.name = name;

        this.dateCreate = dateCreate;
        this.lastEdit = lastEdit;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(Date lastEdit) {
        this.lastEdit = lastEdit;
    }
}
