package com.example.azkar.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.azkar.Adapter.Adapter_MyAzkar;
import com.example.azkar.Moudle.myAzkar;
import com.example.azkar.R;
import com.example.azkar.Utils.OnRecycleViewItemClickListner_For_MyAzkar;
import com.example.azkar.database.MyDataBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyAzkarFragment extends Fragment {
    Adapter_MyAzkar adapter;
    FloatingActionButton floating;
    RecyclerView recycle;
    ArrayList<myAzkar> list;
    Dialog dialog;
    MyDataBase dataBase;
//    Bitmap icon;
//    private Paint p = new Paint();

    private static final String GetItemBundle = "param1";

    private myAzkar AzkarArray;

    public MyAzkarFragment() {
        // Required empty public constructor
    }

    public static MyAzkarFragment newInstance(myAzkar param1) {
        MyAzkarFragment fragment = new MyAzkarFragment();
        Bundle args = new Bundle();
        args.putSerializable(GetItemBundle, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            AzkarArray = (myAzkar) getArguments().getSerializable(GetItemBundle);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_azkar, container, false);
        MyDataBase dataBase = new MyDataBase(view.getContext());
        floating = view.findViewById(R.id.floating);
        recycle = view.findViewById(R.id.recycle);
        list = dataBase.GET_All_My_Azkar();
        adapter = new Adapter_MyAzkar(list, view.getContext());
        recycle.setAdapter(adapter);
        recycle.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        adapter.onClickListener(new OnRecycleViewItemClickListner_For_MyAzkar() {
            @Override
            public void Interface_Recycle_Swip_Next(myAzkar azkarmo, int position) {
                recycle.smoothScrollToPosition(position + 1);
            }
        });
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogAdd(v.getContext());
            }
        });

        return view;
    }

    public void ShowDialogAdd(Context context) {
        dataBase = new MyDataBase(context);
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout);
        EditText title = dialog.findViewById(R.id.title_dialog);
        EditText text = dialog.findViewById(R.id.text_dialog);
        EditText description = dialog.findViewById(R.id.description_dialog);
        EditText endtext = dialog.findViewById(R.id.endText_dialog);
        EditText count = dialog.findViewById(R.id.txtcount_dialog);
        Button sub = dialog.findViewById(R.id.btnSubmit_dialog);
        Button cancle = dialog.findViewById(R.id.btn_cancle);
        sub.setText("اضافة");
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.getText().toString().isEmpty()) {
                    text.setError("هذا الحقل مطلوب");
                } else if (count.getText().toString().isEmpty()) {
                    count.setError("هذا الحقل مطلوب");
                } else if (Integer.parseInt(count.getText().toString()) < 0) {
                    count.setError("0 على الأقل");
                } else {
                    dataBase.INSERT_My_Azkar(new myAzkar(title.getText().toString(), text.getText().toString(), description.getText().toString(), endtext.getText().toString(), Integer.parseInt(count.getText().toString()), Integer.parseInt(count.getText().toString())));
                    list = dataBase.GET_All_My_Azkar();
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}