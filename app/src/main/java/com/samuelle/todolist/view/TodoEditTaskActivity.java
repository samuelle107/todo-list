package com.samuelle.todolist.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import com.samuelle.todolist.R;
import com.samuelle.todolist.base.BaseApplication;
import com.samuelle.todolist.model.TodoDao;
import com.samuelle.todolist.presenter.TodoEditTaskPresenter;

import java.util.Calendar;

public class TodoEditTaskActivity extends AppCompatActivity implements TodoEditTaskView {
    private TodoEditTaskPresenter presenter;
    private EditText title;
    private EditText note;
    private TodoDao dao;
    private Button saveButton;
    private Button deleteButton;
    private ImageButton backButton;
    private TextView datePickerText;
    private TimePicker timepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_edit_task);
        this.saveButton = findViewById(R.id.saveTodoButton);
        this.deleteButton = findViewById(R.id.deleteButton);
        this.backButton = findViewById(R.id.backButtonEdit);
        this.dao = ((BaseApplication) getApplication()).getDatabase().todoDao();
        this.title = findViewById(R.id.todoEditTitleEditScreen);
        this.note = findViewById(R.id.todoEditNoteScreen);
        this.datePickerText = findViewById(R.id.datePickerText);
        this.timepicker = findViewById(R.id.timePickerEditScreen);
        final Calendar calendar = Calendar.getInstance();

        this.presenter = new TodoEditTaskPresenter(this, getIntent().getIntExtra("id", 0));

        timepicker.setOnTimeChangedListener((view, hour, minute) -> {
            calendar.set(Calendar.HOUR, hour);
            calendar.set(Calendar.MINUTE, minute);
        });

        deleteButton.setOnClickListener(v -> {
            presenter.onTodoDeleteClicked();
        });

        saveButton.setOnClickListener(v -> {
            presenter.onUpdatePress(
                    title.getText().toString(),
                    note.getText().toString(),
                    calendar.getTimeInMillis()
            );
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
    public void updateView(String title, String note, String dateFormatted, long hour, long minute) {
        this.title.setText(title);
        this.note.setText(note);
        this.datePickerText.setText(dateFormatted);
        this.timepicker.setHour((int) hour);
        this.timepicker.setMinute((int) minute);
    }

    @Override
    public TodoDao getDao() {
        return this.dao;
    }
}
