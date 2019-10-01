package com.samuelle.todolist.presenter;


import com.samuelle.todolist.model.Todo;
import com.samuelle.todolist.model.TodoDao;
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
            dao.updateAll(new Todo(todo.getId(), title, note, date));
            view.startTodoMainActivity();
        }
    }

    private String getFormattedDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM");

        return simpleDateFormat.format(todo.getDate());
    }
}
