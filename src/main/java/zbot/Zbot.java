package zbot;

import java.io.IOException;
import java.util.Scanner;

import zbot.exceptions.EmptyTaskListException;
import zbot.exceptions.IncorrectInputException;
import zbot.exceptions.InvalidCommandException;
import zbot.exceptions.InvalidTaskNumberException;
import zbot.exceptions.ZbotFileNotFoundException;
import zbot.tasks.TaskList;

/**
 * Main class of the program
 */
class Zbot {
    private final StorageManager storage;
    private TaskList taskList;
    private final Ui ui;

    public Zbot(String filePath) {
        this.storage = new StorageManager(filePath);
        this.ui = new Ui();
        try {
            this.taskList = new TaskList(storage.loadExistingFile());
        } catch (ZbotFileNotFoundException | IOException e) {
            ui.generateResponse("loadingError");
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
    public void run() {
        ui.generateResponse("start");
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        String input;

        while (isRunning) {
            input = scanner.nextLine();

            if (input.equals("bye")) {
                isRunning = false;
            } else {
                try {
                    Parser.parseInput(input, ui, taskList);
                } catch (InvalidCommandException | IncorrectInputException | EmptyTaskListException
                         | InvalidTaskNumberException exception) {
                    ui.printResponse(exception.getMessage());
                }
            }
        }

        try {
            storage.saveToFile(taskList);
        } catch (IOException exception) {
            ui.printResponse(exception.getMessage());
        }

        ui.generateResponse("end");
    }

    public static void main(String[] args) {
        new Zbot("./data/Zbot.txt").run();
    }
}

