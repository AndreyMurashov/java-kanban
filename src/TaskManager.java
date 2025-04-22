import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    int idGenerator();
    List<Task> getHistory();

    // создание
    void createTask(Task task);
    void createEpic(Epic epic);
    void createSubtask(Subtask subtask);


    // получение всего
    ArrayList<Task> getAllTasks();
    ArrayList<Task> getAllEpics();
    ArrayList<Task> getAllSubtasks();
    ArrayList<Task> getSubtasksByEpic(int id);

    // получение по id
    Task getTaskById(int id);
    Epic getEpicById(int id);
    Subtask getSubtaskById(int id);

    // изменение
    void updateTask(Task task);
    void updateEpic(Epic newEpic);
    void updateSubtask(Subtask subtask);

    // удаление всего
    void deleteAllTasks();
    void deleteAllEpics();
    void deleteAllSubtasks();

    // удаление по Id
    void deleteTaskById(int id);
    void deleteEpicById(int id);
    void deleteSubtaskById(int id);
}
