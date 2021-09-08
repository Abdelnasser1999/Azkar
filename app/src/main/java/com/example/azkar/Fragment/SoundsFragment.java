package com.example.azkar.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.azkar.Adapter.soundAdapter;
import com.example.azkar.Moudle.soundModel;
import com.example.azkar.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SoundsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoundsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView mRecyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;

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

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("video");

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<soundModel> options =
                new FirebaseRecyclerOptions.Builder<soundModel>()
                        .setQuery(reference, soundModel.class)
                        .build();

        FirebaseRecyclerAdapter<soundModel, soundAdapter> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<soundModel, soundAdapter>(options) {
                    @NonNull
                    @NotNull
                    @Override
                    public soundAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.sound_row,parent,false);

                        return new soundAdapter(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull soundAdapter holder, int position, @NonNull @NotNull soundModel model) {
                        holder.setMedia(getActivity().getApplication(), model.getTitle(),model.getUrl());
                    }
                };
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sounds, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rec_sound);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplication()));

        return v;
    }
}