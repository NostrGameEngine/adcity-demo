package org.ngengine.platform;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import org.ngengine.demo.AdcDemo.R;

public class AndroidLauncherActivity extends AppCompatActivity {

    private AndroidLauncherFragment launcherFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_launcher);

        launcherFragment = new AndroidLauncherFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, launcherFragment)
                .commit();
    }


}