package com.cleanup.todoc.injections;

import android.content.Context;


import com.cleanup.todoc.database.ToDoDatabase;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class Injection {

    private static TaskDataRepository provideTaskDataSource(Context context) {
        ToDoDatabase database = ToDoDatabase.getInstance(context);
        return new TaskDataRepository(database.taskDao());
    }

    private static ProjectDataRepository provideProjectDataSource(Context context) {
        ToDoDatabase database = ToDoDatabase.getInstance(context);
        return new ProjectDataRepository(database.projectDao());
    }

    private static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        TaskDataRepository dataSourceTask = provideTaskDataSource(context);
        ProjectDataRepository dataSourceProject = provideProjectDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceTask, dataSourceProject, executor);
    }
}
