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
import com.example.securestorage.storage.InternalStorageManager;

public class InternalStorageFragment extends Fragment {

    private static final String DEFAULT_FILENAME_mouad = "secure_notes.txt";
    
    private EditText etFilename_mouad;
    private EditText etContent_mouad;
    private TextView tvFileContent_mouad;
    private InternalStorageManager storageManager_mouad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater_mouad, @Nullable ViewGroup container_mouad, @Nullable Bundle savedInstanceState_mouad) {
        View view_mouad = inflater_mouad.inflate(R.layout.fragment_internal_storage, container_mouad, false);
        
        storageManager_mouad = new InternalStorageManager(requireContext());
        
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
        
        if (filename_mouad.isEmpty()) {
            Toast.makeText(requireContext(), "Veuillez entrer un nom de fichier", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (content_mouad.isEmpty()) {
            Toast.makeText(requireContext(), "Veuillez entrer du contenu", Toast.LENGTH_SHORT).show();
            return;
        }
        
        boolean success_mouad = storageManager_mouad.writeToInternalStorage(filename_mouad, content_mouad);
        if (success_mouad) {
            Toast.makeText(requireContext(), "Fichier enregistré avec succès", Toast.LENGTH_SHORT).show();
            etContent_mouad.setText("");
        } else {
            Toast.makeText(requireContext(), "Erreur lors de l'enregistrement du fichier", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void loadFile_mouad() {
        String filename_mouad = etFilename_mouad.getText().toString();
        
        if (filename_mouad.isEmpty()) {
            Toast.makeText(requireContext(), "Veuillez entrer un nom de fichier", Toast.LENGTH_SHORT).show();
            return;
        }
        
        String content_mouad = storageManager_mouad.readFromInternalStorage(filename_mouad);
        if (content_mouad != null) {
            tvFileContent_mouad.setText(content_mouad);
        } else {
            tvFileContent_mouad.setText("Aucun contenu trouvé ou erreur de lecture");
            Toast.makeText(requireContext(), "Erreur lors de la lecture du fichier", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void deleteFile_mouad() {
        String filename_mouad = etFilename_mouad.getText().toString();
        
        if (filename_mouad.isEmpty()) {
            Toast.makeText(requireContext(), "Veuillez entrer un nom de fichier", Toast.LENGTH_SHORT).show();
            return;
        }
        
        boolean success_mouad = storageManager_mouad.deleteFromInternalStorage(filename_mouad);
        if (success_mouad) {
            Toast.makeText(requireContext(), "Fichier supprimé avec succès", Toast.LENGTH_SHORT).show();
            tvFileContent_mouad.setText("");
        } else {
            Toast.makeText(requireContext(), "Erreur lors de la suppression du fichier", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void listFiles_mouad() {
        String[] files_mouad = storageManager_mouad.listInternalStorageFiles();
        StringBuilder sb_mouad = new StringBuilder("Fichiers disponibles:\n");
        
        if (files_mouad.length == 0) {
            sb_mouad.append("Aucun fichier trouvé");
        } else {
            for (String file_mouad : files_mouad) {
                sb_mouad.append("- ").append(file_mouad).append("\n");
            }
        }
        
        tvFileContent_mouad.setText(sb_mouad.toString());
    }
}
