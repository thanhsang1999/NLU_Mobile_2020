package com.example.mobile.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Package implements Parcelable {
    int id;
    String color;
    String name;

    DateStringConverter lastEdit;
    List<Notebook> notebooks;


    protected Package(Parcel in) {
        id = in.readInt();
        color = in.readString();
        name = in.readString();
    }
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


    public DateStringConverter getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(DateStringConverter lastEdit) {
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
    }
}
