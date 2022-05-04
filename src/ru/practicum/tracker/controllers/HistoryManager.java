package ru.practicum.tracker.controllers;

import ru.practicum.tracker.model.Task;

import java.util.List;

public interface HistoryManager {

    List<Task> getHistory();

    void addTask(Task task);

    void remove (int id);

}
