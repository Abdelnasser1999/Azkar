package com.example.azkar.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.azkar.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SoundsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoundsFragment extends Fragment {
    private RecyclerView recycle;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SoundsFragment() {
        // Required empty public constructor
    }
    public static SoundsFragment newInstance(String param1, String param2) {
        SoundsFragment fragment = new SoundsFragment();
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
        View v = inflater.inflate(R.layout.fragment_sounds, container, false);
        recycle = (RecyclerView) v.findViewById(R.id.recycle);
        return v;
    }
}