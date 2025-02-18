package zbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import zbot.exceptions.IncorrectInputException;
import zbot.tasks.TaskList;

class ParserTest {
    // This code is adapted from a conversation with chatGPT
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void testTodoCommandValid() throws Exception {
        String input = "todo read a book";
        String expectedOutput = "Got it! Another task added to your plate!\n"
                + "[T][ ] read a book\n"
                + "You have 1 tasks in the list. Let's go!!";
        String actualResponse = Parser.parseInput(input, taskList, null);
        assertEquals(expectedOutput, actualResponse);
    }

    @Test
    void testTodoCommandInvalid() {
        String input = "todo ";
        assertThrows(IncorrectInputException.class, () -> {
            Parser.parseInput(input, taskList, null);
        });
    }

    @Test
    void testDeadlineCommandValid() throws Exception {
        String input = "deadline finish homework /by 2025-02-20";
        String expectedOutput = "Tick-tock! I've set your deadline!\n"
                + "[D][ ] finish homework (by: Feb 20 2025)\n"
                + "You have 1 tasks in the list. Let's go!!";
        String actualResponse = Parser.parseInput(input, taskList, null);
        assertEquals(expectedOutput, actualResponse);
    }

    @Test
    void testDeadlineCommandInvalidFormat() {
        String input = "deadline finish homework";
        assertThrows(IncorrectInputException.class, () -> {
            Parser.parseInput(input, taskList, null);
        });
    }

    @Test
    void testMarkCommandValid() throws Exception {
        String input = "todo read a book";
        Parser.parseInput(input, taskList, null);
        input = "mark 1";
        String expectedOutput = "Nice! One less thing to worry about!\n"
                + "[T][X] read a book";
        String actualResponse = Parser.parseInput(input, taskList, null);
        assertEquals(expectedOutput, actualResponse);
    }

    @Test
    void testMarkCommandInvalidIndex() {
        String input = "mark 100";
        assertThrows(zbot.exceptions.EmptyTaskListException.class, () -> {
            Parser.parseInput(input, taskList, null);
        });
    }

    @Test
    void testDeleteCommandValid() throws Exception {
        String input = "todo read a book";
        Parser.parseInput(input, taskList, null);
        input = "delete 1";
        String expectedOutput = "Poof! Gone like it never existed!\n"
                + "Task removed: [T][ ] read a book\n"
                + "You have 0 tasks in the list left! Let's go!!";
        String actualResponse = Parser.parseInput(input, taskList, null);
        assertEquals(expectedOutput, actualResponse);
    }

    @Test
    void testDeleteCommandInvalidIndex() {
        String input = "delete 100";
        assertThrows(zbot.exceptions.EmptyTaskListException.class, () -> {
            Parser.parseInput(input, taskList, null);
        });
    }

    @Test
    void testFindCommandValid() throws Exception {
        String input = "todo read a book";
        Parser.parseInput(input, taskList, null);
        input = "find book";
        String expectedOutput = "Detective mode activated! Here's what I found!\n"
                + "1. [T][ ] read a book\n";
        String actualResponse = Parser.parseInput(input, taskList, null);
        assertEquals(expectedOutput, actualResponse);
    }

    @Test
    void testFindCommandNoResults() throws Exception {
        String input = "find homework";
        String expectedOutput = "Detective mode activated! Here's what I found!\n";
        String actualResponse = Parser.parseInput(input, taskList, null);
        assertEquals(expectedOutput, actualResponse);
    }
    @Test
    void testInvalidCommand() {
        String input = "unknownCommand test";
        assertThrows(zbot.exceptions.InvalidCommandException.class, () -> {
            Parser.parseInput(input, taskList, null);
        });
    }

    @Test
    void testMarkWithEmptyTaskList() {
        String input = "mark 1";
        assertThrows(zbot.exceptions.EmptyTaskListException.class, () -> {
            Parser.parseInput(input, taskList, null);
        });
    }



}
