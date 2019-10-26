package com.example.randomuser.database;

import androidx.room.TypeConverter;

import com.example.randomuser.model.Dob;
import com.example.randomuser.model.Id;
import com.example.randomuser.model.Location;
import com.example.randomuser.model.Login;
import com.example.randomuser.model.Name;
import com.example.randomuser.model.Picture;
import com.example.randomuser.model.Registered;
import com.google.gson.Gson;

import java.io.Serializable;

class DataConverter implements Serializable {

    @TypeConverter
    public static String fromNameObject(Name name) {
        if (name == null) {
            return (null);
        }

        Gson gson = new Gson();

        return gson.toJson(name);
    }

    @TypeConverter
    public Name toNameObject(String nameString) {
        if (nameString == null) {
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(nameString, Name.class);
    }

    @TypeConverter
    public static String fromLocationObject(Location location) {
        if (location == null) {
            return (null);
        }

        Gson gson = new Gson();

        return gson.toJson(location);
    }

    @TypeConverter
    public Location toLocationObject(String locationString) {
        if (locationString == null) {
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(locationString, Location.class);
    }

    @TypeConverter
    public static String fromLoginObject(Login login) {
        if (login == null) {
            return (null);
        }

        Gson gson = new Gson();

        return gson.toJson(login);
    }

    @TypeConverter
    public Login toLoginObject(String loginString) {
        if (loginString == null) {
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(loginString, Login.class);
    }

    @TypeConverter
    public static String fromDobObject(Dob dob) {
        if (dob == null) {
            return (null);
        }

        Gson gson = new Gson();

        return gson.toJson(dob);
    }

    @TypeConverter
    public Dob toDobObject(String dobString) {
        if (dobString == null) {
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(dobString, Dob.class);
    }

    @TypeConverter
    public static String fromRegisteredObject(Registered registered) {
        if (registered == null) {
            return (null);
        }

        Gson gson = new Gson();

        return gson.toJson(registered);
    }

    @TypeConverter
    public Registered toRegisteredObject(String registeredString) {
        if (registeredString == null) {
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(registeredString, Registered.class);
    }

    @TypeConverter
    public static String fromIdObject(Id id) {
        if (id == null) {
            return (null);
        }

        Gson gson = new Gson();

        return gson.toJson(id);
    }

    @TypeConverter
    public Id toIdObject(String idString) {
        if (idString == null) {
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(idString, Id.class);
    }

    @TypeConverter
    public static String fromPictureObject(Picture picture) {
        if (picture == null) {
            return (null);
        }

        Gson gson = new Gson();

        return gson.toJson(picture);
    }

    @TypeConverter
    public Picture toPictureObject(String pictureString) {
        if (pictureString == null) {
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(pictureString, Picture.class);
    }
}
