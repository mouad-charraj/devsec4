package com.example.securestorage.storage;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MediaStoreManager {
    private final ContentResolver contentResolver_mouad;

    public MediaStoreManager(Context context_mouad) {
        this.contentResolver_mouad = context_mouad.getContentResolver();
    }

    public Uri saveImageToGallery(Bitmap bitmap_mouad, String displayName_mouad) {
        ContentValues values_mouad = new ContentValues();
        values_mouad.put(MediaStore.Images.Media.DISPLAY_NAME, displayName_mouad);
        values_mouad.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values_mouad.put(MediaStore.Images.Media.IS_PENDING, 1);
            values_mouad.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
        }

        Uri uri_mouad = null;
        try {
            uri_mouad = contentResolver_mouad.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values_mouad);
            if (uri_mouad != null) {
                try (OutputStream os_mouad = contentResolver_mouad.openOutputStream(uri_mouad)) {
                    if (os_mouad != null) {
                        bitmap_mouad.compress(Bitmap.CompressFormat.JPEG, 90, os_mouad);
                    }
                }
                
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values_mouad.clear();
                    values_mouad.put(MediaStore.Images.Media.IS_PENDING, 0);
                    contentResolver_mouad.update(uri_mouad, values_mouad, null, null);
                }
            }
        } catch (Exception e_mouad) {
            if (uri_mouad != null) {
                contentResolver_mouad.delete(uri_mouad, null, null);
                uri_mouad = null;
            }
        }
        return uri_mouad;
    }

    public List<Uri> getAllImages() {
        List<Uri> imageUris_mouad = new ArrayList<>();
        String[] projection_mouad = {MediaStore.Images.Media._ID};
        
        try (Cursor cursor_mouad = contentResolver_mouad.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection_mouad,
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED + " DESC")) {
            
            if (cursor_mouad != null) {
                int idColumn_mouad = cursor_mouad.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                while (cursor_mouad.moveToNext()) {
                    long id_mouad = cursor_mouad.getLong(idColumn_mouad);
                    Uri contentUri_mouad = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(id_mouad));
                    imageUris_mouad.add(contentUri_mouad);
                }
            }
        } catch (Exception ignored_mouad) {
        }
        return imageUris_mouad;
    }

    public static Intent createImagePickerIntent() {
        Intent intent_mouad = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent_mouad.addCategory(Intent.CATEGORY_OPENABLE);
        intent_mouad.setType("image/*");
        return intent_mouad;
    }

    public Bitmap loadImageFromUri(Uri uri_mouad) {
        try (java.io.InputStream is_mouad = contentResolver_mouad.openInputStream(uri_mouad)) {
            if (is_mouad != null) {
                return android.graphics.BitmapFactory.decodeStream(is_mouad);
            }
        } catch (Exception ignored_mouad) {
        }
        return null;
    }
}
