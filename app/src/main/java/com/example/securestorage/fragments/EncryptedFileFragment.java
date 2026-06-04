package com.example.securestorage.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.securestorage.R;
import com.example.securestorage.storage.EncryptedFileManager;

public class EncryptedFileFragment extends Fragment {

    private static final String DEFAULT_FILENAME_mouad = "encrypted_notes.txt";
    
    private EditText etFilename_mouad;
    private EditText etContent_mouad;
    private TextView tvFileContent_mouad;
    private EncryptedFileManager encryptedFileManager_mouad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater_mouad, @Nullable ViewGroup container_mouad, @Nullable Bundle savedInstanceState_mouad) {
        View view_mouad = inflater_mouad.inflate(R.layout.fragment_encrypted_file, container_mouad, false);
        
        encryptedFileManager_mouad = new EncryptedFileManager(requireContext());
        
        etFilename_mouad = view_mouad.findViewById(R.id.etFilename);
        etContent_mouad = view_mouad.findViewById(R.id.etContent);
        tvFileContent_mouad = view_mouad.findViewById(R.id.tvFileContent);
        Button btnSave_mouad = view_mouad.findViewById(R.id.btnSave);
        Button btnLoad_mouad = view_mouad.findViewById(R.id.btnLoad);
        Button btnDelete_mouad = view_mouad.findViewById(R.id.btnDelete);
        Button btnList_mouad = view_mouad.findViewById(R.id.btnList);
        
        etFilename_mouad.setText(DEFAULT_FILENAME_mouad);
        
        btnSave_mouad.setOnClickListener(v_mouad -> saveFile_mouad());
        btnLoad_mouad.setOnClickListener(v_mouad -> loadFile_mouad());
        btnDelete_mouad.setOnClickListener(v_mouad -> deleteFile_mouad());
        btnList_mouad.setOnClickListener(v_mouad -> listFiles_mouad());
        
        return view_mouad;
    }
    
    private void saveFile_mouad() {
        String filename_mouad = etFilename_mouad.getText().toString();
        String content_mouad = etContent_mouad.getText().toString();
        
        if (filename_mouad.isEmpty() || content_mouad.isEmpty()) {
            Toast.makeText(requireContext(), "Champs requis", Toast.LENGTH_SHORT).show();
            return;
        }
        
        boolean success_mouad = encryptedFileManager_mouad.writeEncryptedFile(filename_mouad, content_mouad);
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
        
        String content_mouad = encryptedFileManager_mouad.readEncryptedFile(filename_mouad);
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
        
        boolean success_mouad = encryptedFileManager_mouad.deleteEncryptedFile(filename_mouad);
        if (success_mouad) {
            Toast.makeText(requireContext(), "Supprimé", Toast.LENGTH_SHORT).show();
            tvFileContent_mouad.setText("");
        } else {
            Toast.makeText(requireContext(), "Erreur", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void listFiles_mouad() {
        String[] files_mouad = encryptedFileManager_mouad.listFiles();
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
