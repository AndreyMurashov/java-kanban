import java.util.ArrayList;

public class Epic extends Task {
    private Status status = Status.NEW;
    private ArrayList<Integer> includedSubtasks = new ArrayList<>();

    public Epic(int id, String name, String description){
        super(id, name, description);
    }

    public Epic(int id, String name, String description, Status status){
        super(id, name, description, status);
    }

    public void addSubtask(int id){
        includedSubtasks.add(id);
    }

    public void removeSubtask(Integer removedId){
        includedSubtasks.remove(removedId);
    }

    public ArrayList<Integer> getSubtasks(){
        return includedSubtasks;
    }
}
