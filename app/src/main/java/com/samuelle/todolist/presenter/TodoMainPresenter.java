package com.samuelle.todolist.presenter;

import com.samuelle.todolist.model.Todo;
import com.samuelle.todolist.model.TodoDao;
import com.samuelle.todolist.view.TodoMainActivity;

import java.util.List;

public class TodoMainPresenter {
    private TodoMainActivity view;
    private TodoDao dao;

    public TodoMainPresenter(TodoMainActivity view) {
        this.view = view;
        this.dao = view.getDao();
    }

    public List<Todo> getTodoList() {
        return this.dao.getAll();
    }

    public void navigateToAddTask() {
        view.startTodoAddTaskActivity();
    }

    public void navigateToEditTask(int id) {
        view.startTodoEditTaskActivity(id);
    }

    public void onCheckBoxClicked(int id, boolean isChecked) {
        Todo todo = getTodoById(id);
        todo.setIsDone(isChecked);
        dao.update(todo);
    }

    public Todo getTodoById(int id) {
        return dao.getTodo(id);
    }
}
