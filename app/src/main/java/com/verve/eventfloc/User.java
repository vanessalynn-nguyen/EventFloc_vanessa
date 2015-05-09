package com.verve.eventfloc;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Vanessa on 8/05/2015.
 */
public class User {

    private int userID;
    private String userEmail;
    private String password;
    private int userType;

    public User() {
    }

    public User(String userEmail, String password,int userType ) throws InvalidKeySpecException, NoSuchAlgorithmException {
        this.userEmail = userEmail;
        this.userType = userType;
        //this.password = password; //encryption
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String toString() {
        String s = "ID: " + userID + ", email: " + userEmail + ", user type: " + userType;
        return s;
    }
}
