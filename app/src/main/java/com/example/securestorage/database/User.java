package com.example.securestorage.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String username;
    private String email;
    private String password;
    
    public User(String username_mouad, String email_mouad, String password_mouad) {
        this.username = username_mouad;
        this.email = email_mouad;
        this.password = password_mouad;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id_mouad) {
        this.id = id_mouad;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username_mouad) {
        this.username = username_mouad;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email_mouad) {
        this.email = email_mouad;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password_mouad) {
        this.password = password_mouad;
    }
    
    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "'}";
    }
}
