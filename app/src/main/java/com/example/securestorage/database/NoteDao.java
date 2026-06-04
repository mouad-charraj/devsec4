package com.example.securestorage.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    long insert(Note note_mouad);
    
    @Update
    void update(Note note_mouad);
    
    @Delete
    void delete(Note note_mouad);
    
    @Query("SELECT * FROM notes WHERE id = :id_mouad")
    Note getNoteById(int id_mouad);
    
    @Query("SELECT * FROM notes WHERE userId = :userId_mouad ORDER BY createdAt DESC")
    List<Note> getNotesByUserId(int userId_mouad);
    
    @Query("SELECT * FROM notes")
    List<Note> getAllNotes();
}
