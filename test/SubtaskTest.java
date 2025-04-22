import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubtaskTest {

    Managers managers = new Managers();
    TaskManager taskManager = managers.getDefault();
    Epic epic = new Epic("Эпик 1", "Это демонстрационный эпик № 1");
    Subtask subtask = new Subtask("Подзадача 1", "Это демонстрационная подзадача № 1", 1);


    @BeforeEach
    public void setUp() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask);
    }

    // Проверка что экземпляры класса Subtask равны друг другу, если равен их id
    @Test
    public void subtaskEqualTest() {
        Subtask subtask1 = taskManager.getSubtaskById(subtask.getId());
        Subtask subtask2 = taskManager.getSubtaskById(subtask.getId());
        Assertions.assertEquals(subtask1, subtask2);
    }

    // Проверка невозможности добавления эпика самого в себя
    @Test
    public void subtaskMakeItselfEpic() {
        int subtaskId = subtask.getId();
        subtask.setEpicId(epic.getId());
        int newSubtaskId = subtask.getId();
        Assertions.assertNotEquals(subtaskId, newSubtaskId, "Подзадачу нельзя сделать своим же эпиком");
    }

}