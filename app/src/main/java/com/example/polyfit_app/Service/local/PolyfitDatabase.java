package com.example.polyfit_app.Service.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.polyfit_app.Model.Reminder;
import com.example.polyfit_app.Model.User;
import com.example.polyfit_app.Service.local.DAO.ReminderDAO;


@Database(entities = {Reminder.class, User.class}, version = 3)

public abstract class PolyfitDatabase extends RoomDatabase {
    private static final String DB_NAME = "polyfit_db";
    private static PolyfitDatabase polyfitDatabase=null;
    public abstract ReminderDAO reminderDAO();

//    public static synchronized PolyfitDatabase getInstance(Context context) {
//        if(instance == null) {
//            instance = Room.databaseBuilder(context.getApplicationContext(), PolyfitDatabase.class, DB_NAME)
//            .fallbackToDestructiveMigration()
//            .build();
//        }
//
//        return instance;
//    }
public static PolyfitDatabase getInstance(Context context) {
    if (polyfitDatabase == null) {
        polyfitDatabase = Room.databaseBuilder(context.getApplicationContext(),
                PolyfitDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }
    return polyfitDatabase;
}



}
