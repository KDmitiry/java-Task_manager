package ru.practicum.tracker.controllers;

import ru.practicum.tracker.exceptions.ManagerSaveException;
import ru.practicum.tracker.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static ru.practicum.tracker.model.TaskTypes.*;


public class FileBackedTasksManager extends InMemoryTaskManager {
    public FileBackedTasksManager() {
    }

    static File tasksStorage = new File("saveAllTasks.csv");


    public void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(tasksStorage.toPath(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Task task : getTasks().values()) {
                writer.write(task.toString());
                writer.newLine();
            }
            for (Task subTask : getTasks().values()) {
                writer.write(subTask.toString());
                writer.newLine();
            }
            for (Task epic : getTasks().values()) {
                writer.write(epic.toString());
                writer.newLine();
            }

            writer.newLine();

            String historyId = getHistoryIds();
            writer.write(historyId);
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при попытке сохранения в файл");
        }
    }

    static String getHistoryIds() {
        List<Integer> listHistoryId = new ArrayList<>();
        for (Map.Entry<Integer, Task> taskId : getTasks().entrySet()) {
            listHistoryId.add(taskId.getKey());
        }

        return listHistoryId.toString().replaceAll("^\\[|]$", "");
    }

    static FileBackedTasksManager loadFromFile(File storageTask) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        try (BufferedReader reader = new BufferedReader(new FileReader(storageTask, StandardCharsets.UTF_8))) {
            boolean readHistory = false;
            while (reader.ready()) {
                String line = reader.readLine();
                if (line.isBlank()) {
                    readHistory = true;
                } else if (readHistory) {
                    break;
                } else {
                    String[] splitter = line.split(", ");
                    TaskTypes type = TaskTypes.valueOf(splitter[0]);
                    String name = splitter[1];
                    String description = splitter[2];
                    Integer id = parseInt(splitter[3]);
                    TaskStatus status = TaskStatus.valueOf(splitter[4]);

                    if (type == TASK) {
                        getTasks().put(id, new Task(id, name, description, TaskStatus.NEW, LocalDateTime.now(), 10));
                    } else if (type == SUBTASK) {
                        int epicId = parseInt(splitter[5]);
                        getTasks().put(id, new Subtask(id, name, description, status, epicId, LocalDateTime.now(), 12));
                    } else if (type == EPIC) {
                        getTasks().put(id,
                                new Epic(id, name, description, TaskStatus.NEW, LocalDateTime.now(), 15));
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при загрузке из файла");
        }
        return fileBackedTasksManager;
    }

    public static TaskStatus statusFromString(String status) {
        switch (status) {
            case "NEW":
                return TaskStatus.NEW;
            case "DONE":
                return TaskStatus.DONE;
            case "IN_PROGRESS":
                return TaskStatus.IN_PROGRESS;
        }
        return null;
    }


    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void clearAllTasks() {
        super.clearAllTasks();
        save();
    }


    @Override
    public List<Task> getHistory() {
        final List<Task> history = super.getHistory();
        save();
        return history;
    }
}


