package com.example.securestorage.storage;

import android.content.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class InternalStorageManager {
    private final Context context_mouad;

    public InternalStorageManager(Context context_mouad) {
        this.context_mouad = context_mouad;
    }

    public boolean writeToInternalStorage(String filename_mouad, String content_mouad) {
        try (FileOutputStream fos_mouad = context_mouad.openFileOutput(filename_mouad, Context.MODE_PRIVATE)) {
            fos_mouad.write(content_mouad.getBytes());
            return true;
        } catch (Exception e_mouad) {
            return false;
        }
    }

    public String readFromInternalStorage(String filename_mouad) {
        try (FileInputStream fis_mouad = context_mouad.openFileInput(filename_mouad);
             InputStreamReader isr_mouad = new InputStreamReader(fis_mouad);
             BufferedReader br_mouad = new BufferedReader(isr_mouad)) {
            
            StringBuilder sb_mouad = new StringBuilder();
            String line_mouad;
            while ((line_mouad = br_mouad.readLine()) != null) {
                sb_mouad.append(line_mouad).append("\n");
            }
            return sb_mouad.toString();
        } catch (Exception e_mouad) {
            return null;
        }
    }

    public boolean deleteFromInternalStorage(String filename_mouad) {
        return context_mouad.deleteFile(filename_mouad);
    }

    public String[] listInternalStorageFiles() {
        return context_mouad.fileList();
    }
}
