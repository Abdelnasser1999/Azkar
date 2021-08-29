package com.example.azkar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azkar.Fragment.MyAzkarFragment;
import com.example.azkar.Moudle.AZKARMoudle;
import com.example.azkar.R;
import com.example.azkar.Utils.OnRecycleViewItemClickListnerForAzkar;
import com.example.azkar.database.MyDataBase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterAzkar extends RecyclerView.Adapter<AdapterAzkar.ViewHolderClass> {
    ArrayList<AZKARMoudle> azkarArrayList;
    OnRecycleViewItemClickListnerForAzkar listner;
    MyDataBase dataBase;
    Context context;
    int isFinish = -1;
    Vibrator vibrator;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public AdapterAzkar(Context context, ArrayList<AZKARMoudle> azkarArrayList, RecyclerView recyclerView) {
        this.context = context;
        this.azkarArrayList = azkarArrayList;
        this.recyclerView = recyclerView;
    }


    public List<AZKARMoudle> getCars() {
        return azkarArrayList;
    }

    public void setCars(ArrayList<AZKARMoudle> cars) {
        this.azkarArrayList = cars;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_zeker_layout, null, false);
        dataBase = new MyDataBase(v.getContext());
        ViewHolderClass holderClass = new ViewHolderClass(v);
        sharedPreferences = v.getContext().getSharedPreferences("email", v.getContext().MODE_PRIVATE);
        vibrator = (Vibrator) v.getContext().getSystemService(v.getContext().VIBRATOR_SERVICE);
        editor = sharedPreferences.edit();
        return holderClass;
    }

    public void delete(int position) {
        azkarArrayList.remove(position);
    }

    public void listener(OnRecycleViewItemClickListnerForAzkar listner) {
        this.listner = listner;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, final int position) {
        int textSizeOnFile = sharedPreferences.getInt("seekSize", 20);
        AZKARMoudle item = azkarArrayList.get(position);
        holder.Title.setText(item.title);
        holder.Text.setText(item.text);
        holder.Description.setText(item.description);
        holder.EndText.setText(item.endText);
        holder.textcount.setText(position + 1 + "");
        holder.textClick.setText(item.countminus + "");

        ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(context, "sadf", Toast.LENGTH_SHORT).show();
            }
        };
        holder.Text.setTextSize(textSizeOnFile);
        holder.Title.setTextSize(textSizeOnFile);

        holder.textClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.countminus = item.countminus - 1;
                holder.textClick.setText(item.countminus + "");
                if (item.countminus == 0) {
                    holder.textClick.setBackgroundResource(R.drawable.btn_disaple);
                    holder.textClick.setEnabled(false);
                    Viberation();
                    Go_TO_Next(position);
                }
            }
        });
        holder.ResetClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.countminus = item.count;
                dataBase.UPDATE_CAR(item);
                if (item.countminus == 0) {
                    holder.textClick.setBackgroundResource(R.drawable.btn_disaple);
                    holder.textClick.setEnabled(false);
                } else {
                    holder.textClick.setBackgroundResource(R.drawable.tvclick);
                    holder.textClick.setEnabled(true);
                    holder.textClick.setText(item.countminus + "");
                }

            }
        });

        int text = Integer.parseInt(holder.textClick.getText().toString());
        if (text == 0) {
            holder.textClick.setBackgroundResource(R.drawable.btn_disaple);
            holder.textClick.setEnabled(false);
        } else if (isFinish != 0) {
            holder.textClick.setBackgroundResource(R.drawable.tvclick);
            holder.textClick.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return azkarArrayList.size();
    }

    public void Go_TO_Next(int position) {
        recyclerView.smoothScrollToPosition(position + 1);
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        TextView Title, Text, Description, EndText, textcount, textClick;
        ImageView ResetClick;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.Title);
            Text = itemView.findViewById(R.id.Text);
            Description = itemView.findViewById(R.id.Discription);
            EndText = itemView.findViewById(R.id.Endtext);
            textcount = itemView.findViewById(R.id.textcount);
            textClick = itemView.findViewById(R.id.textClick);
            ResetClick = itemView.findViewById(R.id.ResetClick);
        }
    }

    public void Viberation() {
        boolean ischeckedViberation = sharedPreferences.getBoolean("ischecked", false);
        if (ischeckedViberation) {
            vibrator.vibrate(500);
        } else {
            vibrator.cancel();
        }
    }

}
