package com.example.securestorage.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface UserDao {
    @Insert
    long insert(User user_mouad);
    
    @Update
    void update(User user_mouad);
    
    @Delete
    void delete(User user_mouad);
    
    @Query("SELECT * FROM users WHERE id = :id_mouad")
    User getUserById(int id_mouad);
    
    @Query("SELECT * FROM users WHERE username = :username_mouad")
    User getUserByUsername(String username_mouad);
    
    @Query("SELECT * FROM users")
    List<User> getAllUsers();
}
