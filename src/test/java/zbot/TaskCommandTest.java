package zbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import zbot.exceptions.EmptyTaskListException;
import zbot.exceptions.IncorrectInputException;
import zbot.exceptions.InvalidCommandException;
import zbot.exceptions.InvalidTaskNumberException;
import zbot.tasks.Task;
import zbot.tasks.TaskList;
import zbot.tasks.ToDoTask;

/**
 * To test the commands input and their expected outputs.
 */
public class TaskCommandTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final TaskList tasks = new TaskList();

    private final Ui ui = new Ui();


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        tasks.clearTasks();
    }

    /**
     * Tests the expected output for marking a task as done.
     * This method verifies that the system correctly outputs
     * the expected message when a task is marked as completed.
     */
    @Test
    public void markTaskSuccess() {
        Task t = new ToDoTask("test");
        List<Task> tasklist = new ArrayList<>();
        tasklist.add(t);
        TaskList tl = new TaskList(tasklist);

        tl.markTask(0, ui);

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

        tl.markTask(0, ui);
        tl.unmarkTask(0, ui);

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
        assertEquals(expected.trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    public void invalidInput() {
        // Simulate an invalid command
        Exception thrownException = null;
        try {
            Parser.parseInput("hi", ui, tasks);
        } catch (IncorrectInputException | InvalidCommandException | EmptyTaskListException
                 | InvalidTaskNumberException e) {
            thrownException = e;
        }

        assertNotNull(thrownException, "Expected IncorrectInputException to be thrown.");
        assertEquals("Sorry!! I didn't recognise that request. These are the "
                        + "following supported commands:\n"
                        + "- list\n- mark\n- unmark\n- find\n- delete\n- todo\n- deadline\n- event\n- bye",
                thrownException.getMessage());
    }

    @Test
    public void todoInput() {
        // Simulate an invalid command
        Exception thrownException = null;
        try {
            Parser.parseInput("todo", ui, tasks);
        } catch (IncorrectInputException | InvalidCommandException | EmptyTaskListException
                 | InvalidTaskNumberException e) {
            thrownException = e;
        }

        assertNotNull(thrownException, "Expected IncorrectInputException to be thrown.");
        assertEquals(
                "Sorry!! Please ensure your command matches the following example "
                        + "and has a description after your command. "
                        + "(e.g. \"todo read a book\")",
                thrownException.getMessage());
    }
}
