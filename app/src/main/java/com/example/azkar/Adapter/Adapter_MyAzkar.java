package com.example.azkar.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azkar.Moudle.MyAZKARMO;
import com.example.azkar.R;
import com.example.azkar.Utils.OnRecycleViewItemClickListner_For_MyAzkar;
import com.example.azkar.database.MyDataBase;

import java.util.ArrayList;
import java.util.List;

public class Adapter_MyAzkar extends RecyclerView.Adapter<Adapter_MyAzkar.ViewHolderClass> {
    ArrayList<MyAZKARMO> azkarArrayList;
    MyDataBase dataBase;
    int isFinish = -1;
    Vibrator vibrator;
    OnRecycleViewItemClickListner_For_MyAzkar click;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context activity;

    public Adapter_MyAzkar(ArrayList<MyAZKARMO> azkarArrayList, Context activity) {
        this.azkarArrayList = azkarArrayList;
        this.activity = activity;
    }

    public List<MyAZKARMO> getCars() {
        return azkarArrayList;
    }

    public void setCars(ArrayList<MyAZKARMO> cars) {
        this.azkarArrayList = cars;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_my_azkar, null, false);
        dataBase = new MyDataBase(v.getContext());
        ViewHolderClass holderClass = new ViewHolderClass(v);
        sharedPreferences = v.getContext().getSharedPreferences("email", v.getContext().MODE_PRIVATE);
        vibrator = (Vibrator) v.getContext().getSystemService(v.getContext().VIBRATOR_SERVICE);
        editor = sharedPreferences.edit();
        return holderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, final int position) {
        int textSizeOnFile = sharedPreferences.getInt("seekSize", 20);
        MyAZKARMO item = azkarArrayList.get(position);
        holder.Title.setText(item.title);
        holder.Text.setText(item.text);
        holder.Description.setText(item.description);
        holder.EndText.setText(item.endText);
        holder.textcount.setText(position + 1 + "");
        holder.textClick.setText(item.countminus + "");

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
                    click.Interface_Recycle_Swip_Next(item, position);
                }
            }
        });
        holder.ResetClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.countminus = item.count;
                dataBase.UPDATE_My_Azkar_Object(item);
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
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogUpdate(v.getContext(), item);
            }
        });
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Accept(v.getContext(), item);
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

    public void UpdateItem(final int position) {
        MyAZKARMO itemm = azkarArrayList.get(position);
        ShowDialogUpdate(activity, itemm);
    }

    public void removeItem(final int position) {
        MyAZKARMO itemm = azkarArrayList.get(position);
        Dialog_Accept(activity, itemm);
    }

    @Override
    public int getItemCount() {
        return azkarArrayList.size();
    }

    public void onClickListener(OnRecycleViewItemClickListner_For_MyAzkar click) {
        this.click = click;
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        TextView Title, Text, Description, EndText, textcount, textClick;
        ImageView ResetClick, Delete, update;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.Title_Myazkar);
            Text = itemView.findViewById(R.id.Text_Myazkar);
            Description = itemView.findViewById(R.id.Discription_Myazkar);
            EndText = itemView.findViewById(R.id.Endtext_Myazkar);
            textcount = itemView.findViewById(R.id.textcount_Myazkar);
            textClick = itemView.findViewById(R.id.textClick_Myazkar);
            ResetClick = itemView.findViewById(R.id.ResetClick_Myazkar);
            Delete = itemView.findViewById(R.id.delete_Myazkar);
            update = itemView.findViewById(R.id.Update_Myazkar);
        }
    }

    public void Viberation() {
        boolean ischeckedViberation = sharedPreferences.getBoolean("ischecked", false);
        if (ischeckedViberation) {
            vibrator.vibrate(300);
        } else {
            vibrator.cancel();
        }
    }

    public void ShowDialogUpdate(Context context, MyAZKARMO azkarmo) {
        dataBase = new MyDataBase(context);
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout);
        EditText title = dialog.findViewById(R.id.title_dialog);
        EditText text = dialog.findViewById(R.id.text_dialog);
        EditText description = dialog.findViewById(R.id.description_dialog);
        EditText endtext = dialog.findViewById(R.id.endText_dialog);
        EditText count = dialog.findViewById(R.id.txtcount_dialog);
        Button sub = dialog.findViewById(R.id.btnSubmit_dialog);
        Button cancle = dialog.findViewById(R.id.btn_cancle);
        sub.setText("تعديل");
        title.setText(azkarmo.title.toString());
        text.setText(azkarmo.text.toString());
        description.setText(azkarmo.description.toString());
        endtext.setText(azkarmo.endText.toString());
        count.setText(azkarmo.count + "");
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
                    int id = azkarmo.id;
                    MyAZKARMO myAZKARMO = new MyAZKARMO(id, title.getText().toString(), text.getText().toString(), description.getText().toString(), endtext.getText().toString(), Integer.parseInt(count.getText().toString()), Integer.parseInt(count.getText().toString()));
                    dataBase.UPDATE_My_Azkar_Object(myAZKARMO);
                    ArrayList<MyAZKARMO> newList = dataBase.GET_All_My_Azkar();
                    setCars(newList);
                    notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ArrayList<MyAZKARMO> newList = dataBase.GET_All_My_Azkar();
                setCars(newList);
                notifyDataSetChanged();
            }
        });
        dialog.show();
    }

    public void Dialog_Accept(Context context, MyAZKARMO item) {
        AlertDialog.Builder bu = new AlertDialog.Builder(context);
        bu.setTitle("تأكيد الحذف")
                .setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = item.id;
                        dataBase.DELETE_My_Azkar_Object(id);
                        ArrayList<MyAZKARMO> newList = dataBase.GET_All_My_Azkar();
                        setCars(newList);
                        notifyDataSetChanged();
                    }
                }).setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ArrayList<MyAZKARMO> newList = dataBase.GET_All_My_Azkar();
                setCars(newList);
                notifyDataSetChanged();
            }
        });
        AlertDialog alertDialog = bu.create();
        alertDialog.show();
    }
}
