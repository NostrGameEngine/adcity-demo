package org.ngengine.platform;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.ngengine.app.Main;
import org.ngengine.platform.android.AndroidThreadedPlatform;


public class AndroidLauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getResources().getIdentifier("activity_android_launcher", "layout", getPackageName());
        setContentView(layoutId);

        int containerId = getResources().getIdentifier("fragment_container", "id", getPackageName());
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerId, new AndroidLauncherFragment(Main::main, AndroidThreadedPlatform::new))
                .commit();
    }


}