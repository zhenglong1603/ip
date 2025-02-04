package zbot;

import java.util.List;

import zbot.tasks.Task;
import zbot.tasks.TaskList;

/**
 * Represents the user interface of ZBOT, responsible for interacting with the user.
 * <p>
 * The class provides methods to display messages, task lists, and errors
 * in a structured manner, ensuring a clear separation between the user interface
 * and the logic handling tasks.
 */
public class Ui {

    /**
     * Generates a response based on the given input command.
     *
     * @param input The command string to determine the response.
     */
    public void generateResponse(String input) {
        switch(input) {
        case "start":
            printResponse("Hello! I'm ZBOT!!", "What can I do for you?");
            break;
        case "end":
            printResponse("Bye. Hope to see you again soon :)");
            break;
        case "loadingError":
            printResponse("There was an issue loading the list");
            break;
        }
    }

    /**
     * Displays all the tasks currently stored in the given {@code TaskList}.
     *
     * @param taskList The {@code TaskList} object containing tasks to be displayed.
     */
    public void showContents(TaskList taskList) {
        printLine();
        System.out.println("Here are the tasks in your list:");
        int index = 1;
        for (Task task : taskList.getTaskList()) {
            System.out.println(index + ". " + task.toString());
            index++;
        }
        printLine();
    }

    /**
     * Displays tasks that match a given search keyword.
     *
     * @param taskList A list of {@code Task} objects that match the search query.
     */
    public void displayFind(List<Task> taskList) {
        printLine();
        System.out.println("Here are the tasks that matches the keyword:");
        int index = 1;
        for (Task task : taskList) {
            System.out.println(index + ". " + task.toString());
            index++;
        }
        printLine();
    }

    /**
     * Prints a single response message, with lines above and below it for separation.
     *
     * @param response The message to be printed.
     */
    public void printResponse(String response) {
        printLine();
        System.out.println(response);
        printLine();
    }

    /**
     * Prints two response messages, with lines above and below them for separation.
     *
     * @param response1 The first message to be printed.
     * @param response2 The second message to be printed.
     */
    public void printResponse(String response1, String response2) {
        printLine();
        System.out.println(response1);
        System.out.println(response2);
        printLine();
    }

    /**
     * Prints three response messages, with lines above and below them for separation.
     *
     * @param response1 The first message to be printed.
     * @param response2 The second message to be printed.
     * @param response3 The third message to be printed.
     */
    public void printResponse(String response1, String response2, String response3) {
        printLine();
        System.out.println(response1);
        System.out.println(response2);
        System.out.println(response3);
        printLine();
    }

    /**
     * Prints three response messages, with lines above and below them for separation.
     *
     * @param task The task to be printed.
     * @param size The size of the task list.
     */
    public void printTaskResponse(Task task, int size) {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.printf("Now you have %d tasks in the list.%n", size);
        printLine();
    }

    /**
     * Prints a line of dashes for formatting purposes.
     */
    public void printLine() {
        System.out.println("---------------------------------------------------");
    }
}
