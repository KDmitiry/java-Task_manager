package ru.practicum.tracker.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class Epic extends Task {


    private List<Subtask> subTasks;
    private LocalDateTime endEpicTime;

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                ", duration=" + getDuration() +
                '}';
    }

    public Epic(Integer id, String name, String description, TaskStatus status, LocalDateTime startTime, long duration) {
        super(id, name, description, status, startTime, duration);
        this.subTasks = new ArrayList<>();

    }
    public List<Subtask> getListSubtask() {
        return subTasks;
    }

    public LocalDateTime getEndEpicTime() {
        return this.endEpicTime;
    }
    public void setEndTimeEpic(LocalDateTime endTime) {
        this.endEpicTime = endTime;
    }

    public void setListSubTask(Subtask subtask) {
        this.subTasks.add(subtask);
    }

    public List<Subtask> getListSubTask() {
        return subTasks;
    }

    public void deleteSubTask(Subtask subtask) {
        this.subTasks.remove(subtask);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTasks, epic.subTasks) && Objects.equals(endEpicTime, epic.endEpicTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasks, endEpicTime);
    }

    public void addSubtask(Subtask subtaskId) {
        this.subTasks.add(subtaskId);
    }

    public void setSubTasks(ArrayList<Subtask> subTasks) {
        this.subTasks = subTasks;
    }

    @Override
    public String getTaskType() {
        return "epic";
    }
}
