package ru.practicum.Tracker.controllers;

import ru.practicum.Tracker.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history = new ArrayList<>();

    @Override
    public int getHistory() {
        return history.size();
    }

    //Проверка количества объектов
    @Override
    public void addTask(Task task) {
        history.add(task);
        if (history.size() > 10) {
            history.remove(0);
        }

    }
}
