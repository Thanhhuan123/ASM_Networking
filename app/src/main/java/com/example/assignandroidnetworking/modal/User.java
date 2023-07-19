package com.example.assignandroidnetworking.modal;


import androidx.annotation.NonNull;

import java.util.ArrayList;

public class User {
    private String _id;
    private String email;
    private String password;

    public User(String _id, String email, String password) {
        this._id = _id;
        this.email = email;
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

}
