package ru.practicum.Tracker.controllers;

import ru.practicum.Tracker.model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> nodes = new HashMap<>();
    private final CustomLinkedList taskCustomList = new CustomLinkedList();

    public void addTask(Task task) {
        int idTask = task.getId();
        if (nodes.containsKey(idTask)) {
            remove(idTask);
        }
        taskCustomList.linkLast(task);
        nodes.put(idTask, taskCustomList.tail);
    }

    public List<Task> getHistory() {
        return taskCustomList.getTasks();
    }

        public void remove(int id) {
        if (nodes.containsKey(id)) {
            taskCustomList.removeNode(nodes.get(id));
            nodes.remove(id);
        }
    }

    public static class CustomLinkedList {
        private Node head;
        private Node tail;

        public void linkLast(Task task) {
            final Node old = tail;
            final Node newNode = new Node(old, task, null);
            tail = newNode;
            if (old == null)
                head = newNode;
            else
                old.next = newNode;
        }

        public void removeNode(Node node) {
            if (node.prev == null && node.next == null) {
                tail = null;
                head = null;
            } else if (node.prev == null) {
                head = node.next;
                node.next.prev = null;
            } else if (node.next == null) {
                node.prev.next = null;
                tail = node.prev;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
        }

        public List<Task> getTasks() {
            Node node = head;
            List<Task> history = new ArrayList<>();
            while (node != null) {
                history.add(node.data);
                node = node.next;
            }
            return history;
        }
    }

}



