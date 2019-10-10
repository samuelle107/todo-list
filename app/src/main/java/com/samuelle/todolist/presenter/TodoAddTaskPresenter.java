package com.samuelle.todolist.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.samuelle.todolist.model.Todo;
import com.samuelle.todolist.model.TodoDao;
import com.samuelle.todolist.receiver.AlertReceiver;
import com.samuelle.todolist.view.TodoAddTaskActivity;

public class TodoAddTaskPresenter {
    private TodoAddTaskActivity view;
    private TodoDao dao;

    public TodoAddTaskPresenter(TodoAddTaskActivity view) {
        this.view = view;
        this.dao = view.getDao();
    }

    public void onTodoAddClicked(String title, String note, Long date) {
        if (!title.isEmpty()) {
            long id = this.dao.insert(new Todo(title, note, date));

            Intent alertIntent = new Intent(view, AlertReceiver.class);
            alertIntent.putExtra("msg", title);
            alertIntent.putExtra("msgTxt", note);
            alertIntent.putExtra("msgAlert", title);
            alertIntent.putExtra("id", id);

            AlarmManager alarmManager = (AlarmManager) view.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    date,
                    PendingIntent.getBroadcast(
                            view,
                            1,
                            alertIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT));

            this.view.startTodoMainActivity();
        }
    }

    public void onBackPress() {
        view.startTodoMainActivity();
    }
}
