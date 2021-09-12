package com.example.azkar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.azkar.Fragment.Favourite_Fragment;
import com.example.azkar.Fragment.Main_Fragment;
import com.example.azkar.Fragment.MyAzkarFragment;
import com.example.azkar.Fragment.SoundsFragment;
import com.example.azkar.R;
import com.example.azkar.database.MyDataBase;
import com.google.android.material.navigation.NavigationView;
import org.jetbrains.annotations.NotNull;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    MyDataBase dataBase;
    DrawerLayout drawaer;
    ActionBarDrawerToggle toggle;
    NavigationView navigation;
    FrameLayout framlayout;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        calendar = Calendar.getInstance();
        toolbar =  findViewById(R.id.toolbar);
        navigation = findViewById(R.id.navigation);
        setSupportActionBar(toolbar);
        drawaer =  findViewById(R.id.drawaer);
        dataBase = new MyDataBase(this);
        framlayout = findViewById(R.id.framlayout);

        toggle = new ActionBarDrawerToggle(this, drawaer, R.string.open, R.string.close);
        drawaer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Main_Fragment azkarFragment = new Main_Fragment();
        Favourite_Fragment favouriteFragment = new Favourite_Fragment();
        MyAzkarFragment myAzkarFragment = new MyAzkarFragment();
        SoundsFragment soundFragment = new SoundsFragment();
        GetFragment(azkarFragment);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.azkar:
                        toolbar.setTitle(R.string.app_name);
                        GetFragment(azkarFragment);
                        drawaer.close();
                        break;
                    case R.id.favorite:
                        toolbar.setTitle(R.string.favorite);
                        GetFragment(favouriteFragment);
                        drawaer.close();
                        break;
                    case R.id.Myazkar:
                        toolbar.setTitle(R.string.myazkar);
                        GetFragment(myAzkarFragment);
                        drawaer.close();
                        break;
                    case R.id.sound:
                        toolbar.setTitle(R.string.sound);
                        GetFragment(soundFragment);
                        drawaer.close();
                        break;
                    case R.id.share:
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Azkary Application ");
                        startActivity(Intent.createChooser(sharingIntent, "ارسال التطبيق عن طريق"));
                        drawaer.close();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, Setting_Activity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                drawaer.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataBase.UPDATE_Count();
    }

    public void GetFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.framlayout, fragment);
        ft.commit();
    }

}

