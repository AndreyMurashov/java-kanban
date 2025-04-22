import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TaskTest {
    Managers managers = new Managers();
    TaskManager taskManager = managers.getDefault();

    // Проверка что экземпляры класса Task равны друг другу, если равен их id
    @Test
    public void taskEqualTest() {
        Task task = new Task("Задача 1", "Это демонстрационная задача № 1");
        taskManager.createTask(task);
        Task task1 = taskManager.getTaskById(1);
        Task task2 = taskManager.getTaskById(1);
        Assertions.assertEquals(task1, task2);
    }
}