package com.example.randomuser.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.randomuser.model.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
@TypeConverters({DataConverter.class})
public abstract class UserDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "userList";
    private static UserDatabase instance;

    public abstract UserDao userDao();

    public static UserDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "getInstance: Creating new database instance");
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        UserDatabase.class,
                        UserDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(TAG, "getInstance: Getting the database instance");
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
