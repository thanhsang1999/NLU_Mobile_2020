package com.example.mobile.model;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Notebook implements Parcelable {
    private int id;
    private String title;
    private String content;
    private Date dateEdit;
    private Date remind;
    private boolean checked;
    private String colorPackage;
    private int star;

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    List<String> images;
    public int id_package;

    public Notebook() {

        this.checked=false;
        this.images= new ArrayList<>();
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
        if(title==null) {
            this.title = "";
        }else
        this.title = title;
    }

    public String getContent() {


        return content;
    }

    public void setContent(String content) {
        if(content==null) {
            this.content = "";
        }else
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

    public Date getRemind() {
        return remind;
    }

    public void setRemind(Date remind) {

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
        dest.writeByte((byte) ((checked) ? 1: 0));
        dest.writeLong(dateEdit.getTime());
        dest.writeByte((byte) ((remind == null) ? 0 : 1));
        if(remind!=null)
        dest.writeLong(remind.getTime());
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

            remind=new Date(in.readLong());
        }
        colorPackage=in.readString();
        images= new ArrayList<>();




    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
