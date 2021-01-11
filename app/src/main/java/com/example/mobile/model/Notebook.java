package com.example.mobile.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Notebook implements Parcelable {
    private int id;
    private String title;
    private String content;

    private Date dateEdit;
    private Calendar remind;
    private boolean checked;
    private String colorPackage;

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



    public static final Creator<Notebook> CREATOR = new Creator<Notebook>() {
        @Override
        public Notebook createFromParcel(Parcel in) {
            return new Notebook(in);
        }

        @Override
        public Notebook[] newArray(int size) {
            return new Notebook[size];
        }
    };

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

    public String getColorPackage() {
        return colorPackage;
    }

    public void setColorPackage(String colorPackage) {
        this.colorPackage = colorPackage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeLong(dateEdit.getTime());
        dest.writeByte((byte) ((checked) ? 1: 0));
        dest.writeByte((byte) ((remind == null) ? 0 : 1));
        if(remind!=null)
        dest.writeLong(remind.getTimeInMillis());
        dest.writeString(colorPackage);


    }
    protected Notebook(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        checked= in.readByte()==1;
        dateEdit=new Date(in.readLong());
        byte checkCalendar = in.readByte();
        if(checkCalendar==(byte)1){
            Calendar c=Calendar.getInstance();
            c.setTimeInMillis(in.readLong());
            remind=c;
        }
        colorPackage=in.readString();



    }
}
