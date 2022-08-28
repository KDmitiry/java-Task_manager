package ru.practicum.tracker.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.tracker.model.Epic;
import ru.practicum.tracker.model.Subtask;
import ru.practicum.tracker.model.Task;
import ru.practicum.tracker.model.TaskStatus;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practicum.tracker.model.TaskStatus.*;

abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    Task task1;
    Task task2;
    Epic epic1;
    Epic epic2;
    Subtask subtask1;
    Subtask subtask2;
    @BeforeEach
    void init() {

        task1 =  new Task(null, "task1", "task1-1", NEW,
                LocalDateTime.of(2022, Month.MAY, 17, 12, 20), 1);
        taskManager.createTask(task1);

        task2 =  new Task(null, "task2", "task2-2", NEW,
                LocalDateTime.of(2022, Month.MAY, 16, 12, 20), 1);
        taskManager.createTask(task2);

        epic1 = new Epic(null, "epic1", "epic1-1", NEW,
                LocalDateTime.of(2022, Month.MAY, 13, 12, 20), 10);
        taskManager.createEpic(epic1);

        epic2 = new Epic(null, "epic2", "epic2-2", NEW,
                LocalDateTime.of(2022, Month.MAY, 15, 12, 20), 18);
        taskManager.createEpic(epic2);

        subtask1 = new Subtask(null, "subtask1", "subtask1-1", NEW, epic1.getId(),
                LocalDateTime.of(2022, Month.MAY, 11, 12, 20), 24);
        taskManager.createSubTask(subtask1);

        subtask2 = new Subtask(null, "subtask2", "subtask2-2", NEW, epic1.getId(),
                LocalDateTime.of(2022, Month.MAY, 12, 12, 20), 13);
        taskManager.createSubTask(subtask2);
    }
    @Test
    void statusEpicIfEmptyListOfSubtasks() {
        taskManager.clearAllSubTasks();
        taskManager.getSubTaskById(epic1.getId());

        assertEquals(TaskStatus.NEW, epic1.getStatus(), "Ожидался статус NEW");
    }

    @Test
    void statusEpicIfStatusSubtasksNew() {
        Epic epicTest = new Epic(null, "epicTest", "epicTest", NEW,
                LocalDateTime.of(2022, Month.MAY, 26, 11, 0), 1);
        taskManager.createEpic(epicTest);
        Subtask subtaskTest1 = new Subtask(null,"subtaskTest1", "subtaskTest1",
                NEW, epicTest.getId(), LocalDateTime.of(2022, Month.MAY, 23, 11, 0), 2);
        Subtask subtaskTest2 = new Subtask(null, "subtaskTest2", "subtaskTest2",
                NEW, epicTest.getId(),LocalDateTime.of(2022, Month.MAY, 23, 12, 0),3);
        taskManager.createSubTask(subtaskTest1);
        taskManager.createSubTask(subtaskTest2);
        taskManager.getEpicById(epicTest.getId());

        assertEquals(NEW, epicTest.getStatus());
    }

    @Test
    void statusEpicIfStatusSubtasksDone() {
        Epic epicTest = new Epic(null, "epicTest", "epicTest", NEW,
                LocalDateTime.of(2022, Month.MAY, 26, 11, 0), 1);
        taskManager.createEpic(epicTest);
        Subtask subtaskTest1 = new Subtask(null,"subtaskTest1", "subtaskTest1",
                DONE, epicTest.getId(), LocalDateTime.of(2022, Month.MAY, 23, 11, 0), 2);
        Subtask subtaskTest2 = new Subtask(null, "subtaskTest2", "subtaskTest2",
                DONE, epicTest.getId(), LocalDateTime.of(2022, Month.MAY, 23, 12, 0),3);
        taskManager.createSubTask(subtaskTest1);
        taskManager.createSubTask(subtaskTest2);
        taskManager.getEpicById(epicTest.getId());

        assertEquals(DONE, epicTest.getStatus());
    }

    @Test
    void statusEpicIfStatusSubtasksInProgress() {
        Epic epicTest = new Epic(null, "epicTest", "epicTest", NEW,
                LocalDateTime.of(2022, Month.MAY, 26, 11, 0), 1);
        taskManager.createEpic(epicTest);
        Subtask subtaskTest1 = new Subtask(null,"subtaskTest1", "subtaskTest1",
                IN_PROGRESS, epicTest.getId(), LocalDateTime.of(2022, Month.MAY, 23, 11, 0), 2);
        Subtask subtaskTest2 = new Subtask(null, "subtaskTest2", "subtaskTest2",
                IN_PROGRESS, epicTest.getId(), LocalDateTime.of(2022, Month.MAY, 23, 12, 0),3);
        taskManager.createSubTask(subtaskTest1);
        taskManager.createSubTask(subtaskTest2);
        taskManager.getEpicById(epicTest.getId());

        assertEquals(IN_PROGRESS, epicTest.getStatus());
    }

    @Test
    void statusEpicIfStatusSubtasksNewAndDone() {
        Epic epicTest = new Epic(null, "epicTest", "epicTest", NEW,
                LocalDateTime.of(2022, Month.MAY, 26, 11, 0), 1);
        taskManager.createEpic(epicTest);
        Subtask subtaskTest1 = new Subtask(null,"subtaskTest1", "subtaskTest1",
                NEW, epicTest.getId(), LocalDateTime.of(2022, Month.MAY, 23, 11, 0), 2);
        Subtask subtaskTest2 = new Subtask(null, "subtaskTest2", "subtaskTest2",
                DONE,epicTest.getId(),LocalDateTime.of(2022, Month.MAY, 23, 12, 0),3);
        taskManager.createSubTask(subtaskTest1);
        taskManager.createSubTask(subtaskTest2);
        taskManager.getEpicById(epicTest.getId());

        assertEquals(TaskStatus.IN_PROGRESS, epicTest.getStatus());
    }

    @Test
    void shouldMethodCheckingForAnEmptyTaskList() {
        taskManager.clearAllTasks();
        assertTrue(taskManager.getAllTasks().isEmpty());
    }

    @Test
    void shouldMethodCheckingForAnEmptySubtaskList() {
        taskManager.clearAllSubTasks();
        assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    void shouldMethodCheckingForAnEmptyEpicList() {
        taskManager.clearAllEpics();
        assertTrue(taskManager.getAllEpics().isEmpty());
    }
    @Test
    void  shouldMethodGetTaskById() {
        assertEquals(0, task1.getId());

    }
    @Test
    void  shouldMethodCreateTask() {
        assertEquals(2,taskManager.getAllTasks().size());
    }

    @Test
    void  shouldMethodCreateSubTask() {
        assertEquals(4,taskManager.getAllSubtasks().size());
    }

    @Test
    void  shouldMethodCreateEpic() {
        assertEquals(3,taskManager.getAllEpics().size());
    }

    @Test
    void  shouldMethodGetSubTaskById() {
        assertEquals(5,subtask2.getId());
    }

    @Test
    void  shouldMethodGetEpicById() {
        assertEquals(2,epic1.getId());
    }

    @Test
    void  shouldMethodUpdatedTask() {
        Task updatedTask = new Task(0,"new Task1", "new Description", IN_PROGRESS, LocalDateTime.now(),19);
        taskManager.updatedTask(updatedTask);
        assertEquals(new Task(0,"new Task1", "new Description", IN_PROGRESS, LocalDateTime.now(),19), taskManager.getTaskById(0));
    }

    @Test
    void  shouldMethodUpdatedEpic() {
        Epic updatedEpic = new Epic(2, "new Epic1", "new Description", DONE, LocalDateTime.of(2022, Month.MARCH,13,11,45),9);
        taskManager.updatedEpic(updatedEpic);
        assertEquals(new Epic(2, "new Epic1", "new Description", DONE, LocalDateTime.of(2022, Month.MARCH,13,11,45),9), taskManager.getEpicById(2));
    }

    @Test
    void  shouldMethodUpdatedSubTask() {
        Subtask updatedSubtask = new Subtask(5,"new Subtask1", "new Discription", NEW, epic1.getId(), LocalDateTime.now(), 7);
        taskManager.updatedSubTask(updatedSubtask);
        assertEquals(new Subtask(5,"new Subtask1", "new Discription", NEW, epic1.getId(), LocalDateTime.now(), 7), taskManager.getSubTaskById(5));
    }

    @Test
    void  shouldMethodGetAllTasks() {
        assertEquals(2, taskManager.getAllTasks().size());
    }

    @Test
    void  shouldMethodGetAllEpics() {
        assertEquals(3, taskManager.getAllEpics().size());
    }

    @Test
    void  shouldMethodGetAllSubtasks() {
        assertEquals(4, taskManager.getAllSubtasks().size());
    }

    @Test
    void  shouldMethodRemoveTaskById() {
        taskManager.removeTaskById(0);
        assertEquals(1, taskManager.getAllTasks().size());
    }

    @Test
    void  shouldMethodRemoveSubTaskById() {
        taskManager.removeSubTaskById(4);
        assertEquals(3, taskManager.getAllSubtasks().size());
    }

    @Test
    void  shouldMethodRemoveEpicById() {
        taskManager.removeEpicById(2);
        assertEquals(2, taskManager.getAllEpics().size());

    }

    @Test
    void  shouldMethodClearAllTasks() {
        taskManager.clearAllTasks();
        assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    void  shouldMethodClearAllSubTasks() {
        taskManager.clearAllSubTasks();
        assertEquals(0, taskManager.getAllSubtasks().size());
    }

    @Test
    void  shouldMethodClearAllEpics() {
        taskManager.clearAllEpics();
        assertEquals(0, taskManager.getAllEpics().size());
    }

    @Test
    void  shouldMethodGetHistory() {
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getSubTaskById(subtask1.getId());
        taskManager.getEpicById(epic1.getId());

        assertEquals(4, taskManager.getHistory().size());
    }

    @Test
    void  shouldMethodGetPrioritizedTasks() {

        final List<Task> priorities = taskManager.getPrioritizedTasks();

        assertNotNull(priorities, "Список задач по приоритетам пустой");
        assertEquals(6, priorities.size(), "Неверное количество задач в списке приоритетов.");
        assertEquals(taskManager.getAllTasks().get(1), priorities.get(4), "Задача не соотвествует порядку приоритета");
        assertEquals(taskManager.getAllTasks().get(0), priorities.get(5), "Задача не соотвествует порядку приоритета");
        }

    @Test
    void shouldMethodGetPrioritizedTasksForEmptyListTask() {
        final List<Task> emptyPriorities = taskManager.getPrioritizedTasks();
        assertNotNull(emptyPriorities, "Список задач по пиоритетам не пустой");
    }

    @Test
    void  shouldMethodGetListSubTasks() {
        assertEquals(2, taskManager.getListSubTasks(2).size());
    }

}