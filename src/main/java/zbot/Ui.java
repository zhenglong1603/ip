package zbot;

import zbot.tasks.Task;
import zbot.tasks.TaskList;

/**
 * Represents a class that generates responses for the bot.
 *
 * The `Ui` class is responsible for interacting with the user by displaying messages,
 * responses, and other output to the console. It provides methods to show feedback to
 * the user, such as task-related messages, error messages, and general information about
 * the application's status.
 *
 * This class helps to separate the user interface logic from other components like task management
 * and data storage, enabling a clear structure for handling user interactions.
 */
class Ui {
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
     * Displays the list of tasks in the task list to the user.
     *
     * This method iterates through all the tasks in the given `TaskList` and prints
     * each task with its respective index number in the console. It includes a header
     * and footer to make the output more readable.
     *
     * @param taskList the TaskList object containing the tasks to be displayed
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

}
