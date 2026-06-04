package com.example.securestorage.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SupportFactory;
import java.security.SecureRandom;

@Database(entities = {User.class, Note.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    
    private static final String DATABASE_NAME_mouad = "secure_app_db";
    private static AppDatabase instance_mouad;
    
    public abstract UserDao userDao();
    public abstract NoteDao noteDao();
    
    public static synchronized AppDatabase getInstance(Context context_mouad, char[] passphrase_mouad) {
        if (instance_mouad == null) {
            byte[] passphraseBytes_mouad = SQLiteDatabase.getBytes(passphrase_mouad);
            SupportFactory factory_mouad = new SupportFactory(passphraseBytes_mouad);
            
            instance_mouad = Room.databaseBuilder(context_mouad.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME_mouad)
                    .openHelperFactory(factory_mouad)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance_mouad;
    }
    
    public static char[] generateRandomPassphrase(int length_mouad) {
        String allowedChars_mouad = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        SecureRandom random_mouad = new SecureRandom();
        char[] passphrase_mouad = new char[length_mouad];
        
        for (int i_mouad = 0; i_mouad < length_mouad; i_mouad++) {
            passphrase_mouad[i_mouad] = allowedChars_mouad.charAt(random_mouad.nextInt(allowedChars_mouad.length()));
        }
        
        return passphrase_mouad;
    }
}
