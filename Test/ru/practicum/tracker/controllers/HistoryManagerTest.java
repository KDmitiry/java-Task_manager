package ru.practicum.tracker.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.tracker.model.Epic;
import ru.practicum.tracker.model.Subtask;
import ru.practicum.tracker.model.Task;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practicum.tracker.model.TaskStatus.NEW;

class HistoryManagerTest {

    private HistoryManager historyManager;

    private Task task;
    private Epic epic;
    private Subtask subtask;

    @BeforeEach
    void init() {
        historyManager = new InMemoryHistoryManager();

        task =  new Task(null, "task1", "task1-1", NEW,
                LocalDateTime.of(2022, Month.MAY, 17, 12, 20), 1);
        task.setId(1);

        epic = new Epic(null, "epic1", "epic1-1", NEW,
                LocalDateTime.of(2022, Month.MAY, 17, 12, 20), 10);
        epic.setId(2);

        subtask =  new Subtask(null, "subtask1", "subtask1-1", NEW, epic.getId(),
                LocalDateTime.of(2022, Month.MAY, 15, 12, 20), 24);
        subtask.setId(3);

    }
    @AfterEach
    void clear() {
        historyManager = null;
    }

    @Test
    void shouldMethodAddHistory() {
        historyManager.addTask(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История пустая.");
        assertEquals(1, history.size(), "История пустая.");
    }

    @Test
    void shouldMethodForEmptyListTask() {
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не возвращается.");
        assertEquals(0, history.size(), "История не пустая.");
    }
    @Test
    void shouldMethodForDuplicationTasks() {
        historyManager.addTask(task);
        historyManager.addTask(task);

        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не возвращается.");
        assertEquals(1, history.size(), "Неверное количество задач в истории.");
    }
    @Test
    void testRemoveFirst() {
        historyManager.addTask(task);
        historyManager.addTask(epic);
        historyManager.addTask(subtask);

        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не возвращается.");
        assertEquals(3, history.size(), "Неверное количество задач в истории.");

        historyManager.remove(subtask.getId());
        history = historyManager.getHistory();
        assertNotNull(history, "История не возвращается.");
        assertEquals(2, history.size(), "Неверное количество задач в истории.");
    }
    @Test
    void testRemoveMiddle() {
        historyManager.addTask(task);
        historyManager.addTask(epic);
        historyManager.addTask(subtask);

        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не возвращается.");
        assertEquals(3, history.size(), "Неверное количество задач в истории.");

        historyManager.remove(epic.getId());
        history = historyManager.getHistory();
        assertNotNull(history, "История не возвращается.");
        assertEquals(2, history.size(), "Неверное количество задач в истории.");
    }
    @Test
    void testRemoveLast() {
        historyManager.addTask(task);
        historyManager.addTask(epic);
        historyManager.addTask(subtask);

        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не возвращается.");
        assertEquals(3, history.size(), "Неверное количество задач в истории.");

        historyManager.remove(task.getId());
        history = historyManager.getHistory();
        assertNotNull(history, "История не возвращается.");
        assertEquals(2, history.size(), "Неверное количество задач в истории.");
    }


}