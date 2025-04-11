public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        taskManager.createTask("default", "Эпик по умолчанию", TaskType.EPIC);

        System.out.println("\nСоздаем две задачи:");
        taskManager.createTask("Задача 1", "Это демонстрационная задача № 1", TaskType.TASK);
        taskManager.createTask("Задача 2", "Это демонстрационная задача № 2", TaskType.TASK);

        System.out.println("\nСоздаем эпик с двумя задачами и эпик с одной задачей:");
        taskManager.createTask("Эпик 1", "Это демонстрационный эпик № 1", TaskType.EPIC);
        taskManager.createTask("Эпик 2", "Это демонстрационный эпик № 2", TaskType.EPIC);

        taskManager.createTask("Подзадача 1", "Это демонстрационная подзадача № 1", TaskType.SUBTASK);
        taskManager.changeEpic(5, 3);
        taskManager.createTask("Подзадача 2", "Это демонстрационная подзадача № 2", TaskType.SUBTASK);
        taskManager.changeEpic(6, 3);
        taskManager.createTask("Подзадача 3", "Это демонстрационная подзадача № 3", TaskType.SUBTASK);
        taskManager.changeEpic(7, 4);

        System.out.println("\nРезультат:");
        System.out.println("subtasks - " + taskManager.getSubtasksByEpic(3));
        System.out.println("subtasks - " + taskManager.getSubtasksByEpic(4));

        System.out.println("\nСписки задач, эпиков и подзадач:");
        System.out.println("Список задач: " + taskManager.getAllTasks(TaskType.TASK));
        System.out.println("Список эпиков': " + taskManager.getAllTasks(TaskType.EPIC));
        System.out.println("Список подзадач: " + taskManager.getAllTasks(TaskType.SUBTASK));

        System.out.println("\nУдаляем одну из задач:");
        System.out.println("Было:");
        System.out.println("1 - " + taskManager.getTaskById(1, TaskType.TASK));
        taskManager.deleteTaskById(1, TaskType.TASK);

        System.out.println("\nРезультат - задача удалена:");
        System.out.println("1 - " + taskManager.getTaskById(1, TaskType.TASK));

        System.out.println("\nУдяляем один из эпиков:");
        System.out.println("Было:");
        System.out.println("3 - " + taskManager.getTaskById(3, TaskType.EPIC));
        System.out.println("5 - " + taskManager.getTaskById(5, TaskType.SUBTASK));
        System.out.println("6 - " + taskManager.getTaskById(6, TaskType.SUBTASK));
        taskManager.deleteTaskById(3, TaskType.EPIC);

        System.out.println("\nРезультат - эпик удален, подзадачи автоматически перемещены в эпик по умолчанию:");
        System.out.println("3 - " + taskManager.getTaskById(3, TaskType.EPIC));
        System.out.println("5 - " + taskManager.getTaskById(5, TaskType.SUBTASK));
        System.out.println("6 - " + taskManager.getTaskById(6, TaskType.SUBTASK));

    }
}
