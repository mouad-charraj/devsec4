package com.example.securestorage.storage;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class ExternalStorageManager {
    private final Context context_mouad;

    public ExternalStorageManager(Context context_mouad) {
        this.context_mouad = context_mouad;
    }

    public boolean isExternalStorageWritable_mouad() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public boolean isExternalStorageReadable_mouad() {
        String state_mouad = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state_mouad) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state_mouad);
    }

    public File getExternalStorageDir_mouad(String type_mouad) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return context_mouad.getExternalFilesDir(type_mouad);
        } else {
            return type_mouad == null ? Environment.getExternalStorageDirectory() : Environment.getExternalStoragePublicDirectory(type_mouad);
        }
    }

    public boolean writeToExternalStorage(String filename_mouad, String content_mouad, String type_mouad) {
        if (!isExternalStorageWritable_mouad()) return false;

        File dir_mouad = getExternalStorageDir_mouad(type_mouad);
        if (dir_mouad == null || (!dir_mouad.exists() && !dir_mouad.mkdirs())) return false;

        File file_mouad = new File(dir_mouad, filename_mouad);
        try (FileOutputStream fos_mouad = new FileOutputStream(file_mouad)) {
            fos_mouad.write(content_mouad.getBytes());
            return true;
        } catch (Exception e_mouad) {
            return false;
        }
    }

    public String readFromExternalStorage(String filename_mouad, String type_mouad) {
        if (!isExternalStorageReadable_mouad()) return null;

        File file_mouad = new File(getExternalStorageDir_mouad(type_mouad), filename_mouad);
        try (FileInputStream fis_mouad = new FileInputStream(file_mouad);
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

    public boolean deleteFromExternalStorage(String filename_mouad, String type_mouad) {
        File file_mouad = new File(getExternalStorageDir_mouad(type_mouad), filename_mouad);
        return file_mouad.delete();
    }

    public String[] listExternalStorageFiles(String type_mouad) {
        File dir_mouad = getExternalStorageDir_mouad(type_mouad);
        if (dir_mouad != null && dir_mouad.exists() && dir_mouad.isDirectory()) {
            return dir_mouad.list();
        }
        return new String[0];
    }
}
