package com.example.mobile.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Date;

public class Account implements Parcelable {
    private int id;
    private String username;
    private String fullname;
    private String email;
    private String password;
    private Date dateOfBirth;
    private String gender;
    private String outside;
    private String idOutSide;

    public Account(int id,String username, String fullname, String email, String password ) {
        this.id= id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;

    }
    public Account(int id ) {
        this.id= id;


    }



    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        if(dateOfBirth==null)return;
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {

        return gender;
    }

    public void setGender(String gender) {
        if(gender==null) return;
        gender = gender.toLowerCase();
        if(!gender.equals(GENDER_FEMALE)&&!gender.equals(GENDER_MALE)){
            Log.e("GENDER","SET ERROR");
            return;
        }

        this.gender = gender;
    }

    public String getOutside() {
        return outside;
    }

    public void setOutside(String outside) {
        if(outside==null)return;
        outside = outside.toLowerCase();
        if(!outside.equals(OUTSIDE_FACEBOOK)){
            Log.e("OUTSIDE","SET ERROR");
            return;
        }
        this.outside = outside;
    }

    public String getIdOutSide() {
        return idOutSide;
    }

    public void setIdOutSide(String idOutSide) {
        this.idOutSide = idOutSide;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    public final  static String  GENDER_MALE="male";
    public final  static String  GENDER_FEMALE="female";
    public final  static String  OUTSIDE_FACEBOOK="facebook";
    public final  static String  OUTSIDE_GOOGLE="google";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(fullname);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(gender);
        dest.writeString(outside);
        dest.writeString(idOutSide);
    }
    protected Account(Parcel in) {
        id = in.readInt();
        username = in.readString();

        fullname = in.readString();
        email = in.readString();
        password = in.readString();
        gender = in.readString();
        outside = in.readString();
        idOutSide = in.readString();
    }
}
