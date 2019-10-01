package com.samuelle.todolist.base;

import android.app.Application;

import androidx.room.Room;

import com.samuelle.todolist.model.AppDatabase;

public class BaseApplication extends Application {

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room
                .databaseBuilder(
                        getApplicationContext(),
                        AppDatabase.class,
                        "todo-database")
                .allowMainThreadQueries()
                .build();
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
