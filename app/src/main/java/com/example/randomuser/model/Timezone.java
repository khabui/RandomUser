package com.example.randomuser.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timezone implements Parcelable {

    @SerializedName("offset")
    @Expose
    private String offset;

    @SerializedName("description")
    @Expose
    private String description;

    protected Timezone(Parcel in) {
        offset = in.readString();
        description = in.readString();
    }

    public static final Creator<Timezone> CREATOR = new Creator<Timezone>() {
        @Override
        public Timezone createFromParcel(Parcel in) {
            return new Timezone(in);
        }

        @Override
        public Timezone[] newArray(int size) {
            return new Timezone[size];
        }
    };

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(offset);
        parcel.writeString(description);
    }
}
