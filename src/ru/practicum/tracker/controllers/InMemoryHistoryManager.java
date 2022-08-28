package ru.practicum.tracker.controllers;

import ru.practicum.tracker.model.Task;

import java.util.*;

public class InMemoryHistoryManager <T> implements HistoryManager {

    private static class Node {
        public Node next;
        public Node prev;
        public Task task;

        public Node(Node prev, Task task, Node next) {
            this.prev = prev;
            this.next = next;
            this.task = task;
        }
    }
    private Node head;
    private Node tail;
    private int listSize;
    private final HashMap<Integer, Node> nodes;
    public InMemoryHistoryManager() {
        nodes = new HashMap<>();
    }


    @Override
    public void addTask(Task task) {
            if (task != null) {
                Integer taskId = task.getId();
                if (nodes.containsKey(taskId)) {
                    removeNode(nodes.get(taskId));
                }
                Node node = linkLast(task);
                nodes.put(taskId, node);
            }
    }

    @Override
    public void remove(Integer id) {
        if (nodes.containsKey(id)) {
            Node nodeToRemove = nodes.get(id);
            removeNode(nodeToRemove);
            nodes.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private Node linkLast(Task task) {
        Node oldTail = this.tail;
        Node newNode = new Node(oldTail, task, null);
        this.tail = newNode;
        if (oldTail == null) {
            this.head = newNode;
        } else {
            oldTail.next = newNode;
        }
        this.listSize++;
        return newNode;
    }

    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node node = head;
        while (node != null) {
            tasks.add(node.task);
            node = node.next;
        }
        return tasks;
    }

    private void removeNode(Node node) {
        if (node != null) {
            if (node.prev != null) {
                node.prev.next = node.next;
            } else {
                this.head = node.next;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            } else {
                this.tail = node.prev;
            }
            this.listSize--;
        }
    }
}



