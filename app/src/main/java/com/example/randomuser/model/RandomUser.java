package com.example.randomuser.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RandomUser implements Parcelable {
    private String gender;
    private String name;
    private String location;
    private String email;
    private String login;
    private String dob;
    private String registered;
    private String phone;
    private String cell;
    private String id;
    private String largePictureURL;
    private String mediumPictureURL;

    public RandomUser() { }

    public RandomUser(String gender, String name, String location, String email, String login, String dob, String registered, String phone, String cell, String id, String largePictureURL, String mediumPictureURL) {
        this.gender = gender;
        this.name = name;
        this.location = location;
        this.email = email;
        this.login = login;
        this.dob = dob;
        this.registered = registered;
        this.phone = phone;
        this.cell = cell;
        this.id = id;
        this.largePictureURL = largePictureURL;
        this.mediumPictureURL = mediumPictureURL;
    }

    protected RandomUser(Parcel in) {
        gender = in.readString();
        name = in.readString();
        location = in.readString();
        email = in.readString();
        login = in.readString();
        dob = in.readString();
        registered = in.readString();
        phone = in.readString();
        cell = in.readString();
        id = in.readString();
        largePictureURL = in.readString();
        mediumPictureURL = in.readString();
    }

    public static final Creator<RandomUser> CREATOR = new Creator<RandomUser>() {
        @Override
        public RandomUser createFromParcel(Parcel in) {
            return new RandomUser(in);
        }

        @Override
        public RandomUser[] newArray(int size) {
            return new RandomUser[size];
        }
    };

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLargePictureURL() {
        return largePictureURL;
    }

    public void setLargePictureURL(String largePictureURL) {
        this.largePictureURL = largePictureURL;
    }

    public String getMediumPictureURL() {
        return mediumPictureURL;
    }

    public void setMediumPictureURL(String mediumPictureURL) {
        this.mediumPictureURL = mediumPictureURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(gender);
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(email);
        parcel.writeString(login);
        parcel.writeString(dob);
        parcel.writeString(registered);
        parcel.writeString(phone);
        parcel.writeString(cell);
        parcel.writeString(id);
        parcel.writeString(largePictureURL);
        parcel.writeString(mediumPictureURL);
    }
}
