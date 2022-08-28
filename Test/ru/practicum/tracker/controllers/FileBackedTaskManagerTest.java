package ru.practicum.tracker.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.tracker.model.Epic;
import ru.practicum.tracker.model.Subtask;
import ru.practicum.tracker.model.Task;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FileBackedTaskManagerTest extends TaskManagerTest{

    private static final Path testFilePath = Paths.get("./resources/test");
    @BeforeEach
    void init() {
        taskManager = new InMemoryTaskManager();
        super.init();
    }

    @Test
    public void shouldSaveAndReadStateWithEmptyTasks() {

        taskManager.clearAllSubTasks();
        taskManager.clearAllEpics();

        FileBackedTasksManager actualManager = new FileBackedTasksManager();

        List<Task> actualTasks = actualManager.getAllTasks();
        assertEquals(2, actualTasks.size(), "Неверное чтение пустого списка задач");

        List<Subtask> actualSubtasks = actualManager.getAllSubtasks();
        assertEquals(0, actualSubtasks.size(), "Неверное чтение пустого списка подзадач");

        List<Epic> actualEpics = actualManager.getAllEpics();
        assertEquals(0, actualEpics.size(), "Неверное чтение пустого списка эпиков");

        List<Task> actualHistory = actualManager.getHistory();
        assertEquals(0, actualHistory.size(), "Неверное чтение пустой истории");
    }


}