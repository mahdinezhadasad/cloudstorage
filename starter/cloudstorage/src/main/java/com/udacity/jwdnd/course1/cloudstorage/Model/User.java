package com.udacity.jwdnd.course1.cloudstorage.Model;

public class User {
    private Integer userId;
    private String password;
    private String salt;
    private String userName;
    private String firstName;
    private String lastName;

    public User(Integer userId, String password, String salt, String userName, String firstName, String lastName) {
        this.userId = userId;
        this.password = password;
        this.salt = salt;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
