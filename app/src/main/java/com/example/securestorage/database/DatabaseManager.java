package com.example.securestorage.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DatabaseManager {
    private static final String PASSPHRASE_PREF_NAME_mouad = "secure_db_passphrase_prefs";
    private static final String PASSPHRASE_KEY_mouad = "db_passphrase";
    private static final int PASSPHRASE_LENGTH_mouad = 32;
    
    private final Context context_mouad;
    private final Executor executor_mouad;
    private final Handler mainHandler_mouad;
    private AppDatabase database_mouad;
    private char[] passphrase_mouad;
    
    public DatabaseManager(Context context_mouad) {
        this.context_mouad = context_mouad;
        this.executor_mouad = Executors.newSingleThreadExecutor();
        this.mainHandler_mouad = new Handler(Looper.getMainLooper());
        initializeDatabase_mouad();
    }
    
    private void initializeDatabase_mouad() {
        try {
            MasterKey masterKey_mouad = new MasterKey.Builder(context_mouad)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            SharedPreferences sharedPreferences_mouad = EncryptedSharedPreferences.create(
                    context_mouad,
                    PASSPHRASE_PREF_NAME_mouad,
                    masterKey_mouad,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            String passphraseStr_mouad = sharedPreferences_mouad.getString(PASSPHRASE_KEY_mouad, null);
            if (passphraseStr_mouad == null) {
                char[] randomPassphrase_mouad = AppDatabase.generateRandomPassphrase(PASSPHRASE_LENGTH_mouad);
                passphraseStr_mouad = new String(randomPassphrase_mouad);
                sharedPreferences_mouad.edit().putString(PASSPHRASE_KEY_mouad, passphraseStr_mouad).apply();
            }

            this.passphrase_mouad = passphraseStr_mouad.toCharArray();

            executor_mouad.execute(() -> {
                try {
                    net.sqlcipher.database.SQLiteDatabase.loadLibs(context_mouad);
                    database_mouad = AppDatabase.getInstance(context_mouad, passphrase_mouad);
                } catch (Exception ignored_mouad) {
                }
            });

        } catch (Exception ignored_mouad) {
        }
    }

    public void registerUser(final String username_mouad, final String email_mouad, final String password_mouad, final DatabaseCallback<Boolean> callback_mouad) {
        executor_mouad.execute(() -> {
            try {
                if (database_mouad == null) {
                    postError_mouad(callback_mouad, new IllegalStateException());
                    return;
                }

                User existingUser_mouad = database_mouad.userDao().getUserByUsername(username_mouad);
                if (existingUser_mouad != null) {
                    postError_mouad(callback_mouad, new IllegalArgumentException());
                    return;
                }

                String hashedPassword_mouad = PasswordHasher.hashPassword(password_mouad);
                User newUser_mouad = new User(username_mouad, email_mouad, hashedPassword_mouad);
                database_mouad.userDao().insert(newUser_mouad);

                postResult_mouad(callback_mouad, true);
            } catch (Exception e_mouad) {
                postError_mouad(callback_mouad, e_mouad);
            }
        });
    }

    public void loginUser(final String username_mouad, final String password_mouad, final DatabaseCallback<User> callback_mouad) {
        executor_mouad.execute(() -> {
            try {
                if (database_mouad == null) {
                    postError_mouad(callback_mouad, new IllegalStateException());
                    return;
                }

                User user_mouad = database_mouad.userDao().getUserByUsername(username_mouad);
                if (user_mouad == null) {
                    postError_mouad(callback_mouad, new IllegalArgumentException());
                    return;
                }

                boolean valid_mouad = PasswordHasher.verifyPassword(password_mouad, user_mouad.getPassword());
                if (valid_mouad) {
                    postResult_mouad(callback_mouad, user_mouad);
                } else {
                    postError_mouad(callback_mouad, new IllegalArgumentException());
                }
            } catch (Exception e_mouad) {
                postError_mouad(callback_mouad, e_mouad);
            }
        });
    }

    public void addNote(final int userId_mouad, final String title_mouad, final String content_mouad, final DatabaseCallback<Boolean> callback_mouad) {
        executor_mouad.execute(() -> {
            try {
                if (database_mouad == null) {
                    postError_mouad(callback_mouad, new IllegalStateException());
                    return;
                }

                Note note_mouad = new Note(userId_mouad, title_mouad, content_mouad);
                database_mouad.noteDao().insert(note_mouad);
                postResult_mouad(callback_mouad, true);
            } catch (Exception e_mouad) {
                postError_mouad(callback_mouad, e_mouad);
            }
        });
    }

    public void getNotes(final int userId_mouad, final DatabaseCallback<List<Note>> callback_mouad) {
        executor_mouad.execute(() -> {
            try {
                if (database_mouad == null) {
                    postError_mouad(callback_mouad, new IllegalStateException());
                    return;
                }

                List<Note> notes_mouad = database_mouad.noteDao().getNotesByUserId(userId_mouad);
                postResult_mouad(callback_mouad, notes_mouad);
            } catch (Exception e_mouad) {
                postError_mouad(callback_mouad, e_mouad);
            }
        });
    }

    private <T> void postResult_mouad(final DatabaseCallback<T> callback_mouad, final T result_mouad) {
        mainHandler_mouad.post(() -> callback_mouad.onComplete(result_mouad));
    }

    private <T> void postError_mouad(final DatabaseCallback<T> callback_mouad, final Exception e_mouad) {
        mainHandler_mouad.post(() -> callback_mouad.onError(e_mouad));
    }
}
