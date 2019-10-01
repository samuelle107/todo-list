package com.samuelle.todolist.view;

import com.samuelle.todolist.model.TodoDao;

public interface TodoMainActivityView {
    void startTodoAddTaskActivity();
    void startTodoEditTaskActivity(int position);
    TodoDao getDao();
}