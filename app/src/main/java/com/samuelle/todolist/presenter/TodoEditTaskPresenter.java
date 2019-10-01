package com.samuelle.todolist.presenter;

import com.samuelle.todolist.model.Todo;
import com.samuelle.todolist.model.TodoDao;
import com.samuelle.todolist.view.TodoEditTaskActivity;

import java.util.Date;

public class TodoEditTaskPresenter {
    private TodoEditTaskActivity view;
    private TodoDao dao;
    private Todo todo;

    public TodoEditTaskPresenter(TodoEditTaskActivity view, int id) {
        this.view = view;
        this.dao = view.getDao();
        this.todo = getTodoById(id);
        view.updateView(todo.getTitle(), todo.getNote());
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

    public void onUpdatePress(String title, String note) {
        if (!title.isEmpty()) {
            long date = (new Date()).getTime();
            dao.updateAll(new Todo(todo.getId(), title, note, date));
            view.startTodoMainActivity();
        }
    }
}
