package ru.practicum.tracker.controllers;

import ru.practicum.tracker.exceptions.ManagerSaveException;
import ru.practicum.tracker.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static ru.practicum.tracker.model.TaskTypes.*;


public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    static File tasksStorage = new File("saveAllTasks.csv");


    public void save() { // Создаем метод для сохранения
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
                        getTasks().put(id,
                                new Task(id, name, description, status));
                    } else if (type == SUBTASK) {
                        Integer epicId = parseInt(splitter[5]);
                        getTasks().put(id,
                                new Subtask(id, name, description, status, epicId));
                    } else if (type == EPIC) {
                        getTasks().put(id,
                                new Epic(id, name, description));
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
    public Task getTaskById(int id) {
        super.getTaskById(id);
        save();
        return getTaskById(id);
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        super.updateEpicStatus(epic);
        save();
    }

    @Override
    public Task createNewTask(Task task) {
        super.createNewTask(task);
        save();
        return task;
    }

    @Override
    public void updateNewTask(Task task) {
        super.updateNewTask(task);
        save();
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

    public static void main(String[] args) { // Создаем задачи и тестируем программу

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        FileBackedTasksManager.loadFromFile(tasksStorage);

        Task task1 = new Task(0, "Task # 1", "description 1", TaskStatus.NEW);
        fileBackedTasksManager.createNewTask(task1);

        Task task2 = new Task(1, "Task # 2", "description 2", TaskStatus.DONE);

        System.out.println(fileBackedTasksManager.getHistory());

        fileBackedTasksManager.createNewTask(task2);

        Epic epic1 = new Epic(2, "Epic # 1", "description 3");
        fileBackedTasksManager.createNewTask(epic1);

        Subtask subtask1 = new Subtask(3, "qrgsd", "description 4", TaskStatus.DONE,
                epic1.getId());
        fileBackedTasksManager.createNewTask(subtask1);

        Subtask subtask2 = new Subtask(4, "fhjtr", "description 5", TaskStatus.DONE,
                epic1.getId());
        fileBackedTasksManager.createNewTask(subtask2);

        Subtask subtask3 = new Subtask(5, "klfc", "description 6", TaskStatus.NEW,
                epic1.getId());
        fileBackedTasksManager.createNewTask(subtask3);

        Epic epic2 = new Epic(6, "Epic # 2", "description 7");
        fileBackedTasksManager.createNewTask(epic2);

        System.out.println(fileBackedTasksManager.getHistory());


        System.out.println("Выводим Task 1" + "\n" + fileBackedTasksManager.getTaskById(0));
        System.out.println("Выводим Epic 2" + "\n" + fileBackedTasksManager.getTaskById(6));

        fileBackedTasksManager.getTaskById(epic1.getId());
        fileBackedTasksManager.getTaskById(epic2.getId());
        fileBackedTasksManager.getTaskById(epic1.getId());
        fileBackedTasksManager.getTaskById(epic2.getId());
        fileBackedTasksManager.getTaskById(epic1.getId());
        fileBackedTasksManager.getTaskById(epic2.getId());

        System.out.println("Выводим историю" + "\n" + fileBackedTasksManager.getHistory());

        fileBackedTasksManager.removeTaskById(task1.getId());  //Тестируем удаление
        System.out.println("Выводим историю без задачи 1" + "\n"
                + fileBackedTasksManager.getHistory());

    }
}


