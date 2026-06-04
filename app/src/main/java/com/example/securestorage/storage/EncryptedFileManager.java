package com.example.securestorage.storage;

import android.content.Context;
import androidx.security.crypto.EncryptedFile;
import androidx.security.crypto.MasterKey;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class EncryptedFileManager {
    private final Context context_mouad;
    private MasterKey masterKey_mouad;

    public EncryptedFileManager(Context context_mouad) {
        this.context_mouad = context_mouad;
        try {
            this.masterKey_mouad = new MasterKey.Builder(context_mouad)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
        } catch (Exception ignored_mouad) {
        }
    }

    public boolean writeEncryptedFile(String filename_mouad, String content_mouad) {
        try {
            if (masterKey_mouad == null) return false;

            File file_mouad = new File(context_mouad.getFilesDir(), filename_mouad);
            if (file_mouad.exists()) {
                file_mouad.delete();
            }

            EncryptedFile encFile_mouad = new EncryptedFile.Builder(
                    context_mouad,
                    file_mouad,
                    masterKey_mouad,
                    EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build();

            try (FileOutputStream fos_mouad = encFile_mouad.openFileOutput()) {
                fos_mouad.write(content_mouad.getBytes());
                return true;
            }
        } catch (Exception e_mouad) {
            return false;
        }
    }

    public String readEncryptedFile(String filename_mouad) {
        try {
            if (masterKey_mouad == null) return null;

            File file_mouad = new File(context_mouad.getFilesDir(), filename_mouad);
            if (!file_mouad.exists()) return null;

            EncryptedFile encFile_mouad = new EncryptedFile.Builder(
                    context_mouad,
                    file_mouad,
                    masterKey_mouad,
                    EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build();

            try (FileInputStream fis_mouad = encFile_mouad.openFileInput();
                 InputStreamReader isr_mouad = new InputStreamReader(fis_mouad);
                 BufferedReader br_mouad = new BufferedReader(isr_mouad)) {
                
                StringBuilder sb_mouad = new StringBuilder();
                String line_mouad;
                while ((line_mouad = br_mouad.readLine()) != null) {
                    sb_mouad.append(line_mouad).append("\n");
                }
                return sb_mouad.toString();
            }
        } catch (Exception e_mouad) {
            return null;
        }
    }

    public boolean deleteEncryptedFile(String filename_mouad) {
        File file_mouad = new File(context_mouad.getFilesDir(), filename_mouad);
        return file_mouad.exists() && file_mouad.delete();
    }

    public String[] listFiles() {
        return context_mouad.fileList();
    }
}
