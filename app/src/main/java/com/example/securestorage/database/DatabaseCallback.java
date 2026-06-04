package com.example.securestorage.database;

public interface DatabaseCallback<T> {
    void onComplete(T result_mouad);
    void onError(Exception e_mouad);
}
