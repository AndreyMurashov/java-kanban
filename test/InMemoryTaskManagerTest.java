import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


class InMemoryTaskManagerTest {
    Managers managers = new Managers();
    TaskManager taskManager = managers.getDefault();

    // проверка, что утилитарный класс возвращает проинициализированный
    // и готовый к работе экземпляр менеджера TaskManager
    @Test
    public void isActualTaskManager() {
        Assertions.assertNotNull(taskManager, "Объекту taskManager присвоено значение null");
    }


    // проверка, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id
    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        taskManager.createTask(task);

        final Task savedTask = taskManager.getTaskById(task.getId());

        Assertions.assertNotNull(savedTask, "Задача не найдена.");
        Assertions.assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        Assertions.assertNotNull(tasks, "Задачи не возвращаются.");
        Assertions.assertEquals(1, tasks.size(), "Неверное количество задач.");
        Assertions.assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    // тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    @Test
    void equalAddedTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        taskManager.createTask(task);
        Task savedTask = taskManager.getTaskById(task.getId());
        Assertions.assertEquals(task.toString(), savedTask.toString(), "Выводы значений не совпадают");
    }

}