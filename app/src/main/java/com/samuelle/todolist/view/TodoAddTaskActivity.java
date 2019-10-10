package com.samuelle.todolist.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.samuelle.todolist.R;
import com.samuelle.todolist.base.BaseApplication;
import com.samuelle.todolist.model.TodoDao;
import com.samuelle.todolist.presenter.TodoAddTaskPresenter;
import com.samuelle.todolist.receiver.AlertReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TodoAddTaskActivity extends AppCompatActivity implements TodoAddTaskActivityView {
    private TodoAddTaskPresenter presenter;
    private ImageButton datePickerButton;
    private ImageButton backButton;
    private TextView datePickerText;
    private TodoDao dao;
    private TimePicker timePicker;
    private Button addTodoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_add_task_activity);

        this.dao = ((BaseApplication) getApplication()).getDatabase().todoDao();
        this.presenter = new TodoAddTaskPresenter(this);
        this.datePickerText = findViewById(R.id.datePickerText);
        this.datePickerButton = findViewById(R.id.datePickerButton);
        this.timePicker = findViewById(R.id.timePicker);
        this.addTodoButton = findViewById(R.id.addTodoButton);
        this.backButton = findViewById(R.id.backButtonAdd);

        final Calendar calendar = Calendar.getInstance();
        updateDateView(calendar);

        final DatePickerDialog.OnDateSetListener currentDate = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateView(calendar);
        };

        datePickerButton.setOnClickListener(v -> {
            new DatePickerDialog(
                    this,
                    currentDate,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH))
                    .show();
        });

        timePicker.setOnTimeChangedListener((view, hour, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
        });

        addTodoButton.setOnClickListener(v -> {
            String title = ((EditText) findViewById(R.id.todoEditTitle)).getText().toString();
            String note = ((EditText) findViewById(R.id.todoEditNote)).getText().toString();

            presenter.onTodoAddClicked(title, note, calendar.getTimeInMillis());
        });

        backButton.setOnClickListener(v -> {
            presenter.onBackPress();
        });
    }

    @Override
    public void startTodoMainActivity() {
        Intent intent = new Intent(this, TodoMainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void updateDateView(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM");
        datePickerText.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @Override
    public TodoDao getDao() {
        return dao;
    }
}
