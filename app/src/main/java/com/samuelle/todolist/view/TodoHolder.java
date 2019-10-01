package com.samuelle.todolist.view;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.samuelle.todolist.R;
import com.samuelle.todolist.model.Todo;
import java.text.SimpleDateFormat;
import com.samuelle.todolist.view.TodoAdapter.OnItemListener;

public class TodoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView title;
    private TextView date;
    private OnItemListener onItemListener;

    public TodoHolder(View itemView, OnItemListener onItemListener) {
        super(itemView);

        this.title = itemView.findViewById(R.id.todoTitle);
        this.date = itemView.findViewById(R.id.todoTime);
        this.onItemListener = onItemListener;

        itemView.setOnClickListener(this);
    }

    public void setDetails(Todo todo) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a E, MMM dd");
        String formattedDate = simpleDateFormat.format(todo.getDate());

        title.setText(todo.getTitle());
        date.setText(formattedDate);
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition());
    }
}
