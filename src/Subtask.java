public class Subtask extends Task {
    private Status status = Status.NEW;
    private int motherEpic;

    public Subtask(int id, String name, String description){
        super(id, name, description);
        this.motherEpic=0;
    }

    public Subtask(int id, String name, String description, Status status, int motherEpic){
        super(id, name, description, status);
        this.motherEpic=motherEpic;
    }

    public void setMotherEpic(int newMotherEpic){
        motherEpic = newMotherEpic;
    }

    public int getMotherEpic(){
        return motherEpic;
    }

    @Override
    public String toString() {
        return "Subtask{" + super.toString() +
                ", motherEpic=" + motherEpic +
                '}';
    }
}
