package com.samuelle.todolist.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import com.samuelle.todolist.R;
import com.samuelle.todolist.base.BaseApplication;
import com.samuelle.todolist.model.TodoDao;
import com.samuelle.todolist.presenter.TodoEditTaskPresenter;

public class TodoEditTaskActivity extends AppCompatActivity implements TodoEditTaskView {
    private TodoEditTaskPresenter presenter;
    private EditText title;
    private EditText note;
    private TodoDao dao;
    private Button saveButton;
    private Button deleteButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_edit_task);
        this.saveButton = findViewById(R.id.saveTodoButton);
        this.deleteButton = findViewById(R.id.deleteButton);
        this.backButton = findViewById(R.id.backButtonEdit);
        this.dao = ((BaseApplication) getApplication()).getDatabase().todoDao();
        this.title = findViewById(R.id.todoEditTitleEditScreen);
        this.note = findViewById(R.id.todoEditNoteEditSreen);
        this.presenter = new TodoEditTaskPresenter(this, getIntent().getIntExtra("id", 0));

        deleteButton.setOnClickListener(v -> {
            presenter.onTodoDeleteClicked();
        });

        saveButton.setOnClickListener(v -> {
            presenter.onUpdatePress(title.getText().toString(), note.getText().toString());
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
    public void updateView(String title, String note) {
        this.title.setText(title);
        this.note.setText(note);
    }

    @Override
    public TodoDao getDao() {
        return this.dao;
    }
}
