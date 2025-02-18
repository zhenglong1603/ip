package zbot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import zbot.tasks.ToDoTask;

class ToDoTaskTest {

    @Test
    void testToDoTaskCreation() {
        ToDoTask task = new ToDoTask("Buy groceries");

        assertEquals("Buy groceries", task.getDescription());
        assertEquals("[T][ ] Buy groceries", task.toString());
    }

    @Test
    void testMarkAsDone() {
        ToDoTask task = new ToDoTask("Submit assignment");
        task.markDone();

        assertEquals("[T][X] Submit assignment", task.toString());
    }

    @Test
    void testMarkAsNotDone() {
        ToDoTask task = new ToDoTask("Go for a run");
        task.markDone();
        task.markUndone();

        assertEquals("[T][ ] Go for a run", task.toString());
    }
}
