import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    List<Task> history = new ArrayList<>();

    @Override
    public void addTask(Task task) {
        if (history.size() >= 10) {
            history.remove(1);
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
