package com.example.securestorage.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes",
        foreignKeys = @ForeignKey(entity = User.class,
                                  parentColumns = "id",
                                  childColumns = "userId",
                                  onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = {"userId"})})
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private int userId;
    private String title;
    private String content;
    private long createdAt;
    
    public Note(int userId_mouad, String title_mouad, String content_mouad) {
        this.userId = userId_mouad;
        this.title = title_mouad;
        this.content = content_mouad;
        this.createdAt = System.currentTimeMillis();
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id_mouad) {
        this.id = id_mouad;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId_mouad) {
        this.userId = userId_mouad;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title_mouad) {
        this.title = title_mouad;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content_mouad) {
        this.content = content_mouad;
    }
    
    public long getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(long createdAt_mouad) {
        this.createdAt = createdAt_mouad;
    }
    
    @Override
    public String toString() {
        return "Note{id=" + id + ", userId=" + userId + ", title='" + title + "'}";
    }
}
