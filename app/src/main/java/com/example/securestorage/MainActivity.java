package com.example.securestorage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.securestorage.fragments.DatabaseFragment;
import com.example.securestorage.fragments.EncryptedFileFragment;
import com.example.securestorage.fragments.ExternalStorageFragment;
import com.example.securestorage.fragments.InternalStorageFragment;
import com.example.securestorage.fragments.ScopedStorageFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState_mouad) {
        super.onCreate(savedInstanceState_mouad);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout_mouad = findViewById(R.id.tabLayout);
        ViewPager2 viewPager_mouad = findViewById(R.id.viewPager);

        viewPager_mouad.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position_mouad) {
                switch (position_mouad) {
                    case 0:
                        return new InternalStorageFragment();
                    case 1:
                        return new ExternalStorageFragment();
                    case 2:
                        return new ScopedStorageFragment();
                    case 3:
                        return new DatabaseFragment();
                    case 4:
                        return new EncryptedFileFragment();
                    default:
                        return new InternalStorageFragment();
                }
            }

            @Override
            public int getItemCount() {
                return 5;
            }
        });

        new TabLayoutMediator(tabLayout_mouad, viewPager_mouad, (tab_mouad, position_mouad) -> {
            switch (position_mouad) {
                case 0:
                    tab_mouad.setText("Interne");
                    break;
                case 1:
                    tab_mouad.setText("Externe");
                    break;
                case 2:
                    tab_mouad.setText("MediaStore");
                    break;
                case 3:
                    tab_mouad.setText("Room DB");
                    break;
                case 4:
                    tab_mouad.setText("Fichiers Chiffrés");
                    break;
            }
        }).attach();
    }
}
