import ru.practicum.Tracker.controllers.Managers;
import ru.practicum.Tracker.controllers.TaskManager;
import ru.practicum.Tracker.model.Epic;
import ru.practicum.Tracker.model.Subtask;
import ru.practicum.Tracker.model.Task;
import ru.practicum.Tracker.model.TaskStatus;

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
        Integer subtask1Id = manager.createNewTask(subtask1);
        epic1.addSubtask(subtask1Id);

        Subtask subtask2 = new Subtask(null, "Покраска комнаты # 2", "Белый цвет", TaskStatus.DONE,
                epic1.getId());
        Integer subtask2Id = manager.createNewTask(subtask2);
        epic1.addSubtask(subtask2Id);

        Subtask subtask3 = new Subtask(null, "Покраска комнаты # 3", "Зеленый цвет", TaskStatus.NEW,
                epic1.getId());
        Integer subtask3Id = manager.createNewTask(subtask3);
        epic1.addSubtask(subtask3Id);

        Epic epic2 = new Epic(null, "Epic # 2", "Покраска корридора", new ArrayList<>());
        manager.createNewTask(epic2);

        System.out.println(task1);
        System.out.println(manager.getHistory());

        System.out.println(epic1);
        System.out.println(manager.getHistory());

        System.out.println(epic2);
        System.out.println(manager.getHistory());

        System.out.println(subtask1);
        System.out.println(manager.getHistory());

        manager.removeTaskById(1);
        System.out.println(manager.getHistory());

        manager.removeTaskById(5);
        System.out.println(manager.getHistory());;

    }

}
