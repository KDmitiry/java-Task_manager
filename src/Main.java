import ru.practicum.tracker.controllers.Managers;
import ru.practicum.tracker.controllers.TaskManager;
import ru.practicum.tracker.model.Epic;
import ru.practicum.tracker.model.Subtask;
import ru.practicum.tracker.model.Task;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.practicum.tracker.model.TaskStatus.NEW;

public class Main {



        static Task task1;
        static Task task2;
        static Epic epic1;
        static Epic epic2;
        static Subtask subtask1;

        static Subtask subtask2;

        public static void main(String[] args) {

            TaskManager manager = Managers.getDefault();

            task1 = new Task(null, "task1", "task1-1", NEW,
                    LocalDateTime.of(2022, Month.MAY, 17, 12, 20), 1);
            manager.createTask(task1);

            task2 = new Task(null, "task2", "task2-2", NEW,
                    LocalDateTime.of(2022, Month.MAY, 16, 12, 20), 1);
            manager.createTask(task2);

            epic1 = new Epic(null, "epic1", "epic1-1", NEW,
                    LocalDateTime.of(2022, Month.MAY, 13, 12, 20), 10);
            manager.createEpic(epic1);

            epic2 = new Epic(null, "epic2", "epic2-2", NEW,
                    LocalDateTime.of(2022, Month.MAY, 15, 12, 20), 18);
            manager.createEpic(epic2);

            subtask1 = new Subtask(null, "subtask1", "subtask1-1", NEW, epic1.getId(),
                    LocalDateTime.of(2022, Month.MAY, 11, 12, 20), 24);
            manager.createSubTask(subtask1);

            subtask2 = new Subtask(null, "subtask2", "subtask2-2", NEW, epic1.getId(),
                    LocalDateTime.of(2022, Month.MAY, 12, 12, 20), 13);
            manager.createSubTask(subtask2);

            System.out.println(manager.getPrioritizedTasks().size());

        }
    }

