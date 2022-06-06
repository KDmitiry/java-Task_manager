package ru.practicum.tracker.exceptions;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(final String messages) {
        super(messages);
    }
}
