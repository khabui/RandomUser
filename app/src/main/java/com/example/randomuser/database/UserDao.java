package com.example.randomuser.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.randomuser.model.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE userId = :userId LIMIT 1")
    User findUserByID(int userId);

    @Query("SELECT * FROM user")
    Single<List<User>> getUserList();

    @Insert(onConflict = REPLACE)
    void insertUser(User user);

    @Update(onConflict = REPLACE)
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM user")
    void deleteAll();
}
