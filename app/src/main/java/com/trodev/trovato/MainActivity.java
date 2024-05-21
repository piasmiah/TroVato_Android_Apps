package com.trodev.trovato;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;
import com.trodev.trovato.fragments.CourseFragment;
import com.trodev.trovato.fragments.EnvatoFragment;
import com.trodev.trovato.fragments.HomeFragment;
import com.trodev.trovato.fragments.ProfileFragment;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    SmoothBottomBar smoothBottomBar;

    private static final String ONESIGNAL_APP_ID = "bd87768b-7af0-4709-b66e-c50baed594b3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        /*init views*/
        smoothBottomBar = findViewById(R.id.bottombar);

        // Verbose Logging set to help debug issues, remove before releasing your app.
        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);
        OneSignal.getUser().getPushSubscription().optIn();


        /*When apps start show HomeFragments*/
        setTitle("Dashboard");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new HomeFragment());
        fragmentTransaction.commit();

        /*smooth bar working process*/
        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {

                if (i == 0) {
                    setTitle("Dashboard");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new HomeFragment());
                    fragmentTransaction.commit();

                } else if (i == 1) {
                    setTitle("Envato Elements");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new EnvatoFragment());
                    fragmentTransaction.commit();

                } else if (i == 2) {
                    setTitle("Premium Course");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new CourseFragment());
                    fragmentTransaction.commit();

                } else if (i == 3) {
                    setTitle("Profile");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new ProfileFragment());
                    fragmentTransaction.commit();
                }

                return false;
            }
        });

    }
}