package com.samuelle.todolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.samuelle.todolist.R;
import com.samuelle.todolist.base.BaseApplication;
import com.samuelle.todolist.model.Todo;
import com.samuelle.todolist.model.TodoDao;
import com.samuelle.todolist.presenter.TodoMainPresenter;
import java.util.List;

public class TodoMainActivity extends AppCompatActivity implements TodoMainActivityView, TodoAdapter.OnItemListener {
    private TodoMainPresenter presenter;
    private RecyclerView recyclerView;
    private TodoAdapter adapter;
    private List<Todo> todoList;
    private TodoDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_main_activity);
        dao = ((BaseApplication) getApplication()).getDatabase().todoDao();
        presenter = new TodoMainPresenter(this);

        recyclerView = findViewById(R.id.todoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        todoList = presenter.getTodoList();
        adapter = new TodoAdapter(this, todoList, this);

        recyclerView.setAdapter(adapter);
    }

    public void onTaskAddStart(View v) {
        presenter.navigateToAddTask();
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
    public TodoDao getDao() {
        return this.dao;
    }
}
