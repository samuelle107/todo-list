package com.samuelle.todolist.presenter;

import com.samuelle.todolist.model.Todo;
import com.samuelle.todolist.model.TodoDao;
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
            this.dao.insertAll(new Todo(title, note, date));
            this.view.startTodoMainActivity();
        }
    }

    public void onBackPress() {
        view.startTodoMainActivity();
    }
}
