package com.example.securestorage.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.securestorage.R;
import com.example.securestorage.storage.MediaStoreManager;

import java.util.List;
import java.util.Random;

public class ScopedStorageFragment extends Fragment {

    private MediaStoreManager mediaStoreManager_mouad;
    private ImageView ivSelectedImage_mouad;
    private TextView tvImageStatus_mouad;
    private ActivityResultLauncher<Intent> imagePickerLauncher_mouad;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState_mouad) {
        super.onCreate(savedInstanceState_mouad);
        imagePickerLauncher_mouad = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result_mouad -> {
                    if (result_mouad.getResultCode() == Activity.RESULT_OK && result_mouad.getData() != null) {
                        Uri uri_mouad = result_mouad.getData().getData();
                        if (uri_mouad != null) {
                            loadAndDisplayImage_mouad(uri_mouad);
                        }
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater_mouad, @Nullable ViewGroup container_mouad, @Nullable Bundle savedInstanceState_mouad) {
        View view_mouad = inflater_mouad.inflate(R.layout.fragment_scoped_storage, container_mouad, false);

        mediaStoreManager_mouad = new MediaStoreManager(requireContext());

        ivSelectedImage_mouad = view_mouad.findViewById(R.id.ivSelectedImage);
        tvImageStatus_mouad = view_mouad.findViewById(R.id.tvImageStatus);
        Button btnSaveDemoImage_mouad = view_mouad.findViewById(R.id.btnSaveDemoImage);
        Button btnPickImage_mouad = view_mouad.findViewById(R.id.btnPickImage);
        Button btnListImages_mouad = view_mouad.findViewById(R.id.btnListImages);

        btnSaveDemoImage_mouad.setOnClickListener(v_mouad -> saveDemoImage_mouad());
        btnPickImage_mouad.setOnClickListener(v_mouad -> pickImage_mouad());
        btnListImages_mouad.setOnClickListener(v_mouad -> listImages_mouad());

        return view_mouad;
    }

    private void saveDemoImage_mouad() {
        Bitmap bitmap_mouad = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas_mouad = new Canvas(bitmap_mouad);
        
        Random rnd_mouad = new Random();
        int color_mouad = Color.argb(255, rnd_mouad.nextInt(256), rnd_mouad.nextInt(256), rnd_mouad.nextInt(256));
        canvas_mouad.drawColor(color_mouad);

        Paint paint_mouad = new Paint();
        paint_mouad.setColor(Color.WHITE);
        paint_mouad.setTextSize(30f);
        paint_mouad.setAntiAlias(true);
        paint_mouad.setTextAlign(Paint.Align.CENTER);
        
        canvas_mouad.drawText("SecureStorageApp Demo", 250f, 220f, paint_mouad);
        canvas_mouad.drawText("Time: " + System.currentTimeMillis(), 250f, 280f, paint_mouad);

        String displayName_mouad = "demo_image_" + System.currentTimeMillis() + ".jpg";
        Uri uri_mouad = mediaStoreManager_mouad.saveImageToGallery(bitmap_mouad, displayName_mouad);

        if (uri_mouad != null) {
            Toast.makeText(requireContext(), "Enregistré", Toast.LENGTH_LONG).show();
            tvImageStatus_mouad.setText("URI: " + uri_mouad.toString());
            ivSelectedImage_mouad.setImageBitmap(bitmap_mouad);
        } else {
            Toast.makeText(requireContext(), "Erreur", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickImage_mouad() {
        Intent intent_mouad = MediaStoreManager.createImagePickerIntent();
        imagePickerLauncher_mouad.launch(intent_mouad);
    }

    private void loadAndDisplayImage_mouad(Uri uri_mouad) {
        Bitmap bitmap_mouad = mediaStoreManager_mouad.loadImageFromUri(uri_mouad);
        if (bitmap_mouad != null) {
            ivSelectedImage_mouad.setImageBitmap(bitmap_mouad);
            tvImageStatus_mouad.setText("OK: " + uri_mouad.toString());
        } else {
            Toast.makeText(requireContext(), "Erreur", Toast.LENGTH_SHORT).show();
        }
    }

    private void listImages_mouad() {
        List<Uri> uris_mouad = mediaStoreManager_mouad.getAllImages();
        StringBuilder sb_mouad = new StringBuilder("Images:\n");
        if (uris_mouad.isEmpty()) {
            sb_mouad.append("Aucune");
        } else {
            for (Uri u_mouad : uris_mouad) {
                sb_mouad.append("- ").append(u_mouad.toString()).append("\n");
            }
        }
        tvImageStatus_mouad.setText(sb_mouad.toString());
    }
}
