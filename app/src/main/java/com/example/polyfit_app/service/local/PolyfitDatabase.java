package com.example.polyfit_app.service.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.polyfit_app.model.Converters;
import com.example.polyfit_app.model.Reminder;
import com.example.polyfit_app.model.Routine;
import com.example.polyfit_app.model.StepCount;
import com.example.polyfit_app.model.User;
import com.example.polyfit_app.service.local.DAO.ReminderDAO;
import com.example.polyfit_app.service.local.DAO.RoutineDAO;
import com.example.polyfit_app.service.local.DAO.StepDAO;
import com.example.polyfit_app.service.local.DAO.UserDAO;

@Database(entities = {User.class, Reminder.class, StepCount.class, Routine.class}, version = 13)
@TypeConverters({Converters.class})
public abstract class PolyfitDatabase extends RoomDatabase {
    private static final String DB_NAME = "polyfit_db";
    private static PolyfitDatabase instance;

    public static synchronized PolyfitDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), PolyfitDatabase.class, DB_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build();
        }

        return instance;
    }

    public abstract UserDAO userDAO();
    public abstract ReminderDAO reminderDAO();
    public abstract RoutineDAO routineDAO();
    public abstract StepDAO stepDAO();
}
