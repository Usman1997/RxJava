package com.example.hp.rxprac.Opertors;

import android.location.Address;

public class User {
    String name;
    String email;
    String gender;
    String address;

    public User(String name, String email, String gender, String address) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.address = address;
    }
    public User(){

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
