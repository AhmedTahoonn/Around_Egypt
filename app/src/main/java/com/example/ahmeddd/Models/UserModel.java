package com.example.ahmeddd.Models;

public class UserModel {
    String uId;
    String Email_Address;
    String Username;
    String Phone;
    String profileImage;

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public UserModel() {
    }

    public UserModel(String uId, String Email_Address, String Username, String Phone, String profileImage) {
        this.uId = uId;
        this.Email_Address = Email_Address;
        this.Username = Username;
        this.Phone = Phone;
       this.profileImage=profileImage;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getEmail_Address() {
        return Email_Address;
    }

    public void setEmail_Address(String email_Address) {
        Email_Address = email_Address;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
