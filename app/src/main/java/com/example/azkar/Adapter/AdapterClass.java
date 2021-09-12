package com.example.azkar.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azkar.Moudle.Item;
import com.example.azkar.R;
import com.example.azkar.Utils.OnRecycleViewItemClickListner;
import com.example.azkar.database.MyDataBase;

import java.util.ArrayList;
import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolderClass> {
    ArrayList<Item> items;
    OnRecycleViewItemClickListner listner;
    View view;
    MyDataBase dataBase;

    public AdapterClass(ArrayList<Item> items, OnRecycleViewItemClickListner listner)
    {
        this.items = items;
        this.listner = listner;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, null, false);
        view = v;
        dataBase = new MyDataBase(v.getContext());
        ViewHolderClass holderClass = new ViewHolderClass(v);
        return holderClass;
    }






    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        Item item = items.get(position);
        holder.Text.setText(item.text);
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    item.favorite = 1;
                    dataBase.UPDATE_FAVERUITE(1,item.id);
                } else {
                    item.favorite = 0;
                    dataBase.UPDATE_FAVERUITE(0,item.id);
                }
            }
        });
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.checkbox_anim);
                holder.check.startAnimation(animation); }
        });
        holder.Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onItemClick(item);
            }
        });
        if (item.favorite == 1) {
            holder.check.setChecked(true);
        } else if (item.favorite == 0) {
            holder.check.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        TextView Text;
        CheckBox check;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            Text = itemView.findViewById(R.id.text);
            check = itemView.findViewById(R.id.favorite);

        }
    }

}
