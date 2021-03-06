package com.example.azkar.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.azkar.Activity.AzkarActivity;
import com.example.azkar.Adapter.AdapterClass;
import com.example.azkar.Moudle.Item;
import com.example.azkar.R;
import com.example.azkar.Utils.OnRecycleViewItemClickListner;
import com.example.azkar.database.MyDataBase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Favourite_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Favourite_Fragment extends Fragment {
    RecyclerView recycle;
    ArrayList<Item> list;
    AdapterClass adapterClass;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Favourite_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favourite_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Favourite_Fragment newInstance(String param1, String param2) {
        Favourite_Fragment fragment = new Favourite_Fragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite_, container, false);
        MyDataBase dataBase = new MyDataBase(view.getContext());
        recycle =  view.findViewById(R.id.recycle);
        list = dataBase.GET_ALL_FAVORITE();
        adapterClass = new AdapterClass(list, new OnRecycleViewItemClickListner() {
            @Override
            public void onItemClick(Item item) {
                Intent intent = new Intent(view.getContext(), AzkarActivity.class);
                intent.putExtra("item", item);
                startActivity(intent);
            }
        });
        recycle.setAdapter(adapterClass);
        recycle.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        return view;
    }
}