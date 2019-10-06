package com.example.polyfit_app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.polyfit_app.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Objects;

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fab;
    Dialog dialog;
    TextView textTime;
    CheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday, everyday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_reminder);
        connectView();

    }

    private void connectView() {
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                showDialog();
                break;
            case R.id.cardSetTime:
                dialogSetTime();
                break;
            case R.id.everyday:
                ifEveryDayChecked();
                break;
            case R.id.monday:
            case R.id.tuesday:
            case R.id.wednesday:
            case R.id.thursday:
            case R.id.friday:
            case R.id.saturday:
            case R.id.sunday:
                if (everyday.isChecked()) {
                    everyday.setChecked(false);
                }
                break;
        }
    }

    private void showDialog() {
        dialog = new Dialog(ReminderActivity.this);
        dialog.setContentView(R.layout.dialog_set_reminder);
        MaterialCardView cardSetTime = dialog.findViewById(R.id.cardSetTime);
        textTime = dialog.findViewById(R.id.tv_setTime);
        monday = dialog.findViewById(R.id.monday);
        tuesday = dialog.findViewById(R.id.tuesday);
        wednesday = dialog.findViewById(R.id.wednesday);
        thursday = dialog.findViewById(R.id.thursday);
        friday = dialog.findViewById(R.id.friday);
        saturday = dialog.findViewById(R.id.saturday);
        sunday = dialog.findViewById(R.id.sunday);
        everyday = dialog.findViewById(R.id.everyday);
        everyday.setOnClickListener(this);
        monday.setOnClickListener(this);
        tuesday.setOnClickListener(this);
        wednesday.setOnClickListener(this);
        thursday.setOnClickListener(this);
        friday.setOnClickListener(this);
        saturday.setOnClickListener(this);
        sunday.setOnClickListener(this);
        cardSetTime.setOnClickListener(this);
        dialog.show();
    }

    private void dialogSetTime() {
        final Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        textTime.setText(hourOfDay + ":" + minute);
                    }
                }, hours, minutes, true);

        timePickerDialog.show();
    }

    private void ifEveryDayChecked() {
        if (monday.isChecked()) {
            monday.setChecked(false);
        }
        if (tuesday.isChecked()) {
            tuesday.setChecked(false);
        }
        if (wednesday.isChecked()) {
            wednesday.setChecked(false);
        }
        if (thursday.isChecked()) {
            thursday.setChecked(false);
        }
        if (friday.isChecked()) {
            friday.setChecked(false);
        }
        if (saturday.isChecked()) {
            saturday.setChecked(false);
        }
        if (sunday.isChecked()) {
            sunday.setChecked(false);
        }

    }
}
