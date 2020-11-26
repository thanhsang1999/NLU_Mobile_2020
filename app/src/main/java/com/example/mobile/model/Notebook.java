package com.example.mobile.model;


import java.util.Date;

public class Notebook {
    private int id;
    private String title;
    private String content;
    private int idPackage;
    private Date dateCreate;
    private Date dateEdit;

    public Notebook(int id, String title, String content, int idPackage, Date dateCreate, Date dateEdit) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.idPackage = idPackage;
        this.dateCreate = dateCreate;
        this.dateEdit = dateEdit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIdPackage() {
        return idPackage;
    }

    public void setIdPackage(int idPackage) {
        this.idPackage = idPackage;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateEdit() {
        return dateEdit;
    }

    public void setDateEdit(Date dateEdit) {
        this.dateEdit = dateEdit;
    }
}
