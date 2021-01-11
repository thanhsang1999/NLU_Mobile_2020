package com.example.mobile.model;


import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Notebook {
    private int id;
    private String title;
    private String content;

    private Date dateEdit;
    private Calendar remind;
    private Boolean checked;

    public Notebook() {
        this.checked=false;
    }

    public Notebook(int id, String title, String content, Date dateEdit, Boolean checked) {
        this.id = id;
        this.title = title;
        this.content = content;

        this.dateEdit = dateEdit;
        this.checked = checked;
    }
    public Notebook(int id, String title, String content, int idPackage, Date dateEdit) {
        this.id = id;
        this.title = title;
        this.content = content;

        this.dateEdit = dateEdit;
        this.checked = false;
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



    public Date getDateEdit() {
        return dateEdit;
    }

    public void setDateEdit(Date dateEdit) {
        this.dateEdit = dateEdit;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Calendar getRemind() {
        return remind;
    }

    public void setRemind(Calendar remind) {

        this.remind = remind;
    }
}
