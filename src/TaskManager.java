import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int id = 0;

    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();


    private int idGenerator() {
        id++;
        return id;
    }

    // Для Task
    public ArrayList<String> getAllTasks() {
        ArrayList<String> allTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            allTasks.add(task.getName());
        }
        return allTasks;
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public Task getTaskById(int id) {
        Task findedItem = null;
        if (!tasks.containsKey(id)) {
            System.out.println("Задача не найдена");
        } else {
            findedItem = tasks.get(id);
        }
        return findedItem;
    }

    public void createTask(Task task) {
        id = idGenerator();
        task.setId(id);
        tasks.put(id, task);
        System.out.println("Задача создана и сохранена");
    }

    public void updateTask(Task task) {
        int id = task.getId();
        tasks.put(id, task);
        System.out.println("Задача изменена");
    }

    public void deleteTaskById(int id) {
        if (!tasks.containsKey(id)) {
            System.out.println("Задача c таким id не найдена");
        } else {
            tasks.remove(id);
        }
        System.out.println("Задача удалена");
    }


// Для Epic

    public ArrayList<String> getAllEpics() {
        ArrayList<String> allEpics = new ArrayList<>();
        for (Epic epic : epics.values()) {
            allEpics.add(epic.getName());
        }
        return allEpics;
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear(); // с удалением эпиков удяляются и входящие в них подзадачи
    }

    public Epic getEpicById(int id) {
        Epic findedItem = null;
        if (!epics.containsKey(id)) {
            System.out.println("Эпик не найден");
        } else {
            findedItem = epics.get(id);
        }
        return findedItem;
    }

    public void createEpic(Epic epic) {
        id = idGenerator();
        epic.setId(id);
        epics.put(id, epic);
        System.out.println("Эпик создан и сохранен");
    }

    public void updateEpic(Epic newEpic) {
        // при изменении полей эпика не должны пропасть данные об относящихся к нему подзадачах
        int id = newEpic.getId();
        Epic oldEpic = epics.get(id);
        ArrayList<Integer> subtaskList = oldEpic.getSubtasks();
        newEpic.transferSubtasks(subtaskList);
        epics.put(id, newEpic);
        System.out.println("Эпик изменен");
    }

    public void deleteEpicById(int id) {
        if (!epics.containsKey(id)) {
            System.out.println("Эпик c таким id не найден");
        } else {
            // удаляем подзадачи, относящиеся к удаляемому эпику
            ArrayList<Integer> subs = getSubtasksByEpic(id);
            for (int sub : subs) {
                subtasks.remove(sub);
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
            ArrayList<Integer> epicSubtasks = epic.getSubtasks();
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

    public ArrayList<String> getAllSubtasks() {
        ArrayList<String> allSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            allSubtasks.add(subtask.getName());
        }
        return allSubtasks;
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
    }

    public Subtask getSubtaskById(int id) {
        Subtask findedItem = null;
        if (!subtasks.containsKey(id)) {
            System.out.println("Подзадача не найдена");
        } else {
            findedItem = subtasks.get(id);
        }
        return findedItem;
    }

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

    public ArrayList<Integer> getSubtasksByEpic(int id) {
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        if (!epics.containsKey(id)) {
            System.out.println("Эпик c таким id не найден");
        } else {
            Epic epic = epics.get(id);
            epicSubtasks = epic.getSubtasks();
        }
        return epicSubtasks;
    }
}