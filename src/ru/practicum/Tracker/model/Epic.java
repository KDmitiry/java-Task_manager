package ru.practicum.Tracker.model;
import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subTasks;

    public Epic(Integer id, String name, String description, ArrayList<Integer> subTasks) {
        super(id, name, description, "new");
        this.subTasks = subTasks;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }

    public void addSubtask(Integer subtaskId) {
        this.subTasks.add(subtaskId);
    }

    public ArrayList<Integer> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(ArrayList<Integer> subTasks) {
        this.subTasks = subTasks;
    }

    @Override
    public String getTaskType() {
        return "epic";
    }
}
