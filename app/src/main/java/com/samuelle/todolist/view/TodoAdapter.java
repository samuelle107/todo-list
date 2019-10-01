package com.samuelle.todolist.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samuelle.todolist.R;
import com.samuelle.todolist.model.Todo;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoHolder> {
    private Context context;
    private List<Todo> todoList;
    private OnItemListener onItemListener;

    public TodoAdapter(Context context, List<Todo> todoList, OnItemListener onItemListener) {
        this.context = context;
        this.todoList = todoList;
        this.onItemListener = onItemListener;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.setDetails(todo);
    }

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.todo_item, parent, false);

        return new TodoHolder(view, onItemListener);
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}
