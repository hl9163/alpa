package com.example.alpa;

public class user {
    public String Email;
    public String Password;

    public user(String email, String password) {
        Email = email;
        Password = password;
    }
    public user(){

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}

