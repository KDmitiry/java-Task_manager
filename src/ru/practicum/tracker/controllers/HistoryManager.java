package ru.practicum.tracker.controllers;

import ru.practicum.tracker.model.Task;

import java.util.List;

public interface HistoryManager {

    void remove(Integer id);

    List<Task> getHistory();

    void addTask(Task task);


}
