package com.example.azkar.Adapter;

import android.content.Context;
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

import com.example.azkar.Activity.AzkarActivity;
import com.example.azkar.Moudle.AZKARMoudle;
import com.example.azkar.Moudle.Item;
import com.example.azkar.R;
import com.example.azkar.Utils.OnRecycleViewItemClickListner;
import com.example.azkar.database.MyDataBase;

import java.util.ArrayList;
import java.util.List;

public class AdapterNames extends RecyclerView.Adapter<AdapterNames.ViewHolderClass> {
    ArrayList<AZKARMoudle> Names;
    View view;
    MyDataBase dataBase;
    Context context;
    public AdapterNames(Context context,ArrayList<AZKARMoudle> Names) {
        this.context = context;
        this.Names = Names;
    }

    public List<AZKARMoudle> getNames() {
        return Names;
    }

    public void setNames(ArrayList<AZKARMoudle> Names) {
        this.Names = Names;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.names_of_allah, null, false);
        view = v;
        dataBase = new MyDataBase(v.getContext());
        ViewHolderClass holderClass = new ViewHolderClass(v);
        return holderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        AZKARMoudle item = Names.get(position);
        holder.Text.setText(item.text);
    }

    @Override
    public int getItemCount() {
        return Names.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        TextView Text;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            Text = itemView.findViewById(R.id.text);
        }

    }
}
