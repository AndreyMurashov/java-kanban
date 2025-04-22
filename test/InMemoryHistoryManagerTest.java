import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class InMemoryHistoryManagerTest {
    Managers managers = new Managers();
    TaskManager taskManager = managers.getDefault();

    // Тест добавления в историю
    @Test
    void add() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task("Задача 1", "Это демонстрационная задача № 1");
        historyManager.addTask(task);
        final List<Task> history = historyManager.getHistory();
        Assertions.assertNotNull(history, "После добавления задачи, история не должна быть пустой.");
        Assertions.assertEquals(1, history.size(), "После добавления задачи, история не должна быть пустой.");
    }


    // проверка, что утилитарный класс возвращает проинициализированный
    // и готовый к работе экземпляр менеджера historyManager
    @Test
    void isActualTaskManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Assertions.assertNotNull(historyManager, "Объекту taskManager присвоено значение null");
    }

    // проверка, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данные
    @Test
    void isSavedActualVersion() {
        Task task = new Task("Задача 1", "Это демонстрационная задача № 1");
        taskManager.createTask(task);
        Task savedTask1 = taskManager.getTaskById(1);
        List<Task> tasksOldVersion = taskManager.getHistory();
        int listSizeOld = tasksOldVersion.size();
        Task oldVersion = tasksOldVersion.get(listSizeOld - 1);
        System.out.println(oldVersion);

        Task newTask = new Task("Измененная задача", "с измененным статусом", Status.IN_PROGRESS);
        newTask.setId(1);
        taskManager.updateTask(newTask);
        Task savedTask2 = taskManager.getTaskById(1);
        List<Task> tasksNewVersion = taskManager.getHistory();
        int listSizeNew = tasksNewVersion.size();
        Task newVersion = tasksNewVersion.get(listSizeNew - 1);
        System.out.println(newVersion);

        Assertions.assertNotEquals(oldVersion, newVersion, "Версии сохраненных в истории задач не отличаются");
    }
}