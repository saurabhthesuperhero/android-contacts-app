package com.example.contactsapp;

public class ContactsModel {

    // variables for our user name
    // and contact number.
    private String userName;
    private String contactNumber;
    private String company;
    private String email;



    // constructor
    public ContactsModel(String userName, String contactNumber, String company, String email) {
        this.userName = userName;
        this.contactNumber = contactNumber;
        this.company = company;
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // on below line we have
    // created getter and setter
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

}