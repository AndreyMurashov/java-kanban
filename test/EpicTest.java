import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class EpicTest {
    Managers managers = new Managers();
    TaskManager taskManager = managers.getDefault();

    // Проверка что экземпляры класса Epic равны друг другу, если равен их id
    @Test
    public void epicEqualTest() {
        Epic epic = new Epic("Эпик 1", "Это демонстрационный эпик № 1");
        taskManager.createEpic(epic);
        Task epic1 = taskManager.getEpicById(1);
        Task epic2 = taskManager.getEpicById(1);
        Assertions.assertEquals(epic1, epic2);
    }

// Проверка невозможности добавления эпика самого в себя
    @Test
    public void epicAddToSubtask(){
        Epic epic = new Epic("Эпик 1", "Это демонстрационный эпик № 1");
        taskManager.createEpic(epic);
        int epicId = epic.getId();
        epic.addSubtask(epicId);
        List<Integer> epicSubtasks = epic.getSubtasks();
        boolean hasAny = epicSubtasks.stream().anyMatch(i -> i == epicId);
        Assertions.assertFalse(hasAny, "Эпик нельзя добавить сам в себя");
    }
}