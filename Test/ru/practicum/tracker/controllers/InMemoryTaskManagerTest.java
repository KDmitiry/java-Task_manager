package ru.practicum.tracker.controllers;

import org.junit.jupiter.api.BeforeEach;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    void init() {
    taskManager = new InMemoryTaskManager();
    super.init();
    }

}

