package ru.practicum.Tracker.controllers;

import ru.practicum.Tracker.model.Epic;
import ru.practicum.Tracker.model.Subtask;
import ru.practicum.Tracker.model.Task;
import ru.practicum.Tracker.model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryTaskManager implements TaskManager {

    private final HistoryManager historyManager = Managers.getDefaultHistory();


    private Map<Integer, Task> tasks = new HashMap<>();
    private int idCounter = 0;

    private int generateNewId() {
        return idCounter++;
    }

    @Override
    public Task getTaskById(int id) {
        if (!tasks.containsKey(id)) {
            System.out.println("Задачи с таким id  не найдено!");

        }
        return tasks.get(id);
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        List<Subtask> subtasks = getAllSubtasksOfEpicByEpicId(epic.getId());
        Map<TaskStatus, Integer> statusCounter = new HashMap<>();
        int allSubtasksCount = 0;
        for (Subtask subtask : subtasks) {
            Integer statusCount = statusCounter.getOrDefault(subtask.getStatus(), 0);
            statusCounter.put(subtask.getStatus(), statusCount + 1);
            allSubtasksCount += 1;
        }
        if (allSubtasksCount == 0 || statusCounter.getOrDefault(TaskStatus.NEW, 0) == allSubtasksCount) {
            epic.setStatus(TaskStatus.NEW);
        } else if (statusCounter.getOrDefault(TaskStatus.DONE, 0) == allSubtasksCount) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public Integer createNewTask(Task task) {

        if (task.getId() != null) {
            throw new IllegalArgumentException("");
        }
        Integer id = generateNewId();

        if (task.getTaskType().equals("subtask")) {
            Subtask subtask = (Subtask) task;
            int epicId = subtask.getEpicId();
            if (!tasks.containsKey(epicId)) {
                throw new IllegalArgumentException("");
            }
            subtask.setId(id);
            tasks.put(subtask.getId(), subtask);
            Epic epic = (Epic) tasks.get(epicId);
            List<Integer> subTasks = epic.getSubTasks();
            subTasks.add(subtask.getId());
            updateEpicStatus(epic);
        } else {
            task.setId(id);
            tasks.put(task.getId(), task);
        }
        historyManager.addTask(task);
        return id;
    }

    @Override
    public void updateNewTask(Task task) {
        if (task.getId() == null) {
            System.out.println("Ошибка! Передана задача с пустым id.");
            return;
        }
        if (!tasks.containsKey(task.getId())) {
            System.out.println("Ошибка! Передана несуществующая задача.");
            return;
        }
        if (task.getTaskType().equals("subtask")) {
            Subtask subtask = (Subtask) task;
            int epicId = subtask.getEpicId();
            if (!tasks.containsKey(epicId)) {
                System.out.println("Ошибка! Передан epicId которого нет в базе.");
            }
            tasks.put(subtask.getId(), subtask);
            Epic epic = (Epic) tasks.get(epicId);
            updateEpicStatus(epic);
        } else {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void removeTaskById(int id) {
        if (!tasks.containsKey(id)) {
            System.out.println("Ошибка! Передана несуществующая задача.");
            return;
        }
        Task task = tasks.get(id);
        if (task.getTaskType().equals("subtask")) {
            Subtask subtask = (Subtask) task;
            int epicId = subtask.getEpicId();
            Epic epic = (Epic) tasks.get(epicId);
            epic.getSubTasks().remove(subtask.getId());
            updateEpicStatus(epic);
        } else if (task.getTaskType().equals("epic")) {
            Epic epic = (Epic) task;
            if (epic.getSubTasks().isEmpty()) {
                System.out.println("Ошибка! Нельзя удалить эпик с подзадачами");
                return;
            } else {
                tasks.remove(task.getId());
            }
        } else {
            tasks.remove(task.getId());
        }
        historyManager.remove(task.getId());
    }

    @Override
    public void clearAllTasks() {
        tasks.clear();
    }

    @Override
    public List<Subtask> getAllSubtasksOfEpicByEpicId(int id) {
        Task task = tasks.get(id);
        if (!task.getTaskType().equals("epic")) {
            System.out.println("Ошибка! Нельзя получить список по id задачи или подзадачи");
        }
        Epic epic = (Epic) task;
        List<Integer> subtaskIds = epic.getSubTasks();
        List<Subtask> subtasks = new ArrayList<>();
        for (Integer subtaskId : subtaskIds) {
            Subtask subtask = (Subtask) tasks.get(subtaskId);
            subtasks.add(subtask);
        }
        return subtasks;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
