package com.example.azkar.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azkar.Adapter.AdapterAzkar;
import com.example.azkar.Adapter.AdapterNames;
import com.example.azkar.Moudle.azkar;
import com.example.azkar.Moudle.Item;
import com.example.azkar.R;
import com.example.azkar.database.MyDataBase;

import java.util.ArrayList;

public class AzkarActivity extends AppCompatActivity {

    RecyclerView recycle;
    MyDataBase dataBase;
    Toolbar toolbar;
    ArrayList<azkar> azkaArrayList;
    AdapterAzkar adapter;
    Vibrator vibrator;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azkar);

        recycle = findViewById(R.id.recycle);
        toolbar = findViewById(R.id.toolbar);
        dataBase = new MyDataBase(this);
        sharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        editor = sharedPreferences.edit();
        Item item = (Item) getIntent().getSerializableExtra("item");
        if (item.id == 23) {
            GetData_ON_AdapterNames(item.id);
        } else {
            GetData_ON_AdapterAzkar(item.id);
        }
        toolbar.setTitle(item.text);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void GetData_ON_AdapterAzkar(int id_Main) {
        azkaArrayList = dataBase.GETAllFromWhere(id_Main);
        adapter = new AdapterAzkar(this, azkaArrayList,recycle);
        recycle.setAdapter(adapter);
        recycle.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }


    public void GetData_ON_AdapterNames(int id_Main) {
        azkaArrayList = dataBase.GETAllFromWhere(id_Main);
        AdapterNames adapterNames = new AdapterNames(this, azkaArrayList);
        recycle.setAdapter(adapterNames);
        recycle.setLayoutManager(new GridLayoutManager(this, 3));
    }
}