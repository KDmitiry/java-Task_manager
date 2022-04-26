package ru.practicum.Tracker.controllers;

import ru.practicum.Tracker.model.Task;

import java.util.List;

public interface HistoryManager {

    List<Task> getHistory();

    void addTask(Task task);

    void remove (int id);

}
