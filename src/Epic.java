import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> includedSubtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public void addSubtask(int id) {
        if(super.getId() == id){
            System.out.println("Задачу нельзя добавить саму в себя");
        }
        includedSubtasks.add(id);
    }

    public void transferSubtasks(List<Integer> newSubtasks) {
        includedSubtasks = newSubtasks;
    }

    public void removeSubtask(Integer removedId) {
        includedSubtasks.remove(removedId);
    }

    public List<Integer> getSubtasks() {
        return includedSubtasks;
    }

    @Override
    public String toString() {
        return "Epic{" + super.toString() +
                ", includedSubtasks = " + includedSubtasks +
                '}';
    }
}
