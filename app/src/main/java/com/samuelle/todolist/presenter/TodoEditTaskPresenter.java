package com.samuelle.todolist.presenter;


import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.samuelle.todolist.model.Todo;
import com.samuelle.todolist.model.TodoDao;
import com.samuelle.todolist.receiver.AlertReceiver;
import com.samuelle.todolist.view.TodoEditTaskActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TodoEditTaskPresenter {
    private TodoEditTaskActivity view;
    private TodoDao dao;
    private Todo todo;

    public TodoEditTaskPresenter(TodoEditTaskActivity view, int id) {
        this.view = view;
        this.dao = view.getDao();
        this.todo = getTodoById(id);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(todo.getDate());

        view.updateView(
                todo.getTitle(),
                todo.getNote(),
                getFormattedDate(),
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE));
    }

    public void onTodoDeleteClicked() {
        dao.delete(todo);

        PendingIntent pendingIntent = getPendingIntent();

        AlarmManager alarmManager = (AlarmManager) view.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        view.startTodoMainActivity();
    }

    private Todo getTodoById(int id) {
        return dao.getTodo(id);
    }

    public void onBackPress() {
        view.startTodoMainActivity();
    }

    public void onUpdatePress(String title, String note, long date) {
        if (!title.isEmpty()) {
            dao.update(new Todo(todo.getId(), title, note, date));

            PendingIntent pendingIntent = getPendingIntent();

            Intent alertIntent = new Intent(view, AlertReceiver.class);
            alertIntent.putExtra("msg", title);
            alertIntent.putExtra("msgTxt", note);
            alertIntent.putExtra("msgAlert", title);
            alertIntent.putExtra("id", todo.getId());

            AlarmManager alarmManager = (AlarmManager) view.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    date,
                    PendingIntent.getBroadcast(
                            view,
                            1,
                            alertIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT));

            view.startTodoMainActivity();
        }
    }

    private String getFormattedDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM");

        return simpleDateFormat.format(todo.getDate());
    }

    private PendingIntent getPendingIntent() {
        Intent alertIntent = new Intent(view, AlertReceiver.class);
        alertIntent.putExtra("msg", todo.getTitle());
        alertIntent.putExtra("msgTxt", todo.getNote());
        alertIntent.putExtra("msgAlert", todo.getTitle());
        alertIntent.putExtra("id", todo.getId());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                view,
                1,
                alertIntent,
                0);

        return pendingIntent;
    }
}
