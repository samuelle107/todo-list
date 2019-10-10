package com.samuelle.todolist.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM TODO")
    List<Todo> getAll();

    @Query("SELECT * FROM TODO WHERE id like :id")
    Todo getTodo(int id);

    @Insert
    long insert(Todo todo);

    @Delete
    void delete(Todo todo);

    @Update
    void update(Todo todo);
}
