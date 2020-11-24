package com.example.mobile.model;

import java.util.Date;

public class Package {
    
    int level;
    String name;
    String content;
    Date dateCreate;
    Date lastEdit;
    public Package(int level, String name){
        this.level = level;
        this.name=name;

    }
    public Package(int level, String name, String content, Date dateCreate, Date lastEdit) {
        this.level = level;
        this.name = name;
        this.content = content;
        this.dateCreate = dateCreate;
        this.lastEdit = lastEdit;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
