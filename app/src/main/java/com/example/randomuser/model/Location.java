package com.example.randomuser.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location implements Parcelable {

    @SerializedName("street")
    @Expose
    private Street street;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("postcode")
    @Expose
    private Integer postcode;

    @SerializedName("coordinates")
    @Expose
    private Coordinates coordinates;

    @SerializedName("timezone")
    @Expose
    private Timezone timezone;

    protected Location(Parcel in) {
        street = in.readParcelable(Street.class.getClassLoader());
        city = in.readString();
        state = in.readString();
        country = in.readString();
        if (in.readByte() == 0) {
            postcode = null;
        } else {
            postcode = in.readInt();
        }
        coordinates = in.readParcelable(Coordinates.class.getClassLoader());
        timezone = in.readParcelable(Timezone.class.getClassLoader());
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Timezone getTimezone() {
        return timezone;
    }

    public void setTimezone(Timezone timezone) {
        this.timezone = timezone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(street, i);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(country);
        if (postcode == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(postcode);
        }
        parcel.writeParcelable(coordinates, i);
        parcel.writeParcelable(timezone, i);
    }
}
