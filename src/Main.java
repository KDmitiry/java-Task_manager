import ru.practicum.Tracker.controllers.*;
import ru.practicum.Tracker.model.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task(null, "Ремонт", "Список материалов", "new");
        taskManager.createNewTask(task1);
        System.out.println(task1);

        Task task2 = new Task(null, "Поход в магазин", "Список продуктов", "done");
        taskManager.createNewTask(task2);
        System.out.println(task2);

        Epic epic1 = new Epic(null, "Покраска квартиры", "Покраска комнат", new ArrayList<>());
        taskManager.createNewTask(epic1);
        Subtask subtask1 = new Subtask(null, "Покраска комнаты # 1", "Синий цвет", "new",
                epic1.getId());
        Integer subtask1Id = taskManager.createNewTask(subtask1);
        epic1.addSubtask(subtask1Id);
        Subtask subtask2 = new Subtask(null, "Покраска комнаты # 2", "Белый цвет", "done",
                epic1.getId());
        Integer subtask2Id = taskManager.createNewTask(subtask2);
        epic1.addSubtask(subtask2Id);

        taskManager.updateNewTask(epic1);
        taskManager.updateEpicStatus(epic1);
        System.out.println(epic1);
        System.out.println(subtask1);
        System.out.println(subtask2);

        task1.setStatus("done");
        System.out.println(task1);

        Epic epic2 = new Epic(null, "Машина", "Ремонт машины", new ArrayList<>());
        taskManager.createNewTask(epic2);
        Subtask subtask3 = new Subtask(null, "Замена масла", "Слить старое масла, залить новое",
                "in progress", epic2.getId());
        Integer subtask3Id = taskManager.createNewTask(subtask3);
        epic1.addSubtask(subtask3Id);
        taskManager.updateNewTask(epic2);
        taskManager.updateEpicStatus(epic2);
        System.out.println(epic2);
        System.out.println(subtask3);

        subtask1.setStatus("in progress");
        taskManager.updateEpicStatus(epic1);
        System.out.println(subtask1);
        System.out.println(epic1);

        taskManager.getTaskById(8);

    }

}
