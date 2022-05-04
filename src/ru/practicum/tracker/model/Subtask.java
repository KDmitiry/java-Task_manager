package ru.practicum.tracker.model;

import java.util.Objects;

public class Subtask extends Task {

    private int epicId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    public Subtask(Integer id, String name, String description, TaskStatus status, int epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" + "id=" + getId() + ", name='" + getName() + '\'' + ", description='" + getDescription() + '\'' + ", status='" + getStatus() + '\'' + '}';
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String getTaskType() {
        return "subtask";
    }
}
