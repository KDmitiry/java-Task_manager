package ru.practicum.tracker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.practicum.tracker.model.TaskStatus.NEW;

public class Epic extends Task {

    private List<Integer> subTasks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTasks, epic.subTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasks);
    }

    public Epic(Integer id, String name, String description) {
        super(id, name, description, NEW);
        subTasks = new ArrayList<>();
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


    public List<Integer> getSubTasksIds() {
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
