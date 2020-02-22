package com.example.utamobilevendingsystem.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDetails {

    private int userId;
    private int fName;
    private int lName;
    private int utaID;
    private Date dob;
    private String phoneNummber;
    private String email;
    private String address;
    private String city;
    private String state;
    private String ZIP;

    private List<UserCart> userCart = new ArrayList<UserCart>();
    private  List<Payments> payments = new ArrayList<Payments>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getfName() {
        return fName;
    }

    public void setfName(int fName) {
        this.fName = fName;
    }

    public int getlName() {
        return lName;
    }

    public void setlName(int lName) {
        this.lName = lName;
    }

    public int getUtaID() {
        return utaID;
    }

    public void setUtaID(int utaID) {
        this.utaID = utaID;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhoneNummber() {
        return phoneNummber;
    }

    public void setPhoneNummber(String phoneNummber) {
        this.phoneNummber = phoneNummber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZIP() {
        return ZIP;
    }

    public void setZIP(String ZIP) {
        this.ZIP = ZIP;
    }

    public List<UserCart> getUserCart() {
        return userCart;
    }

    public List<Payments> getPayments() {
        return payments;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userId=" + userId +
                ", fName=" + fName +
                ", lName=" + lName +
                ", utaID=" + utaID +
                ", dob=" + dob +
                ", phoneNummber='" + phoneNummber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", ZIP='" + ZIP + '\'' +
                '}';
    }
}
