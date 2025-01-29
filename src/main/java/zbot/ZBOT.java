package zbot;

import zbot.exceptions.ZBOTFileNotFoundException;
import zbot.exceptions.IncorrectInputException;
import zbot.exceptions.InvalidTaskException;
import zbot.exceptions.InvalidTaskNumberException;
import zbot.exceptions.EmptyTaskListException;

import zbot.tasks.TaskList;

import java.util.Scanner;
import java.io.IOException;

public class ZBOT {
    private final StorageManager storage;
    private TaskList taskList;
    private final Ui ui;

    public ZBOT(String filePath) {
        this.storage = new StorageManager(filePath);
        this.ui = new Ui();
        try {
            this.taskList = new TaskList(storage.loadExistingFile());
        }  catch (ZBOTFileNotFoundException | IOException e){
            ui.generateResponse("loadingError");
            this.taskList = new TaskList();

        }
    }

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
                    Parser.parseInput(input,ui,taskList);
                } catch (InvalidTaskException | IncorrectInputException | EmptyTaskListException |
                         InvalidTaskNumberException e) {
                    System.out.println("---------------------------------------------------");
                    System.out.println(e.getMessage());
                    System.out.println("---------------------------------------------------");
                }
            }
        }

        try {
            storage.saveToFile(taskList);
        } catch (IOException e) {
            System.out.println("-----------------------");
            System.out.println(e.getMessage());
            System.out.println("-----------------------");
        }

        ui.generateResponse("end");

    }

    public static void main(String[] args) {
        new ZBOT("./data/ZBOT.txt").run();
    }
}

