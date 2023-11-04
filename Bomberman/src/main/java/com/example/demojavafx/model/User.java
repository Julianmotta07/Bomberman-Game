package com.example.demojavafx.model;

// Singleton Class, multiple controllers can access the same unique User instance
public class User {

    static private User user;
    private String username;
    private String password;

    private User(){
    }

    static public User getInstance(){
        if(user==null) user = new User();
        return user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}