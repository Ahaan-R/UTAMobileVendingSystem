package com.example.utamobilevendingsystem.Tables;

public class User_Credentials {
    int userID,recoverCode;
    String userName,password;

    public User_Credentials() {
    }

    public User_Credentials(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    public User_Credentials(int userID, int recoverCode, String userName, String password) {
        this.userID = userID;
        this.recoverCode = recoverCode;
        this.userName = userName;
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRecoverCode() {
        return recoverCode;
    }

    public void setRecoverCode(int recoverCode) {
        this.recoverCode = recoverCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
