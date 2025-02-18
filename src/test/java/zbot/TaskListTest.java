package zbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import zbot.tasks.Task;
import zbot.tasks.TaskList;

class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void testAddTodoTask() {
        String taskDescription = "Buy groceries";
        String result = taskList.addContent("todo", taskDescription);

        assertEquals("[T][ ] Buy groceries", result);
        assertEquals(1, taskList.getSize());
    }

    @Test
    void testAddEventTask() {
        String taskDescription = "Team meeting /from 2025-02-20 /to 2025-02-21";
        String result = taskList.addContent("event", taskDescription);

        assertTrue(result.contains("Team meeting"));
        assertTrue(result.contains("from: Feb 20 2025"));
        assertEquals(1, taskList.getSize());
    }

    @Test
    void testAddDeadlineTask() {
        String taskDescription = "Submit assignment /by 2025-02-25";
        String result = taskList.addContent("deadline", taskDescription);

        assertTrue(result.contains("Submit assignment"));
        assertTrue(result.contains("by: Feb 25 2025"));
        assertEquals(1, taskList.getSize());
    }

    @Test
    void testAddInvalidTask() {
        String taskDescription = "Complete homework";
        String result = taskList.addContent("homework", taskDescription);

        assertEquals("Sorry!! I didn't recognise that task type. Please use 'todo', 'event', or 'deadline'.", result);
        assertEquals(0, taskList.getSize());
    }

    @Test
    void testDeleteTask() {
        taskList.addContent("todo", "Buy groceries");
        taskList.addContent("todo", "Clean the house");

        String result = taskList.deleteContent(0);

        assertEquals("[T][ ] Buy groceries", result);
        assertEquals(1, taskList.getSize());
    }

    @Test
    void testMarkTask() {
        taskList.addContent("todo", "Buy groceries");

        String result = taskList.markTask(0);

        assertTrue(result.contains("[X] Buy groceries"));
    }

    @Test
    void testUnmarkTask() {
        taskList.addContent("todo", "Buy groceries");
        taskList.markTask(0);

        String result = taskList.unmarkTask(0);

        assertTrue(result.contains("[ ] Buy groceries"));
    }

    @Test
    void testFindTasks() {
        taskList.addContent("todo", "Buy groceries");
        taskList.addContent("todo", "Buy fruits");

        List<Task> foundTasks = taskList.findTasks("fruits");

        assertEquals(1, foundTasks.size());
        assertTrue(foundTasks.get(0).getDescription().contains("fruits"));
    }

    @Test
    void testRestoreState() {
        taskList.addContent("todo", "Buy groceries");
        taskList.addContent("todo", "Clean the house");

        taskList.deleteContent(0);

        boolean restored = taskList.restore();

        assertTrue(restored);
        assertEquals(2, taskList.getSize()); // Ensure task list is restored to previous state
    }

    @Test
    void testUndoWithEmptyHistory() {
        boolean restored = taskList.restore();
        assertFalse(restored); // No history to restore
    }

    @Test
    void testGetSize() {
        taskList.addContent("todo", "Buy groceries");
        taskList.addContent("event", "Team meeting /from 2025-02-20 /to 2025-02-21");

        int size = taskList.getSize();

        assertEquals(2, size);
    }
}
