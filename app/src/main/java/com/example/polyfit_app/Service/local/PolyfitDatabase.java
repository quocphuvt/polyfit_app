package com.example.polyfit_app.Service.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.polyfit_app.Model.User;

@Database(entities = {User.class}, version = 1)

public abstract class PolyfitDatabase extends RoomDatabase {
    private static final String DB_NAME = "polyfit_db";
    private static PolyfitDatabase instance;

    public static synchronized PolyfitDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), PolyfitDatabase.class, DB_NAME)
            .fallbackToDestructiveMigration()
            .build();
        }

        return instance;
    }

    public abstract UserDAO  userDAO();
}