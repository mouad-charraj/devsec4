package com.example.securestorage.fragments;

import android.graphics.Color;
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
import com.example.securestorage.database.DatabaseCallback;
import com.example.securestorage.database.DatabaseManager;
import com.example.securestorage.database.Note;
import com.example.securestorage.database.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseFragment extends Fragment {

    private DatabaseManager databaseManager_mouad;
    private User currentUser_mouad = null;

    private EditText etUsername_mouad;
    private EditText etEmail_mouad;
    private EditText etPassword_mouad;
    private TextView tvCurrentUser_mouad;
    private Button btnRegister_mouad;
    private Button btnLogin_mouad;
    private Button btnLogout_mouad;

    private View cardNotes_mouad;
    private View cardNotesDisplay_mouad;
    private EditText etNoteTitle_mouad;
    private EditText etNoteContent_mouad;
    private TextView tvNotesList_mouad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater_mouad, @Nullable ViewGroup container_mouad, @Nullable Bundle savedInstanceState_mouad) {
        View view_mouad = inflater_mouad.inflate(R.layout.fragment_database, container_mouad, false);

        databaseManager_mouad = new DatabaseManager(requireContext());

        etUsername_mouad = view_mouad.findViewById(R.id.etUsername);
        etEmail_mouad = view_mouad.findViewById(R.id.etEmail);
        etPassword_mouad = view_mouad.findViewById(R.id.etPassword);
        tvCurrentUser_mouad = view_mouad.findViewById(R.id.tvCurrentUser);
        btnRegister_mouad = view_mouad.findViewById(R.id.btnRegister);
        btnLogin_mouad = view_mouad.findViewById(R.id.btnLogin);
        btnLogout_mouad = view_mouad.findViewById(R.id.btnLogout);

        cardNotes_mouad = view_mouad.findViewById(R.id.cardNotes);
        cardNotesDisplay_mouad = view_mouad.findViewById(R.id.cardNotesDisplay);
        etNoteTitle_mouad = view_mouad.findViewById(R.id.etNoteTitle);
        etNoteContent_mouad = view_mouad.findViewById(R.id.etNoteContent);
        tvNotesList_mouad = view_mouad.findViewById(R.id.tvNotesList);
        Button btnAddNote_mouad = view_mouad.findViewById(R.id.btnAddNote);

        btnRegister_mouad.setOnClickListener(v_mouad -> handleRegistration_mouad());
        btnLogin_mouad.setOnClickListener(v_mouad -> handleLogin_mouad());
        btnLogout_mouad.setOnClickListener(v_mouad -> handleLogout_mouad());
        btnAddNote_mouad.setOnClickListener(v_mouad -> handleAddNote_mouad());

        updateUiState_mouad();

        return view_mouad;
    }

    private void handleRegistration_mouad() {
        String username_mouad = etUsername_mouad.getText().toString().trim();
        String email_mouad = etEmail_mouad.getText().toString().trim();
        String password_mouad = etPassword_mouad.getText().toString();

        if (username_mouad.isEmpty() || email_mouad.isEmpty() || password_mouad.isEmpty()) {
            Toast.makeText(requireContext(), "Veuillez remplir tous les champs d'inscription", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseManager_mouad.registerUser(username_mouad, email_mouad, password_mouad, new DatabaseCallback<Boolean>() {
            @Override
            public void onComplete(Boolean success_mouad) {
                if (success_mouad) {
                    Toast.makeText(requireContext(), "Inscription réussie !", Toast.LENGTH_LONG).show();
                    etEmail_mouad.setText("");
                    etPassword_mouad.setText("");
                }
            }

            @Override
            public void onError(Exception e_mouad) {
                Toast.makeText(requireContext(), "Erreur d'inscription: " + e_mouad.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleLogin_mouad() {
        String username_mouad = etUsername_mouad.getText().toString().trim();
        String password_mouad = etPassword_mouad.getText().toString();

        if (username_mouad.isEmpty() || password_mouad.isEmpty()) {
            Toast.makeText(requireContext(), "Veuillez entrer votre nom d'utilisateur et mot de passe", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseManager_mouad.loginUser(username_mouad, password_mouad, new DatabaseCallback<User>() {
            @Override
            public void onComplete(User user_mouad) {
                currentUser_mouad = user_mouad;
                Toast.makeText(requireContext(), "Connexion réussie ! Bonjour " + user_mouad.getUsername(), Toast.LENGTH_SHORT).show();
                updateUiState_mouad();
                loadUserNotes_mouad();
            }

            @Override
            public void onError(Exception e_mouad) {
                Toast.makeText(requireContext(), "Erreur de connexion: " + e_mouad.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleLogout_mouad() {
        currentUser_mouad = null;
        Toast.makeText(requireContext(), "Déconnecté", Toast.LENGTH_SHORT).show();
        etUsername_mouad.setText("");
        etPassword_mouad.setText("");
        updateUiState_mouad();
    }

    private void handleAddNote_mouad() {
        if (currentUser_mouad == null) return;

        String title_mouad = etNoteTitle_mouad.getText().toString().trim();
        String content_mouad = etNoteContent_mouad.getText().toString().trim();

        if (title_mouad.isEmpty() || content_mouad.isEmpty()) {
            Toast.makeText(requireContext(), "Veuillez saisir un titre et un contenu", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseManager_mouad.addNote(currentUser_mouad.getId(), title_mouad, content_mouad, new DatabaseCallback<Boolean>() {
            @Override
            public void onComplete(Boolean result_mouad) {
                Toast.makeText(requireContext(), "Note enregistrée !", Toast.LENGTH_SHORT).show();
                etNoteTitle_mouad.setText("");
                etNoteContent_mouad.setText("");
                loadUserNotes_mouad();
            }

            @Override
            public void onError(Exception e_mouad) {
                Toast.makeText(requireContext(), "Erreur d'enregistrement: " + e_mouad.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadUserNotes_mouad() {
        if (currentUser_mouad == null) return;

        databaseManager_mouad.getNotes(currentUser_mouad.getId(), new DatabaseCallback<List<Note>>() {
            @Override
            public void onComplete(List<Note> notes_mouad) {
                StringBuilder sb_mouad = new StringBuilder();
                if (notes_mouad.isEmpty()) {
                    sb_mouad.append("Aucune note enregistrée.");
                } else {
                    SimpleDateFormat sdf_mouad = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                    for (Note note_mouad : notes_mouad) {
                        String dateStr_mouad = sdf_mouad.format(new Date(note_mouad.getCreatedAt()));
                        sb_mouad.append("📌 ").append(note_mouad.getTitle()).append(" (").append(dateStr_mouad).append(")\n")
                          .append(note_mouad.getContent()).append("\n\n");
                    }
                }
                tvNotesList_mouad.setText(sb_mouad.toString());
            }

            @Override
            public void onError(Exception e_mouad) {
                tvNotesList_mouad.setText("Erreur: " + e_mouad.getMessage());
            }
        });
    }

    private void updateUiState_mouad() {
        if (currentUser_mouad == null) {
            tvCurrentUser_mouad.setText("Statut : Non connecté");
            tvCurrentUser_mouad.setTextColor(Color.parseColor("#E53935"));
            cardNotes_mouad.setVisibility(View.GONE);
            cardNotesDisplay_mouad.setVisibility(View.GONE);
            btnRegister_mouad.setVisibility(View.VISIBLE);
            btnLogin_mouad.setVisibility(View.VISIBLE);
            btnLogout_mouad.setVisibility(View.GONE);
        } else {
            tvCurrentUser_mouad.setText("Connecté en tant que : " + currentUser_mouad.getUsername());
            tvCurrentUser_mouad.setTextColor(Color.parseColor("#4CAF50"));
            cardNotes_mouad.setVisibility(View.VISIBLE);
            cardNotesDisplay_mouad.setVisibility(View.VISIBLE);
            btnRegister_mouad.setVisibility(View.GONE);
            btnLogin_mouad.setVisibility(View.GONE);
            btnLogout_mouad.setVisibility(View.VISIBLE);
        }
    }
}
