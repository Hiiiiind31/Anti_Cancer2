package com.example.hindahmed.anti_cancer;


public class User {

    public String username;
    public String email;
    public String sex;

    public User(String username, String email, String sex) {
        this.username = username;
        this.email = email;
        this.sex = sex;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

}
