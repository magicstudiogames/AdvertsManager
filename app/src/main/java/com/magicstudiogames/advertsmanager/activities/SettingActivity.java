package com.magicstudiogames.advertsmanager.activities;

import static com.magicstudiogames.advertsmanager.databinding.ActivitySettingBinding.inflate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.magicstudiogames.advertsmanager.R;
import com.magicstudiogames.advertsmanager.databinding.ActivitySettingBinding;

@SuppressLint("StaticFieldLeak")
public class SettingActivity extends AppCompatActivity {

    protected static ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        setContentView(binding.getRoot());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(binding.container.getId(), new SettingsFragment());
        transaction.commit();
        setOnBack();
    }

    private void setOnBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                MaterialAlertDialogBuilder mBuilder = new MaterialAlertDialogBuilder(SettingActivity.this, R.style.MaterialDialog);
                mBuilder.setMessage(R.string.do_you_want_to_go_back_to_the_home_screen);
                mBuilder.setNegativeButton(getText(R.string.no), (dialog, which) -> dialog.dismiss());
                mBuilder.setPositiveButton(getText(R.string.yes), (dialog, which) -> finish());
                mBuilder.setCancelable(false);
                mBuilder.show();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.settings, rootKey);

            final String url = "https://github.com/magicstudiogames/AdvertsManager";
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

}
