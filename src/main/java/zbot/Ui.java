package zbot;

import zbot.tasks.Task;
import zbot.tasks.TaskList;

import java.util.List;

/**
 * Represents the user interface of ZBOT, responsible for interacting with the user.
 * <p>
 * The {@code Ui} class provides methods to display messages, task lists, and errors
 * in a structured manner, ensuring a clear separation between the user interface
 * and the logic handling tasks.
 */
class Ui {

    /**
     * Generates a response based on the given input command.
     *
     * @param input The command string to determine the response.
     */
    public void generateResponse(String input) {
        switch(input) {
        case "start":
                System.out.println("---------------------------------------------------");
                System.out.println("Hello! I'm ZBOT!!");
                System.out.println("What can I do for you?");
                System.out.println("---------------------------------------------------");
                break;
        case "end":
            System.out.println("---------------------------------------------------");
            System.out.println("Bye. Hope to see you again soon :)");
            System.out.println("---------------------------------------------------");
            break;
        case "loadingError":
            System.out.println("---------------------------------------------------");
            System.out.println("There was an issue loading the list");
            System.out.println("---------------------------------------------------");
            break;
        }
    }

    /**
     * Displays all the tasks currently stored in the given {@code TaskList}.
     *
     * @param taskList The {@code TaskList} object containing tasks to be displayed.
     */
    public void showContents(TaskList taskList) {
        System.out.println("---------------------------------------------------");
        System.out.println("Here are the tasks in your list:");
        int index = 1;
        for (Task task : taskList.getTaskList()) {
            System.out.println(index + ". " + task.toString());
            index++;
        }
        System.out.println("---------------------------------------------------");
    }

    /**
     * Displays tasks that match a given search keyword.
     *
     * @param taskList A list of {@code Task} objects that match the search query.
     */
    public void displayFind(List<Task> taskList) {
        System.out.println("---------------------------------------------------");
        System.out.println("Here are the tasks that matches the keyword:");
        int index = 1;
        for (Task task : taskList) {
            System.out.println(index + ". " + task.toString());
            index++;
        }
        System.out.println("---------------------------------------------------");
    }
}
