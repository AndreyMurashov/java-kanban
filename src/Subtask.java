public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int epicId, Status status) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public void setEpicId(int newEpicId) {
        if(this.epicId == newEpicId){
            System.out.println("Задача не может быть присвоена сама себе");
        }
        epicId = newEpicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" + super.toString() +
                ", epicId = " + epicId +
                '}';
    }
}
