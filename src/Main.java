import ru.practicum.tracker.controllers.Managers;
import ru.practicum.tracker.controllers.TaskManager;
import ru.practicum.tracker.model.Epic;
import ru.practicum.tracker.model.Subtask;
import ru.practicum.tracker.model.Task;
import ru.practicum.tracker.model.TaskStatus;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();


        Task task1 = new Task(null, "Task # 1", "Список материалов", TaskStatus.NEW);
        manager.createNewTask(task1);

        Task task2 = new Task(null, "Task # 2", "Список продуктов", TaskStatus.DONE);
        manager.createNewTask(task2);

        Epic epic1 = new Epic(null, "Epic # 1", "Покраска комнат", new ArrayList<>());
        manager.createNewTask(epic1);

        Subtask subtask1 = new Subtask(null, "Покраска комнаты # 1", "Синий цвет", TaskStatus.DONE,
                epic1.getId());
        manager.createNewTask(subtask1);

        Subtask subtask2 = new Subtask(null, "Покраска комнаты # 2", "Белый цвет", TaskStatus.DONE,
                epic1.getId());
        manager.createNewTask(subtask2);

        Subtask subtask3 = new Subtask(null, "Покраска комнаты # 3", "Зеленый цвет", TaskStatus.NEW,
                epic1.getId());
        manager.createNewTask(subtask3);

        Epic epic2 = new Epic(null, "Epic # 2", "Покраска корридора", new ArrayList<>());
        manager.createNewTask(epic2);

        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(3);
        manager.getTaskById(4);
        manager.getTaskById(5);
        manager.getTaskById(6);

        System.out.println(manager.getHistory());
        System.out.println(" ");

        manager.getTaskById(4);
        manager.getTaskById(2);
        manager.getTaskById(6);
        manager.getTaskById(1);
        manager.getTaskById(3);
        manager.getTaskById(5);

        System.out.println(manager.getHistory());
        System.out.println(" ");

        manager.removeTaskById(3);

        System.out.println(manager.getHistory());

        manager.removeTaskById(2);

        System.out.println(manager.getHistory());


    }

}
