import jdk.jshell.Snippet;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        System.out.println("\nСоздаем две задачи:");
        Task task1 = new Task("Задача 1", "Это демонстрационная задача № 1");
        taskManager.createTask(task1);
        Task task2 = new Task("Задача 2", "Это демонстрационная задача № 2");
        taskManager.createTask(task2);

        System.out.println("\nСоздаем эпик с двумя задачами и эпик с одной задачей:");
        Epic epic1 = new Epic("Эпик 1", "Это демонстрационный эпик № 1");
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Эпик 2", "Это демонстрационный эпик № 2");
        taskManager.createEpic(epic2);

        Subtask subtask1 = new Subtask("Подзадача 1", "Это демонстрационная подзадача № 1", 3);
        taskManager.createSubtask(subtask1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Это демонстрационная подзадача № 2", 3);
        taskManager.createSubtask(subtask2);
        Subtask subtask3 = new Subtask("Подзадача 3", "Это демонстрационная подзадача № 3", 4);
        taskManager.createSubtask(subtask3);

        System.out.println("\nРезультат:");
        System.out.println("subtasks - " + taskManager.getSubtasksByEpic(3));
        System.out.println("subtasks - " + taskManager.getSubtasksByEpic(4));

        System.out.println("\nСписки задач, эпиков и подзадач:");
        System.out.println("Список задач: " + taskManager.getAllTasks());
        System.out.println("Список эпиков': " + taskManager.getAllEpics());
        System.out.println("Список подзадач: " + taskManager.getAllSubtasks());

        System.out.println("\nИзменяем статус задачи:");
        System.out.println("Было: ");
        System.out.println(taskManager.getTaskById(1));

        Task newTask = new Task("Измененная задача", "с измененным статусом", Status.IN_PROGRESS);
        taskManager.updateTask(1, newTask);
        System.out.println(taskManager.getTaskById(1));

        System.out.println("\nИзменяем подзадачи:");
        System.out.println("Было:");
        System.out.println("3 - " + taskManager.getEpicById(3));
        System.out.println("5 - " + taskManager.getSubtaskById(5));
        System.out.println("6 - " + taskManager.getSubtaskById(6));

        Subtask newSubtask = new Subtask("Измененная подзадача № 1", "Статус изменен на DONE", 3, Status.DONE);
        taskManager.updateSubtask(5, newSubtask);
        Subtask newSubtask2 = new Subtask("Измененная подзадача № 2", "Статус изменен на DONE", 3, Status.DONE);
        taskManager.updateSubtask(6, newSubtask2);
        System.out.println("\nРезультат - статусы подзадач и эпика изменились:");
        System.out.println("3 - " + taskManager.getEpicById(3));
        System.out.println("5 - " + taskManager.getSubtaskById(5));
        System.out.println("6 - " + taskManager.getSubtaskById(6));


        System.out.println("\nУдаляем одну из задач:");
        System.out.println("Было:");
        System.out.println("1 - " + taskManager.getTaskById(1));
        taskManager.deleteTaskById(1);

        System.out.println("\nРезультат - задача удалена:");
        System.out.println("1 - " + taskManager.getTaskById(1));

        System.out.println("\nУдяляем один из эпиков:");
        System.out.println("Было:");
        System.out.println("3 - " + taskManager.getEpicById(3));
        System.out.println("5 - " + taskManager.getSubtaskById(5));
        System.out.println("6 - " + taskManager.getSubtaskById(6));
        taskManager.deleteEpicById(3);

        System.out.println("\nРезультат - эпик удален, подзадачи автоматически удалены:");
        System.out.println("3 - " + taskManager.getEpicById(3));
        System.out.println("5 - " + taskManager.getSubtaskById(5));
        System.out.println("6 - " + taskManager.getSubtaskById(6));

    }
}
