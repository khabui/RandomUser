package com.example.randomuser.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user")
public class User implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    public int userId;

    @ColumnInfo(name = "gender")
    @SerializedName("gender")
    @Expose
    private String gender;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    private Name name;

    @ColumnInfo(name = "location")
    @SerializedName("location")
    @Expose
    private Location location;

    @ColumnInfo(name = "email")
    @SerializedName("email")
    @Expose
    private String email;

    @ColumnInfo(name = "login")
    @SerializedName("login")
    @Expose
    private Login login;

    @ColumnInfo(name = "dob")
    @SerializedName("dob")
    @Expose
    private Dob dob;

    @ColumnInfo(name = "registered")
    @SerializedName("registered")
    @Expose
    private Registered registered;

    @ColumnInfo(name = "phone")
    @SerializedName("phone")
    @Expose
    private String phone;

    @ColumnInfo(name = "cell")
    @SerializedName("cell")
    @Expose
    private String cell;

    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private Id id;

    @ColumnInfo(name = "picture")
    @SerializedName("picture")
    @Expose
    private Picture picture;

    @ColumnInfo(name = "nat")
    @SerializedName("nat")
    @Expose
    private String nat;

    /**
     * No args constructor for use in serialization
     */
    @Ignore
    public User() {
    }

    /**
     * @param picture
     * @param id
     * @param phone
     * @param email
     * @param location
     * @param registered
     * @param cell
     * @param dob
     * @param name
     * @param gender
     * @param nat
     * @param login
     */
    public User(String gender, Name name, Location location, String email, Login login, Dob dob, Registered registered, String phone, String cell, Id id, Picture picture, String nat) {
        super();
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
        this.picture = picture;
        this.nat = nat;
    }

    protected User(Parcel in) {
        gender = in.readString();
        name = in.readParcelable(Name.class.getClassLoader());
        location = in.readParcelable(Location.class.getClassLoader());
        email = in.readString();
        login = in.readParcelable(Login.class.getClassLoader());
        dob = in.readParcelable(Dob.class.getClassLoader());
        registered = in.readParcelable(Registered.class.getClassLoader());
        phone = in.readString();
        cell = in.readString();
        id = in.readParcelable(Id.class.getClassLoader());
        picture = in.readParcelable(Picture.class.getClassLoader());
        nat = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Dob getDob() {
        return dob;
    }

    public void setDob(Dob dob) {
        this.dob = dob;
    }

    public Registered getRegistered() {
        return registered;
    }

    public void setRegistered(Registered registered) {
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

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(gender);
        parcel.writeParcelable(name, i);
        parcel.writeParcelable(location, i);
        parcel.writeString(email);
        parcel.writeParcelable(login, i);
        parcel.writeParcelable(dob, i);
        parcel.writeParcelable(registered, i);
        parcel.writeString(phone);
        parcel.writeString(cell);
        parcel.writeParcelable(id, i);
        parcel.writeParcelable(picture, i);
        parcel.writeString(nat);
    }
}