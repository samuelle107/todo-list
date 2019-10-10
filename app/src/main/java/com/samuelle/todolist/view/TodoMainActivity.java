package com.samuelle.todolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.samuelle.todolist.R;
import com.samuelle.todolist.base.BaseApplication;
import com.samuelle.todolist.model.Todo;
import com.samuelle.todolist.model.TodoDao;
import com.samuelle.todolist.presenter.TodoMainPresenter;
import com.samuelle.todolist.receiver.AlertReceiver;

import java.util.GregorianCalendar;
import java.util.List;

public class TodoMainActivity extends AppCompatActivity implements TodoMainActivityView, TodoAdapter.OnItemListener {
    private TodoMainPresenter presenter;
    private RecyclerView recyclerView;
    private TodoAdapter adapter;
    private List<Todo> todoList;
    private TodoDao dao;
    private FloatingActionButton addTodoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_main_activity);
        addTodoButton = findViewById(R.id.addTodoFloatingButton);
        dao = ((BaseApplication) getApplication()).getDatabase().todoDao();
        presenter = new TodoMainPresenter(this);

        recyclerView = findViewById(R.id.todoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        todoList = presenter.getTodoList();
        adapter = new TodoAdapter(this, todoList, this);

        recyclerView.setAdapter(adapter);

        addTodoButton.setOnClickListener(v -> {
            presenter.navigateToAddTask();
        });
    }

    @Override
    public void startTodoAddTaskActivity() {
        Intent intent = new Intent(this, TodoAddTaskActivity.class);
        startActivity(intent);
    }

    @Override
    public void startTodoEditTaskActivity(int id) {
        Intent intent = new Intent(this, TodoEditTaskActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        int id = todoList.get(position).getId();

        presenter.navigateToEditTask(id);
    }

    @Override
    public void onCheckBoxClick(int position, boolean isChecked) {
        int id = todoList.get(position).getId();

        presenter.onCheckBoxClicked(id, isChecked);
    }

    @Override
    public TodoDao getDao() {
        return this.dao;
    }

    public void sendNotification(View view) {
        long alertTime = new GregorianCalendar().getTimeInMillis() + 5 * 1000;

        Intent alertIntent = new Intent(this, AlertReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                alertTime,
                PendingIntent.getBroadcast(
                        this,
                        1,
                        alertIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT));
    }
}
