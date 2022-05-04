package ru.practicum.tracker.controllers;

import ru.practicum.tracker.model.Epic;
import ru.practicum.tracker.model.Subtask;
import ru.practicum.tracker.model.Task;

import java.util.List;

public interface TaskManager {

    Task getTaskById(int id);

    void updateEpicStatus(Epic epic);

    Integer createNewTask(Task task);

    void updateNewTask(Task task);

    void removeTaskById(int id);

    void clearAllTasks();

    List<Subtask> getAllSubtasksOfEpicByEpicId(int id);


    List<Task> getHistory();

}
