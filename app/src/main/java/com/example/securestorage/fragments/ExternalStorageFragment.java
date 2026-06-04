package com.example.securestorage.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.securestorage.R;
import com.example.securestorage.storage.ExternalStorageManager;

public class ExternalStorageFragment extends Fragment {

    private static final String DEFAULT_FILENAME_mouad = "external_notes.txt";
    private static final int PERMISSION_REQUEST_CODE_mouad = 123;
    
    private EditText etFilename_mouad;
    private EditText etContent_mouad;
    private TextView tvFileContent_mouad;
    private ExternalStorageManager storageManager_mouad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater_mouad, @Nullable ViewGroup container_mouad, @Nullable Bundle savedInstanceState_mouad) {
        View view_mouad = inflater_mouad.inflate(R.layout.fragment_external_storage, container_mouad, false);
        
        storageManager_mouad = new ExternalStorageManager(requireContext());
        
        etFilename_mouad = view_mouad.findViewById(R.id.etFilename);
        etContent_mouad = view_mouad.findViewById(R.id.etContent);
        tvFileContent_mouad = view_mouad.findViewById(R.id.tvFileContent);
        Button btnSave_mouad = view_mouad.findViewById(R.id.btnSave);
        Button btnLoad_mouad = view_mouad.findViewById(R.id.btnLoad);
        Button btnDelete_mouad = view_mouad.findViewById(R.id.btnDelete);
        Button btnList_mouad = view_mouad.findViewById(R.id.btnList);
        
        etFilename_mouad.setText(DEFAULT_FILENAME_mouad);
        
        btnSave_mouad.setOnClickListener(v_mouad -> {
            if (checkPermissions_mouad()) {
                saveFile_mouad();
            }
        });
        
        btnLoad_mouad.setOnClickListener(v_mouad -> {
            if (checkPermissions_mouad()) {
                loadFile_mouad();
            }
        });
        
        btnDelete_mouad.setOnClickListener(v_mouad -> {
            if (checkPermissions_mouad()) {
                deleteFile_mouad();
            }
        });
        
        btnList_mouad.setOnClickListener(v_mouad -> {
            if (checkPermissions_mouad()) {
                listFiles_mouad();
            }
        });
        
        return view_mouad;
    }
    
    private boolean checkPermissions_mouad() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return true;
        }
        
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE_mouad);
            return false;
        }
        
        return true;
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode_mouad, @NonNull String[] permissions_mouad, @NonNull int[] grantResults_mouad) {
        if (requestCode_mouad == PERMISSION_REQUEST_CODE_mouad) {
            if (grantResults_mouad.length > 0 && grantResults_mouad[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Permission accordée", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Permission refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    private void saveFile_mouad() {
        String filename_mouad = etFilename_mouad.getText().toString();
        String content_mouad = etContent_mouad.getText().toString();
        
        if (filename_mouad.isEmpty()) {
            Toast.makeText(requireContext(), "Nom requis", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (content_mouad.isEmpty()) {
            Toast.makeText(requireContext(), "Contenu requis", Toast.LENGTH_SHORT).show();
            return;
        }
        
        boolean success_mouad = storageManager_mouad.writeToExternalStorage(filename_mouad, content_mouad, Environment.DIRECTORY_DOCUMENTS);
        if (success_mouad) {
            Toast.makeText(requireContext(), "Enregistré", Toast.LENGTH_SHORT).show();
            etContent_mouad.setText("");
        } else {
            Toast.makeText(requireContext(), "Erreur", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void loadFile_mouad() {
        String filename_mouad = etFilename_mouad.getText().toString();
        
        if (filename_mouad.isEmpty()) {
            Toast.makeText(requireContext(), "Nom requis", Toast.LENGTH_SHORT).show();
            return;
        }
        
        String content_mouad = storageManager_mouad.readFromExternalStorage(filename_mouad, Environment.DIRECTORY_DOCUMENTS);
        if (content_mouad != null) {
            tvFileContent_mouad.setText(content_mouad);
        } else {
            tvFileContent_mouad.setText("Erreur");
        }
    }
    
    private void deleteFile_mouad() {
        String filename_mouad = etFilename_mouad.getText().toString();
        
        if (filename_mouad.isEmpty()) {
            Toast.makeText(requireContext(), "Nom requis", Toast.LENGTH_SHORT).show();
            return;
        }
        
        boolean success_mouad = storageManager_mouad.deleteFromExternalStorage(filename_mouad, Environment.DIRECTORY_DOCUMENTS);
        if (success_mouad) {
            Toast.makeText(requireContext(), "Supprimé", Toast.LENGTH_SHORT).show();
            tvFileContent_mouad.setText("");
        } else {
            Toast.makeText(requireContext(), "Erreur", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void listFiles_mouad() {
        String[] files_mouad = storageManager_mouad.listExternalStorageFiles(Environment.DIRECTORY_DOCUMENTS);
        StringBuilder sb_mouad = new StringBuilder("Fichiers:\n");
        
        if (files_mouad.length == 0) {
            sb_mouad.append("Aucun");
        } else {
            for (String file_mouad : files_mouad) {
                sb_mouad.append("- ").append(file_mouad).append("\n");
            }
        }
        
        tvFileContent_mouad.setText(sb_mouad.toString());
    }
}
