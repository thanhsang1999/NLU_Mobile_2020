package com.example.mobile.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Package implements Parcelable {
    int id;
    String color;
    String name;

    Date lastEdit;
    List<Notebook> notebooks;



    public Package(){

    }

    public static final Creator<Package> CREATOR = new Creator<Package>() {
        @Override
        public Package createFromParcel(Parcel in) {
            return new Package(in);
        }

        @Override
        public Package[] newArray(int size) {
            return new Package[size];
        }
    };

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


    public Date getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(Date lastEdit) {
        this.lastEdit = lastEdit;
    }

    public int getId() {
        return id;
    }

    public void setId(int i) {
        this.id=i;
    }

    public List<Notebook> getNotebooks() {
        return notebooks;
    }

    public void setNotebooks(List<Notebook> notebooks) {
        this.notebooks = notebooks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(color);
        dest.writeString(name);
        dest.writeArray(this.notebooks.toArray());
    }
    protected Package(Parcel in) {
        id = in.readInt();
        color = in.readString();
        name = in.readString();
        notebooks = new ArrayList<>();
        in.readList(notebooks,Notebook.class.getClassLoader());
    }
}
