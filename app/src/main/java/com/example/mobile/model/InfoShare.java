package com.example.mobile.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        InfoShare that= (InfoShare) o;
        if (that == null) return false;

        return this.email.equals(that.email) &&
                this.name.equals(that.name);
    }


}
