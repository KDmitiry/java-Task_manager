package ru.practicum.tracker.controllers;

import ru.practicum.tracker.model.*;
import java.time.LocalDateTime;
import java.util.*;


public class InMemoryTaskManager implements TaskManager {

    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private static final Map<Integer, Task> tasks = new HashMap<>();
    protected static final Map<Integer, Epic> epics = new HashMap<>();
    protected static final Map<Integer, Subtask> subtasks = new HashMap<>();
    private int idCounter = 0;
    protected final Map<LocalDateTime, Task> prioritizedTasks = new TreeMap<>();


    private int generateNewId() {
        return idCounter++;
    }

    public static Map<Integer, Task> getTasks() {
        return tasks;
    }

    public static Map<Integer, Epic> getEpic() {
        return epics;
    }

    public static Map<Integer, Subtask> getSubTask() {
        return subtasks;
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        List<Task> tasksByPriority = new ArrayList<>();
        List<Task> startTimeNotSetTasks = new ArrayList<>();
        for (Task prioritizedTask : prioritizedTasks.values()) {
            if (prioritizedTask.getDuration() == 0) {
                startTimeNotSetTasks.add(prioritizedTask);
            } else {
                tasksByPriority.add(prioritizedTask);
            }
        }
        tasksByPriority.addAll(startTimeNotSetTasks);
        return tasksByPriority;
    }

    public void setStartEndEpic(int idEpic) {
        SortedSet<LocalDateTime> startTime = new TreeSet<>();
        SortedSet<LocalDateTime> endTime = new TreeSet<>();
        List<Subtask> listSubTask = getAllSubtasks();

        for (Subtask subtask : listSubTask) {
            if (subtask.getEpicId() == idEpic) {
                startTime.add(subtask.getStartTime());
                endTime.add(subtask.getEndTime());
            }
        }
        epics.get(idEpic).setStartTime(startTime.first());
        epics.get(idEpic).setEndTimeEpic(endTime.last());
    }

    private boolean searchForATemporaryIntersection() {
        boolean result = false;
        Task previousTask = null;
        for (Task task : prioritizedTasks.values()) {
            if (previousTask == null) {
                previousTask = task;
                continue;
            }
            if (previousTask.getEndTime() == null || task.getStartTime() == null) {
                continue;
            }
            if (previousTask.getEndTime().isAfter(task.getStartTime())) {
                result = true;
                break;
            }
            previousTask = task;
        }
        return result;
    }

    @Override
    public void updatedTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            return;
        }
        searchForATemporaryIntersection();
        tasks.put(task.getId(), task);
        prioritizedTasks.put(task.getStartTime(), task);
        task.getEndTime();
    }

    @Override
    public void updatedEpic(Epic epic) {
        if (!epics.containsKey(epic.getId())) {
            return;
        }
        epics.put(epic.getId(), epic);
        prioritizedTasks.put(epic.getStartTime(), epic);
        searchForATemporaryIntersection();
        epic.getEndTime();
    }

    @Override
    public void updatedSubTask(Subtask subTask) {
        try {
            searchForATemporaryIntersection();
            subtasks.put(subTask.getId(), subTask);
            setStatusEpic(subTask.getEpicId());
            setStartEndEpic(subTask.getEpicId());
            prioritizedTasks.put(subTask.getStartTime(), subTask);
        } catch (UnsupportedOperationException exception) {
            System.out.println("Подзадача <" + subTask.getName() + "> не обновлена!\n");
        }
    }
    @Override
    public void createTask(Task task) {
        try {
            int id = generateNewId();
            task.setId(id);
            tasks.put(id, task);
            prioritizedTasks.put(task.getStartTime(), task);
        } catch (UnsupportedOperationException exception) {
            System.out.println(exception.getMessage() + "Задача " + task.getName() + " не добавлена!");
        }
    }

    @Override
    public void createSubTask(Subtask subtask) {
        try {
            Epic epic = getEpicById(subtask.getEpicId());

            int idSubtask = generateNewId();
            subtask.setId(idSubtask);
            subtasks.put(idSubtask, subtask);

            epic.setListSubTask(subtask);
            setStatusEpic(subtask.getEpicId());
            setStartEndEpic(subtask.getEpicId());

            prioritizedTasks.put(subtask.getStartTime(), subtask);

        } catch (UnsupportedOperationException | NullPointerException exception) {
            System.out.println(exception.getMessage() + "Подзадача <" + subtask.getName() + "> не добавлена!\n");
        }
    }

    @Override
    public void createEpic(Epic epic) {
        int id = generateNewId();
        epic.setId(id);
        epics.put(id, epic);
        prioritizedTasks.put(epic.getStartTime(), epic);
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.addTask(task);
        }
        return task;
    }
    @Override
    public Subtask getSubTaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.addTask(subtask);
        }
        return subtask;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.addTask(epic);
            return epic;
        } else {
            throw new NullPointerException(String.format("Эпик с номером [%d] не существует!", id));
        }
    }


    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void removeTaskById(int id) {
        Task removedTask = tasks.remove(id);
        if (removedTask == null) {
            System.out.println("Задачи с номером " + id + " нет в списке!");
        } else {
            LocalDateTime keyRemovedTask = removedTask.getStartTime();
            historyManager.remove(id);
            prioritizedTasks.remove(keyRemovedTask);
        }
    }

    @Override
    public void removeSubTaskById(int id) {
        for (Subtask subtask : subtasks.values()) {
            if (id == subtask.getId()) {
                Epic epic = epics.get(subtask.getEpicId());
                epic.getListSubTask().remove(subtask);
                prioritizedTasks.remove(subtask.getStartTime());
                epic.getEndTime();
                break;
            }
        }
        subtasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeEpicById(int id) {
        historyManager.remove(id);
        Epic epic = epics.get(id);
        epics.remove(epic.getId());
        for (Subtask subtask : epic.getListSubtask()) {
            subtasks.remove(subtask.getId());
        }
        epic.getEndTime();
    }

    @Override
    public void clearAllTasks() {
        tasks.clear();
        prioritizedTasks.clear();
    }
    @Override
    public void clearAllSubTasks() {
        subtasks.clear();
        prioritizedTasks.clear();
    }
    @Override
    public void clearAllEpics() {
        epics.clear();
        prioritizedTasks.clear();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void setStatusEpic(int idEpic) {
        List<Subtask> listSubTask = getListSubTasks(idEpic);
        boolean isStatusNew = false;
        boolean isStatusInProgess = false;
        boolean isStatusDone = false;

        for (Subtask subtask : listSubTask) {
            switch (subtask.getStatus().toString()) {
                case ("NEW") -> isStatusNew = true;
                case ("IN_PROGRESS") -> isStatusInProgess = true;
                case ("DONE") -> isStatusDone = true;
            }
        }

        if (!isStatusNew && !isStatusInProgess && isStatusDone) {
            epics.get(idEpic).setStatus(TaskStatus.DONE);
        } else if (isStatusNew && !isStatusInProgess && !isStatusDone) {
            epics.get(idEpic).setStatus(TaskStatus.NEW);
        } else {
            epics.get(idEpic).setStatus(TaskStatus.IN_PROGRESS);
        }

    }

    @Override
    public List<Subtask> getListSubTasks(int id) {
        if (getEpicById(id) != null) {
            return getEpicById(id).getListSubTask();
        } else {
            System.out.println("Эпика с номером " + id + " не существует!");
            return null;
        }
    }
}

