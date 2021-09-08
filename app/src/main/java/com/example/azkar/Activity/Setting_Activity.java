package com.example.azkar.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.azkar.R;

import java.util.Calendar;
import java.util.Date;

public class Setting_Activity extends AppCompatActivity {
    Switch switchW;
    SeekBar seek;
    TextView tvSeek;
    TextView tvFont;
    int hour, minute;
    Switch notificationSwitch;
    ImageButton minusHourMorning;
    TextView HourTimeMorning;
    ImageButton plusHourMorning;
    ImageButton minusHourNight;
    TextView HourTimeNight;
    ImageButton plusHourNight;

    Vibrator vibrator;
    Toolbar toolbar;
    Date currentTime;
    Calendar calendar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LinearLayout lineContainerTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbar = findViewById(R.id.toolbar);
        switchW = findViewById(R.id.switchW);
        seek =  findViewById(R.id.seek);
        tvSeek = findViewById(R.id.tv_seek);
        tvFont = findViewById(R.id.tv_font);
        notificationSwitch = findViewById(R.id.notification_switch);
        minusHourMorning = findViewById(R.id.minusHouremorning);
        HourTimeMorning = findViewById(R.id.HouerTimeMorning);
        plusHourMorning = findViewById(R.id.plusHouremorning);
        minusHourNight = findViewById(R.id.minusHourenight);
        HourTimeNight = findViewById(R.id.HouerTimeNight);
        plusHourNight = findViewById(R.id.plusHourenight);
        lineContainerTime = findViewById(R.id.lineContainerTime);

        calendar = Calendar.getInstance();
        currentTime = Calendar.getInstance().getTime();
        sharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        boolean ischecked_viberat = sharedPreferences.getBoolean("ischecked", false);
        boolean ischecked_notification = sharedPreferences.getBoolean("ischecked_notification", false);
        int seekbarSize = sharedPreferences.getInt("seekSize", 20);
        int morningTime = sharedPreferences.getInt("morningTime", 1);
        int nightTime = sharedPreferences.getInt("nightTime", 1);
        tvFont.setTextSize(seekbarSize);
        seek.setProgress(seekbarSize);
        tvSeek.setText(seek.getProgress() + "");
        switchW.setChecked(ischecked_viberat);
        notificationSwitch.setChecked(ischecked_notification);
        HourTimeMorning.setText(morningTime + "");
        HourTimeNight.setText(nightTime + "");

        toolbar.setTitle(R.string.setting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (notificationSwitch.isChecked()){
            lineContainerTime.setVisibility(View.VISIBLE);
        }else{
            lineContainerTime.setVisibility(View.GONE);
        }

        switchW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("ischecked", true);
                    editor.apply();
                } else {
                    editor.putBoolean("ischecked", false);
                    editor.apply();
                }
            }
        });
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSeek.setText(progress + "");
                tvFont.setTextSize(progress);
                editor.putInt("seekSize", progress);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lineContainerTime.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_to_left_animation);
                    lineContainerTime.startAnimation(animation);
                    editor.putBoolean("ischecked_notification", true);
                    editor.apply();
                } else {
                    lineContainerTime.setVisibility(View.GONE);
                    editor.putBoolean("ischecked_notification", false);
                    editor.apply();
                }
            }
        });
        minusHourMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlusMinusHoureAndMinuth(HourTimeMorning, 1, 12, "-");
                int time = Integer.parseInt(HourTimeMorning.getText().toString());
                editor.putInt("morningTime",time);
                editor.apply();
            }
        });
        plusHourMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlusMinusHoureAndMinuth(HourTimeMorning, 1, 12, "+");
                int time = Integer.parseInt(HourTimeMorning.getText().toString());
                editor.putInt("morningTime",time);
                editor.apply();
            }
        });
        minusHourNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlusMinusHoureAndMinuth(HourTimeNight, 1, 12, "-");
                int time = Integer.parseInt(HourTimeNight.getText().toString());
                editor.putInt("nightTime",time);
                editor.apply();
            }
        });
        plusHourNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlusMinusHoureAndMinuth(HourTimeNight, 1, 12, "+");
                int time = Integer.parseInt(HourTimeNight.getText().toString());
                editor.putInt("nightTime",time);
                editor.apply();
            }
        });
    }
    private void PlusMinusHoureAndMinuth(TextView textView, int minValue, int maxValue, String operation) {

        int time = Integer.parseInt(textView.getText().toString());
        if (operation.equals("+")) {
            textView.setText((time + 1) + "");
        } else if (operation.equals("-")) {
            textView.setText((time - 1) + "");
        }
        if (time == maxValue && operation.equals("+")) {
            textView.setText(minValue + "");
        } else if (time == minValue && operation.equals("-")) {
            textView.setText(maxValue + "");
        }
    }
}