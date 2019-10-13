package com.example.polyfit_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.polyfit_app.Adapter.ReminderAdapter;
import com.example.polyfit_app.Model.Reminder;
import com.example.polyfit_app.R;

import com.example.polyfit_app.Service.local.PolyfitDatabase;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fab;
    Dialog dialog;
    TextView textTime;
    CheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday, everyday;
    TextView btnSaveAlarm;
    int hours, minutes;
    int hoursSet, minutesSet;
    RecyclerView viewReminder;
    List<Reminder> listReminder;
    ReminderAdapter reminderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_reminder);
        connectView();
        getListReminder();
    }

    private void connectView() {
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        viewReminder = findViewById(R.id.viewReminder);
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
            case R.id.btnSaveAlarm:
                saveReminder();

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
        btnSaveAlarm = dialog.findViewById(R.id.btnSaveAlarm);
        everyday.setOnClickListener(this);
        monday.setOnClickListener(this);
        tuesday.setOnClickListener(this);
        wednesday.setOnClickListener(this);
        thursday.setOnClickListener(this);
        friday.setOnClickListener(this);
        saturday.setOnClickListener(this);
        sunday.setOnClickListener(this);
        cardSetTime.setOnClickListener(this);
        btnSaveAlarm.setOnClickListener(this);
        dialog.show();
    }

    private void dialogSetTime() {
        final Calendar c = Calendar.getInstance();
        hours = c.get(Calendar.HOUR_OF_DAY);
        minutes = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        hoursSet = hourOfDay;
                        minutesSet = minute;
                        if(hourOfDay<10){
                            textTime.setText("0"+hourOfDay + ":" + minute);
                        }else if(minute<10){
                            textTime.setText(hourOfDay + ":" + "0"+minute);
                        } else {
                            textTime.setText(hourOfDay + ":" + minute);
                        }
                        if(hourOfDay<10&&minute<10) {
                            textTime.setText("0" + hourOfDay + ":" + "0" + minute);
                        }

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

    private void saveReminder() {
        Log.e("PhayTRan", "Save data");
//                saveReminder();
        Reminder reminder = new Reminder();
        reminder.setHour(hoursSet);
        reminder.setMinute(minutesSet);

        if (monday.isChecked()) {
            reminder.setMonday(1);
        } else {
            reminder.setMonday(0);
        }
        if (tuesday.isChecked()) {
            reminder.setTuesday(1);
        } else {
            reminder.setTuesday(0);
        }
        if (wednesday.isChecked()) {
            reminder.setWednesday(1);
        } else {
            reminder.setWednesday(0);
        }
        if (thursday.isChecked()) {
            reminder.setThursday(1);
        } else {
            reminder.setThursday(0);
        }
        if (friday.isChecked()) {
            reminder.setFriday(1);
        } else {
            reminder.setFriday(0);
        }
        if (saturday.isChecked()) {
            reminder.setSaturday(1);
        } else {
            reminder.setSaturday(0);
        }
        if (sunday.isChecked()) {
            reminder.setSunday(1);
        } else {
            reminder.setSunday(0);
        }
        if (everyday.isChecked()) {
            reminder.setMonday(1);
            reminder.setTuesday(1);
            reminder.setWednesday(1);
            reminder.setThursday(1);
            reminder.setFriday(1);
            reminder.setSaturday(1);
            reminder.setSunday(1);
        }
//        reminderService.insert(reminder);
        PolyfitDatabase.getInstance(ReminderActivity.this).reminderDAO().insert(reminder);
        getListReminder();

    }

    public void getListReminder() {
        listReminder = new ArrayList<>();
        listReminder = PolyfitDatabase.getInstance(ReminderActivity.this).reminderDAO().getReminder();
        viewReminder.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ReminderActivity.this, LinearLayoutManager.VERTICAL, false);
        viewReminder.setLayoutManager(layoutManager);
        reminderAdapter = new ReminderAdapter(listReminder, ReminderActivity.this);
        viewReminder.setAdapter(reminderAdapter);
    }
}