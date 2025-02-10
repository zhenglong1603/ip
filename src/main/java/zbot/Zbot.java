package zbot;

import java.io.IOException;

import zbot.exceptions.EmptyTaskListException;
import zbot.exceptions.IncorrectInputException;
import zbot.exceptions.InvalidCommandException;
import zbot.exceptions.InvalidTaskNumberException;
import zbot.exceptions.ZbotFileNotFoundException;
import zbot.tasks.TaskList;

/**
 * Main class of the program
 */
public class Zbot {
    private final StorageManager storage;
    private TaskList taskList;

    /**
     * Zbot initialize
     */
    public Zbot(String filePath) {
        this.storage = new StorageManager(filePath);

        try {
            this.taskList = new TaskList(storage.loadExistingFile());
        } catch (ZbotFileNotFoundException | IOException e) {
            this.taskList = new TaskList();
        }
    }

    /**
     * Starts and runs the bot, continuously accepting user input and processing commands.
     * This method initializes the bot, displays the starting response, and enters a loop to
     * read user input until the user types "bye". For each input, it tries to parse and execute
     * the command using the {@link Parser}. If an exception occurs during parsing (e.g.,
     * invalid command or empty task list), an error message is displayed. The method ensures
     * that the task list is saved to a file before the program ends and then displays an ending response.
     *
     */
    public String getResponse(String s) {
        try {
            return Parser.parseInput(s, taskList, storage);
        } catch (InvalidCommandException | IncorrectInputException | EmptyTaskListException
                 | InvalidTaskNumberException exception) {
            return exception.getMessage();
        }
    }
}
