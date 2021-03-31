package com.cleanup.todoc.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.ToDoDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    // FOR DATA
    private ToDoDatabase database;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                ToDoDatabase.class)
                .allowMainThreadQueries()
                .build();
        this.database.projectDao().createProject(PROJECT_DEMO);
    }

    @After
    public void closeDb() {
        database.close();
    }

    // DATA SET FOR TEST
    private static long PROJECT_ID = 1;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "test", 0xFFEADAD1);
    private static long TASK_ID = 1;
    private static Task TASK_DEMO = new Task(TASK_ID,1,"test", 1583658000);

    @Test
    public void insertAndGetTaskAndDelete() throws InterruptedException {
        // BEFORE : Adding a new task
        this.database.taskDao().insertTask(TASK_DEMO);
        // TEST
        Task task = LiveDataTestUtil.getValue(this.database.taskDao().getTask(TASK_ID));
        assertTrue(task.getName().equals(TASK_DEMO.getName())
                && task.getId() == TASK_ID
                && task.getProjectId() == PROJECT_ID
                && task.getCreationTimestamp() == TASK_DEMO.getCreationTimestamp());

        this.database.taskDao().deleteTask(TASK_DEMO);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertFalse(tasks.contains(TASK_DEMO));

    }
}
