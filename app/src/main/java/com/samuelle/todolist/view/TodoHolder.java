package com.samuelle.todolist.view;

import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
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
    private CheckBox isDoneCheckBox;

    public TodoHolder(View itemView, OnItemListener onItemListener) {
        super(itemView);

        this.title = itemView.findViewById(R.id.todoTitle);
        this.date = itemView.findViewById(R.id.todoTime);
        this.isDoneCheckBox = itemView.findViewById(R.id.isDoneCheckBox);
        this.onItemListener = onItemListener;

        this.isDoneCheckBox.setOnClickListener(v -> {
            onItemListener.onCheckBoxClick(getAdapterPosition(), this.isDoneCheckBox.isChecked());
        });

        itemView.setOnClickListener(this);
    }

    public void setDetails(Todo todo) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a E, MMM dd");
        String formattedDate = simpleDateFormat.format(todo.getDate());

        if (todo.getIsDone()) {
            this.title.setPaintFlags(this.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            this.date.setPaintFlags(this.date.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            this.title.setPaintFlags(this.title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            this.date.setPaintFlags(this.date.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        this.title.setText(todo.getTitle());
        this.date.setText(formattedDate);
        this.isDoneCheckBox.setChecked(todo.getIsDone());
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition());
    }
}
