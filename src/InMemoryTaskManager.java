import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;

    Map<Integer, Task> tasks = new HashMap<>();
    Map<Integer, Epic> epics = new HashMap<>();
    Map<Integer, Subtask> subtasks = new HashMap<>();
    HistoryManager historyManager = Managers.getDefaultHistory();

    private int idGenerator() {
        id++;
        return id;
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = historyManager.getHistory();
        List<Task> historyForSend = new ArrayList<>(history);
        return historyForSend;
    }


    // Для Task
    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            allTasks.add(task);
        }
        return allTasks;
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task findedItem = null;
        if (!tasks.containsKey(id)) {
            System.out.println("Задача не найдена");
        } else {
            findedItem = tasks.get(id);
            historyManager.addTask(findedItem);
        }
        return findedItem;
    }

    @Override
    public void createTask(Task task) {
        id = idGenerator();
        task.setId(id);
        tasks.put(id, task);
        System.out.println("Задача создана и сохранена");
    }

    @Override
    public void updateTask(Task task) {
        int id = task.getId();
        tasks.put(id, task);
        System.out.println("Задача изменена");
    }

    @Override
    public void deleteTaskById(int id) {
        if (!tasks.containsKey(id)) {
            System.out.println("Задача c таким id не найдена");
        } else {
            tasks.remove(id);
        }
        System.out.println("Задача удалена");
    }


    // Для Epic
    @Override
    public ArrayList<Task> getAllEpics() {
        ArrayList<Task> allEpics = new ArrayList<>();
        for (Epic epic : epics.values()) {
            allEpics.add(epic);
        }
        return allEpics;
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear(); // с удалением эпиков удяляются и входящие в них подзадачи
    }

    @Override
    public Epic getEpicById(int id) {
        Epic findedItem = null;
        if (!epics.containsKey(id)) {
            System.out.println("Эпик не найден");
        } else {
            findedItem = epics.get(id);
            historyManager.addTask(findedItem);
        }
        return findedItem;
    }

    @Override
    public void createEpic(Epic epic) {
        id = idGenerator();
        epic.setId(id);
        epics.put(id, epic);
        System.out.println("Эпик создан и сохранен");
    }

    @Override
    public void updateEpic(Epic newEpic) {
        // при изменении полей эпика не должны пропасть данные об относящихся к нему подзадачах
        int id = newEpic.getId();
        Epic oldEpic = epics.get(id);
        List<Integer> subtaskList = oldEpic.getSubtasks();
        newEpic.transferSubtasks(subtaskList);
        epics.put(id, newEpic);
        System.out.println("Эпик изменен");
    }

    @Override
    public void deleteEpicById(int id) {
        if (!epics.containsKey(id)) {
            System.out.println("Эпик c таким id не найден");
        } else {
            // удаляем подзадачи, относящиеся к удаляемому эпику
            List<Task> subs = getSubtasksByEpic(id);
            for (Task sub : subs) {
                int deletedId = sub.getId();
                subtasks.remove(deletedId);
            }
            // удаляем сам эпик
            epics.remove(id);
            System.out.println("Эпик удален");
        }
    }

    private void changeEpicStatus(int id) {
        if (!epics.containsKey(id)) {
            System.out.println("Эпик c таким id не найден");
        } else {
            // ищем материнский эпик
            Epic epic = epics.get(id);
            // получаем список дочерних подзадач
            List<Integer> epicSubtasks = epic.getSubtasks();
            // собираем статусы подзадач
            ArrayList<Status> stats = new ArrayList<>();
            for (Integer epicSubtask : epicSubtasks) {
                Subtask epicContainedSubtask = subtasks.get(epicSubtask);
                if (epicContainedSubtask != null) {
                    Status stat = epicContainedSubtask.getStatus();
                    stats.add(stat);
                } else {
                    System.out.println("ошибка при анализе статусов");
                }
            }
            // устанавливаем статус эпику
            if (!stats.contains(Status.IN_PROGRESS) && !stats.contains(Status.DONE)) {
                epic.setStatus(Status.NEW);
            } else if (!stats.contains(Status.IN_PROGRESS) && !stats.contains(Status.NEW)) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }


    // Для Subtask
    @Override
    public ArrayList<Task> getAllSubtasks() {
        ArrayList<Task> allSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            allSubtasks.add(subtask);
        }
        return allSubtasks;
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask findedItem = null;
        if (!subtasks.containsKey(id)) {
            System.out.println("Подзадача не найдена");
        } else {
            findedItem = subtasks.get(id);
            historyManager.addTask(findedItem);
        }
        return findedItem;
    }

    @Override
    public void createSubtask(Subtask subtask) {
        id = idGenerator();
        subtask.setId(id);
        subtasks.put(id, subtask);
        int epicID = subtask.getEpicId();
        Epic epic = epics.get(epicID);
        epic.addSubtask(id);
        System.out.println("Подзадача создана и сохранена");
        changeEpicStatus(epicID);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        // отбираем старый и новый статусы для последующего сравнения
        int id = subtask.getId();
        Subtask oldSubtask = subtasks.get(id);
        Status oldStatus = oldSubtask.getStatus();
        Status newStatus = subtask.getStatus();

        // при обновлении полей подзадачи не должна пропасть информация о материнском эпике
        int oldEpicId = oldSubtask.getEpicId();
        subtask.setEpicId(oldEpicId);

        // изменяем подзадачу
        subtasks.put(id, subtask);
        System.out.println("Подзадача изменена");
        int epicId = subtask.getEpicId();
        changeEpicStatus(epicId);
    }

    @Override
    public void deleteSubtaskById(int id) {
        if (!subtasks.containsKey(id)) {
            System.out.println("Подзадача c таким id не найдена");
        } else {
            // прежде всего удаляем упоминание об удаляемой подзадаче из ее эпика
            Subtask subtask = subtasks.get(id);
            int epicId = subtask.getEpicId();
            Epic epic = epics.get(epicId);
            epic.removeSubtask(id);
            // удаляем саму подзадачу
            subtasks.remove(id);
            changeEpicStatus(epicId);
        }
        System.out.println("Подзадача удалена");
    }

    @Override
    public List<Task> getSubtasksByEpic(int id) {
        List<Integer> epicSubtasksNumbers = new ArrayList<>();
        List<Task> epicSubtasks = new ArrayList<>();
        if (!epics.containsKey(id)) {
            System.out.println("Эпик c таким id не найден");
        } else {
            Epic epic = epics.get(id);
            epicSubtasksNumbers = epic.getSubtasks();

            for (int epicSubtasksNumber : epicSubtasksNumbers) {
                Subtask subtask = subtasks.get(epicSubtasksNumber);
                epicSubtasks.add(subtask);
            }
        }
        return epicSubtasks;
    }
}