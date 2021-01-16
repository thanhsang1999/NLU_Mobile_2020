package com.example.mobile.model;

import android.util.Log;

import java.util.Date;

public class Account {
    private String username;
    private String fullname;
    private String email;
    private String password;
    private Date dateOfBirth;
    private String gender;
    private String outside;
    private String idOutSide;

    public Account(String username, String fullname, String email, String password ) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;

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
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {

        return gender;
    }

    public void setGender(String gender) {
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
        outside = outside.toLowerCase();
        if(outside!=OUTSIDE_FACEBOOK){
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
}
