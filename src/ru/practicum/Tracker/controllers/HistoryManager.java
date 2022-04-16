package ru.practicum.Tracker.controllers;

import ru.practicum.Tracker.model.Task;

public interface HistoryManager {

    int getHistory();

    void addTask(Task task);


}
