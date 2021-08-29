package com.example.azkar.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.azkar.Adapter.Adapter_MyAzkar;
import com.example.azkar.Moudle.MyAZKARMO;
import com.example.azkar.R;
import com.example.azkar.Utils.OnRecycleViewItemClickListner_For_MyAzkar;
import com.example.azkar.database.MyDataBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyAzkarFragment extends Fragment {
    Adapter_MyAzkar adapter;
    FloatingActionButton floating;
    RecyclerView recycle;
    ArrayList<MyAZKARMO> list;
    Dialog dialog;
    MyDataBase dataBase;
//    Bitmap icon;
//    private Paint p = new Paint();

    private static final String GetItemBundle = "param1";

    private MyAZKARMO AzkarArray;

    public MyAzkarFragment() {
        // Required empty public constructor
    }

    public static MyAzkarFragment newInstance(MyAZKARMO param1) {
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
            AzkarArray = (MyAZKARMO) getArguments().getSerializable(GetItemBundle);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_azkar, container, false);
        MyDataBase dataBase = new MyDataBase(view.getContext());
        floating = (FloatingActionButton) view.findViewById(R.id.floating);
        recycle = (RecyclerView) view.findViewById(R.id.recycle);
        list = dataBase.GET_All_My_Azkar();
        adapter = new Adapter_MyAzkar(list, view.getContext());
        recycle.setAdapter(adapter);
        recycle.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        adapter.onClickListener(new OnRecycleViewItemClickListner_For_MyAzkar() {
            @Override
            public void Interface_Recycle_Swip_Next(MyAZKARMO azkarmo, int position) {
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
                    dataBase.INSERT_My_Azkar(new MyAZKARMO(title.getText().toString(), text.getText().toString(), description.getText().toString(), endtext.getText().toString(), Integer.parseInt(count.getText().toString()), Integer.parseInt(count.getText().toString())));
                    list = dataBase.GET_All_My_Azkar();
                    adapter.setCars(list);
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

//    private void initSwipe() {
//        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                final int position = viewHolder.getAdapterPosition();
//                if (direction == ItemTouchHelper.LEFT) {
//                    adapter.removeItem(position);
//                }
//                if (direction == ItemTouchHelper.RIGHT) {
//                    adapter.UpdateItem(position);
//                }
//            }
//
//            @Override
//            public void onChildDraw(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//                    View itemView = viewHolder.itemView;
//                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
//                    float width = height / 3;
//                    if (dX > 0) {
//                            p.setColor(Color.parseColor("#D4D1D1"));
//                            RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
//                            c.drawRect(background,p);
//                            icon = BitmapFactory.decodeResource(getResources(), R.drawable.pen);
//                            RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
//                            c.drawBitmap(icon,null,icon_dest,p);
//                    } else {
//                        p.setColor(Color.parseColor("#D4D1D1"));
//                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
//                        c.drawRect(background, p);
//                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.trash);
//                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
//                        c.drawBitmap(icon, null, icon_dest, p);
//                    }
//                }
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//            }
//        };
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
//        itemTouchHelper.attachToRecyclerView(recycle);
//    }
}