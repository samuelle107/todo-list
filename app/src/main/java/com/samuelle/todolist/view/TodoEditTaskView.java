package com.samuelle.todolist.view;

import com.samuelle.todolist.model.TodoDao;

public interface TodoEditTaskView {
    TodoDao getDao();
    void updateView(String title, String note);
    void startTodoMainActivity();
}