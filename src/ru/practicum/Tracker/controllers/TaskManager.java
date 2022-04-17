package ru.practicum.Tracker.controllers;

import ru.practicum.Tracker.model.Epic;
import ru.practicum.Tracker.model.Subtask;
import ru.practicum.Tracker.model.Task;

import java.util.List;

public interface TaskManager {

    Task getTaskById(int id);

    void updateEpicStatus(Epic epic);

    Integer createNewTask(Task task);

    void updateNewTask(Task task);

    void removeTaskById(int id);

    void clearAllTasks();

    List<Subtask> getAllSubtasksOfEpicByEpicId(int id);

    //Получение истории

    List<Task> getHistory();

}
