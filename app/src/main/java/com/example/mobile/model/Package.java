package com.example.mobile.model;

import java.util.Date;

public class Package {
    int id;
    String color;
    String name;
    DateStringConverter dateCreate;
    DateStringConverter lastEdit;

    public Package(String color, String name, Date dateCreate, Date lastEdit) {
        this.color = color;
        this.name = name;


        this.dateCreate = new DateStringConverter(dateCreate);
        this.lastEdit = new DateStringConverter(lastEdit);
    }
    public Package(int id,String color, String name, String dateCreate, String lastEdit) {
        this.color = color;
        this.name = name;
        this.id=id;

        this.dateCreate = new DateStringConverter(dateCreate);
        this.lastEdit = new DateStringConverter(lastEdit);
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


    public DateStringConverter getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(DateStringConverter dateCreate) {
        this.dateCreate = dateCreate;
    }

    public DateStringConverter getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(DateStringConverter lastEdit) {
        this.lastEdit = lastEdit;
    }
}
