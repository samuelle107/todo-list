package com.samuelle.todolist.view;

import com.samuelle.todolist.model.TodoDao;
import java.util.Calendar;
import java.util.Date;

public interface TodoAddTaskActivityView {
    void startTodoMainActivity();
    TodoDao getDao();
    void updateDateView(Calendar calendar);
}
