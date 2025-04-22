public class Managers {
    public TaskManager getDefault() {
        TaskManager taskManager = new InMemoryTaskManager();
        return taskManager;
    }

    public static HistoryManager getDefaultHistory() {
        HistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        return inMemoryHistoryManager;
    }
}
