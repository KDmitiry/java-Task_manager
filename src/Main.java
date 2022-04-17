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
        System.out.println(task1);

        Task task2 = new Task(null, "Task # 2", "Список продуктов", TaskStatus.DONE);
        manager.createNewTask(task2);
        System.out.println(task2);

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
        System.out.println(manager.getHistory());

        manager.updateNewTask(epic1);
        manager.updateEpicStatus(epic1);
        System.out.println(epic1);
        System.out.println(subtask1);
        System.out.println(subtask2);

        task1.setStatus(TaskStatus.DONE);
        System.out.println(task1);

        Epic epic2 = new Epic(null, "Epic # 2", "Ремонт машины", new ArrayList<>());
        manager.createNewTask(epic2);

        Subtask subtask3 = new Subtask(null, "Замена масла", "Слить старое масла, залить новое",
                TaskStatus.IN_PROGRESS, epic2.getId());
        Integer subtask3Id = manager.createNewTask(subtask3);
        epic1.addSubtask(subtask3Id);
        manager.updateNewTask(epic2);
        manager.updateEpicStatus(epic2);
        System.out.println(epic2);
        System.out.println(subtask3);

        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateEpicStatus(epic1);
        System.out.println(subtask1);
        System.out.println(epic1);

        System.out.println(manager.getHistory());


    }

}
