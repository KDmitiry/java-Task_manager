package ru.practicum.Tracker.controllers;

import ru.practicum.Tracker.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return history;
    }

    //Проверка количества объектов
    @Override
    public void addTask(Task task) {
        if (Objects.isNull(task)) {
            return;
        }
        history.add(task);
        if (history.size() > 10) {
            history.remove(0);
        }

    }
}
