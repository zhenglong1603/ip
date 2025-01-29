package zbot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zbot.tasks.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * To test the commands input and their expected outputs.
 */
public class TaskCommandTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final TaskList tasks = new TaskList();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        tasks.clearTasks();
    }

    /**
     * Tests the expected output for marking a task as done.
     *
     * This method verifies that the system correctly outputs
     * the expected message when a task is marked as completed.
     */
    @Test
    public void markTaskSuccess() {
        Task t = new ToDoTask("test");
        List<Task> tasklist = new ArrayList<>();
        tasklist.add(t);
        TaskList tl = new TaskList(tasklist);

        tl.markTask(0);

        String expected = String.join(System.lineSeparator(),
                "---------------------------------------------------",
                "Nice! I've marked this task as done:",
                "[T][X] test",
                "---------------------------------------------------"
        );

        assertEquals(expected.trim(), outputStreamCaptor.toString().trim());
    }

    /**
     * Tests the expected output for marking a task as done and undone.
     *
     * This method verifies that the system correctly outputs
     * the expected message when a task is marked as completed
     * and unmarked as uncompleted.
     */
    @Test
    public void unmarkTaskSuccess() {
        Task t = new ToDoTask("test");
        List<Task> tasklist = new ArrayList<>();
        tasklist.add(t);
        TaskList tl = new TaskList(tasklist);

        tl.markTask(0);
        tl.unmarkTask(0);

        String expected = String.join(System.lineSeparator(),
                "---------------------------------------------------",
                "Nice! I've marked this task as done:",
                "[T][X] test",
                "---------------------------------------------------",
                "---------------------------------------------------",
                "OK, I've marked this task as not done yet:",
                "[T][ ] test",
                "---------------------------------------------------"
        );

        // Assert
        assertEquals(expected.trim(), outputStreamCaptor.toString().trim());
    }
}
