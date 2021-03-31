package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;


public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    // --- GET ALL TASKS ---
    public LiveData<List<Task>> getTasks(){ return this.taskDao.getTasks(); }

    // --- CREATE TASK ---
    public void createTask(Task task){ taskDao.insertTask(task); }

    // --- DELETE TASK ---
    public void deleteTask(Task task){ taskDao.deleteTask(task); }
}
