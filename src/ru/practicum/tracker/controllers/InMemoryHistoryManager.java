package ru.practicum.tracker.controllers;

import ru.practicum.tracker.model.Node;
import ru.practicum.tracker.model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private static final Map<Integer, Node> nodes = new HashMap<>();
    private final Nodes history = new Nodes();

    public List<Task> getHistory() {
        return history.getTasks();
    }

    @Override
    public void addTask(Task task) {
        if (task == null) {
            return;
        } else {
            int idTask = task.getId();
            if (nodes.containsKey(idTask)) {
                Node node = nodes.get(idTask);
                history.removeNode(node);
                history.linkLast(task);
            } else {
                history.linkLast(task);
            }
        }
    }

    @Override
    public void remove(int id) {
        if (nodes.containsKey(id)) {
            if (nodes.containsKey(id)) {
                history.removeNode(nodes.get(id));
                nodes.remove(id);
            }

        } else {
            System.out.println("Нет такой задачи в истории");
        }
    }

    public static class Nodes {
        public Node head;
        public Node tail;

        public void linkLast(Task task) {
            final Node oldLastNode = tail;
            final Node newNode = new Node(oldLastNode, task, null);
            tail = newNode;
            if (oldLastNode == null)
                head = newNode;
            else
                oldLastNode.setNext(newNode);
            nodes.put(task.getId(), newNode);
        }

        public void removeNode(Node node) {
            if (head == null && tail == null) {
                return;
            } else {
                assert head != null;
                if (head.getNext() == null && tail.getPrev() == null) {
                    head = tail = null;
                } else {
                    if (node.getPrev() == null) {
                        head = head.getNext();
                        head.setPrev(null);
                    } else if (node.getNext() == null) {
                        tail = tail.getPrev();
                        tail.setNext(null);
                    } else {
                        Node prevTaskNode = node.getPrev();
                        Node nextTaskNode = node.getNext();
                        prevTaskNode.setNext(nextTaskNode);
                        nextTaskNode.setPrev(prevTaskNode);
                    }
                }
            }
            nodes.remove(node.getData().getId());
        }

        public List<Task> getTasks() {
            List<Task> taskHistory = new ArrayList<>();
            Node node = head;
            while (node != null) {
                taskHistory.add(node.getData());
                node = node.getNext();
            }
            return taskHistory;
        }
    }
}



