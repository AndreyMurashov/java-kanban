import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static int id = -1;

    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();

    Task task;
    Epic epic;
    Subtask subtask;


    public static int idGenerator() {
        id++;
        return id;
    }

    public ArrayList<String> getAllTasks(TaskType taskType) {
        ArrayList<String> allTasks = new ArrayList<>();
        switch (taskType) {
            case TASK:
                for (Task task : tasks.values()) {
                    allTasks.add(task.getName());
                }
                break;
            case EPIC:
                for (Epic epic : epics.values()) {
                    allTasks.add(epic.getName());
                }
                break;
            case SUBTASK:
                for (Subtask subtask : subtasks.values()) {
                    allTasks.add(subtask.getName());
                }
                break;
        }
        return allTasks;
    }

    public boolean deleteAllTasks() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
        return true;
    }

    public Task getTaskById(int id, TaskType taskType) {
        Task findedItem = null;
        switch (taskType) {
            case TASK:
                if (!tasks.containsKey(id)) {
                    System.out.println("Задача не найдена");
                } else {
                    findedItem = tasks.get(id);
                }
                break;
            case EPIC:
                if (!epics.containsKey(id)) {
                    System.out.println("Эпик не найден");
                } else {
                    findedItem = epics.get(id);
                }
                break;
            case SUBTASK:
                if (!subtasks.containsKey(id)) {
                    System.out.println("Подзадача не найдена");
                } else {
                    findedItem = subtasks.get(id);
                }
                break;
        }
        return findedItem;
    }

    public boolean createTask(String name, String description, TaskType taskType) {
        switch (taskType) {
            case TASK:
                id = idGenerator();
                task = new Task(id, name, description);
                tasks.put(id, task);
                System.out.println("Задача создана");
                break;
            case EPIC:
                id = idGenerator();
                epic = new Epic(id, name, description);
                epics.put(id, epic);
                System.out.println("Эпик создан");
                break;
            case SUBTASK:
                id = idGenerator();
                subtask = new Subtask(id, name, description);
                subtasks.put(id, subtask);
                Epic defaultEpic = epics.get(0);
                defaultEpic.addSubtask(id);
                System.out.println("Подзадача создана");
                break;
        }
        return true;
    }

    public boolean updateTaskById(int id, String name, String description, TaskType taskType, Status status) {
        deleteTaskById(id, taskType);
        switch (taskType) {
            case TASK:
                task = new Task(id, name, description, status);
                tasks.put(id, task);
                break;
            case EPIC:
                epic = new Epic(id, name, description, status);
                epics.put(id, epic);
                break;
            case SUBTASK:
                System.out.println("Необходимо указать тип задачи: подзадача");
                return false;
        }
        System.out.println("Изменено");
        return true;
    }

    public boolean updateTaskById(int id, String name, String description, TaskType taskType, Status status, int motherEpic) {
        switch (taskType) {
            case SUBTASK:
                deleteTaskById(id, taskType);
                subtask = new Subtask(id, name, description, status, motherEpic);
                subtasks.put(id, subtask);
                break;
            case TASK:
            case EPIC:
                System.out.println("Необходимо указать правильный тип задачи");
                return false;
        }
        System.out.println("Изменено");
        return true;
    }

    public boolean deleteTaskById(int id, TaskType taskType) {
        switch (taskType) {
            case TASK:
                if (!tasks.containsKey(id)) {
                    System.out.println("Задача c таким id не найдена");
                    return false;
                } else {
                    tasks.remove(id);
                }
                break;
            case EPIC:
                if (!epics.containsKey(id)) {
                    System.out.println("Эпик c таким id не найден");
                    return false;
                } else {
                    Epic epic = epics.get(id);
                    Epic defaultEpic = epics.get(0);
                    ArrayList<Integer> subs = epic.getSubtasks();

                    for (int sub : subs) {
                        Subtask findedItem = subtasks.get(sub);
                        findedItem.setMotherEpic(0);
                        defaultEpic.addSubtask(sub);
                    }
                    epics.remove(id);
                }
                break;
            case SUBTASK:
                if (!subtasks.containsKey(id)) {
                    System.out.println("Подзадача c таким id не найдена");
                    return false;
                } else {
                    subtasks.remove(id);
                    for (Epic epic : epics.values()) {
                        ArrayList<Integer> subs = epic.getSubtasks();
                        if (subs.contains(id)) {
                            for (int sub : subs) {
                                if (sub == id) {
                                    epic.removeSubtask(id);
                                }
                            }
                        }
                    }
                }
                break;
        }
        System.out.println("Удалено");
        return true;
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

    public boolean changeStatus(int id, Status status, TaskType taskType) {
        switch (taskType) {
            case TASK:
                if (!tasks.containsKey(id)) {
                    System.out.println("Задача c таким id не найдена");
                    return false;
                } else {
                    Task findedItem = tasks.get(id);
                    findedItem.setStatus(status);
                }
                break;
            case EPIC:
                System.out.println("Статус эпика изменен быть не может");
                return false;
            case SUBTASK:
                if (!subtasks.containsKey(id)) {
                    System.out.println("Подзадача c таким id не найдена");
                    return false;
                } else {
                    if (status.equals(Status.IN_PROGRESS)) {
                        Subtask sub = subtasks.get(id);
                        sub.setStatus(Status.IN_PROGRESS);
                        int motherEpicNumber = sub.getMotherEpic();
                        Epic motherEpic = epics.get(motherEpicNumber);
                        motherEpic.setStatus(Status.IN_PROGRESS);
                    } else if (status.equals((Status.DONE))) {
                        Subtask sub = subtasks.get(id);
                        sub.setStatus(Status.DONE);
                        int motherEpicNumber = sub.getMotherEpic();
                        Epic motherEpic = epics.get(motherEpicNumber);
                        motherEpic.setStatus(Status.IN_PROGRESS);
                        ArrayList<Status> stats = new ArrayList<>();
                        ArrayList<Integer> epicSubtasks = motherEpic.getSubtasks();
                        for (Integer epicSubtask : epicSubtasks) {
                            Subtask epicContainedSubtask = subtasks.get(epicSubtask);
                            if (epicContainedSubtask != null) {
                                Status stat = epicContainedSubtask.getStatus();
                                stats.add(stat);
                            } else {
                                System.out.println("ошибка");
                                return false;
                            }
                        }
                        if (stats.contains(Status.IN_PROGRESS) || stats.contains(Status.NEW)) {
                            // nothing
                        } else {
                            motherEpic.setStatus(Status.DONE);
                        }
                    }
                }
                break;
        }
        System.out.println("Статус изменен");
        return true;
    }

    public boolean changeEpic(int id, int newEpicNumber) {
        if (!subtasks.containsKey(id)) {
            System.out.println("Подзадача c таким id не найдена");
            return false;
        } else {
            if (!epics.containsKey(newEpicNumber)) {
                System.out.println("Эпик c таким id не существует");
                return false;
            } else {
                Subtask findedItem = subtasks.get(id);
                int currentEpicNumber = findedItem.getMotherEpic();
                Epic currentEpic = epics.get(currentEpicNumber);
                currentEpic.removeSubtask(id);
                Epic newEpic = epics.get(newEpicNumber);
                newEpic.addSubtask(id);
                findedItem.setMotherEpic(newEpicNumber);
            }
        }
        return true;
    }
}