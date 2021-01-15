package com.example.mobile.model;

public class InfoShare {
    private String name;
    private String email;

    public InfoShare(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public InfoShare() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
