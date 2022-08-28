package ru.practicum.tracker.controllers;

import ru.practicum.tracker.model.Epic;
import ru.practicum.tracker.model.Subtask;
import ru.practicum.tracker.model.Task;

import java.util.List;

public interface TaskManager {

    Task getTaskById(int id);

    void createTask(Task task);

    void createSubTask(Subtask subTask);

    void createEpic(Epic epic);

    Subtask getSubTaskById(int id);

    Epic getEpicById(int id);

    void updatedTask(Task task);

    void updatedEpic(Epic epic);

    void updatedSubTask(Subtask subTask);

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    void removeTaskById(int id);

    void removeSubTaskById(int id);

    void removeEpicById(int id);

    void clearAllTasks();

    void clearAllSubTasks();

    void clearAllEpics();

    List<Task> getHistory();

    List<Task> getPrioritizedTasks();

    List<Subtask> getListSubTasks(int id);
}
