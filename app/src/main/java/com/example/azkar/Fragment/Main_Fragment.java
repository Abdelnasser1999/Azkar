package com.example.azkar.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.azkar.Activity.AzkarActivity;
import com.example.azkar.Adapter.AdapterClass;
import com.example.azkar.Moudle.Item;
import com.example.azkar.R;
import com.example.azkar.Utils.OnRecycleViewItemClickListner;
import com.example.azkar.database.MyDataBase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Main_Fragment extends Fragment {
    AdapterClass adapterClass;
    MyDataBase dataBase;
    RecyclerView recycle;
    ArrayList<Item> list;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Main_Fragment() {
    }

    public static Main_Fragment newInstance(String param1, String param2) {
        Main_Fragment fragment = new Main_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_azkar_, container, false);
        setHasOptionsMenu(true);
        recycle = v.findViewById(R.id.recycle);
        dataBase = new MyDataBase(v.getContext());
        list = dataBase.GET_ALL_ITEMS();
        adapterClass = new AdapterClass(list, new OnRecycleViewItemClickListner() {
            @Override
            public void onItemClick(Item item) {
                Intent intent = new Intent(v.getContext(), AzkarActivity.class);
                intent.putExtra("item", item);
                startActivity(intent);
            }
        });
        recycle.setAdapter(adapterClass);
        recycle.setLayoutManager(new GridLayoutManager(v.getContext(), 2));
        return v;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.search_bar);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Item> listQuery = dataBase.SEARCH_ITEM(newText);
                adapterClass.setItems(listQuery);
                adapterClass.notifyDataSetChanged();
                return false;
            }
        });
    }
}