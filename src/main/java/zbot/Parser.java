package zbot;

import java.io.IOException;
import java.util.List;

import zbot.exceptions.EmptyTaskListException;
import zbot.exceptions.IncorrectInputException;
import zbot.exceptions.InvalidCommandException;
import zbot.exceptions.InvalidTaskNumberException;
import zbot.tasks.Task;
import zbot.tasks.TaskList;

/**
 * Represents a class that handles input parsing and executes the corresponding logic.
 * <p>
 * This class is responsible for:
 * <ul>
 *   <li>Parsing user input commands or data</li>
 *   <li>Executing the appropriate actions based on the parsed input</li>
 *   <li>Managing the flow of operations in response to the input</li>
 * </ul>
 * The class may interface with other components of the application to perform tasks, such as
 * task management, file handling, or displaying results.
 */
class Parser {
    /**
     * This method processes the user's input and triggers the appropriate actions based on the command.
     * It interacts with the UI and task list to carry out the task.
     *
     * @param input the input string entered by the user, containing the command and related arguments
     * @param taskList the list of tasks to be managed and modified based on the input
     * @param storage the UI object used to display messages and interact with the user
     * @throws IncorrectInputException if the input does not follow the correct format
     * @throws InvalidCommandException if the command entered by the user does not exist or is not recognized
     * @throws EmptyTaskListException if the task list is empty and a task operation is attempted
     * @throws InvalidTaskNumberException if the task number is less than 0 or greater
     *      than or equal to the size of the task list
     */
    public static String parseInput(String input, TaskList taskList, StorageManager storage)
            throws IncorrectInputException, InvalidCommandException,
            EmptyTaskListException, InvalidTaskNumberException {
        StringBuilder output = new StringBuilder();
        String[] parts = input.split(" ", 2);
        final String supportedCommands =
                "- list\n- mark\n- unmark\n- find\n- delete\n- todo\n- deadline\n- event\n- save";
        switch (parts[0]) {
        case "list":
            if (parts.length == 1) {
                output.append("Here are the tasks in your list:\n");
                int index = 1;
                for (Task t : taskList.getTaskList()) {
                    output.append(index).append(".").append(t.toString()).append("\n");
                    index++;
                }
            } else {
                throw new IncorrectInputException(
                        "Sorry!! Please ensure your command matches the following example and have nothing else. "
                                + "(e.g. \"list\")");
            }
            break;
        case "delete":
            if (parts.length != 2) {
                throw new IncorrectInputException(
                        "Sorry!! Please ensure your command matches the example: \"delete 1\"");
            }
            int deleteIndex = getIndex(parts, taskList);
            output.append("OK, I've removed this task from the list:\n");
            output.append(taskList.deleteContent(deleteIndex)).append("\n");
            output.append("Now you have ").append(taskList.getSize()).append(" tasks in the list.\n");
            break;
        case "mark":
            if (parts.length != 2) {
                throw new IncorrectInputException(
                        "Sorry!! Please ensure your command matches the example: \"mark 1\"");
            }
            int markIndex = getIndex(parts, taskList);
            output.append("Nice! I've marked this task as done:\n");
            output.append(taskList.markTask(markIndex)).append("\n");
            break;
        case "unmark":
            if (parts.length != 2) {
                throw new IncorrectInputException(
                        "Sorry!! Please ensure your command matches the example: \"unmark 1\"");
            }
            int unmarkIndex = getIndex(parts, taskList);
            output.append("OK, I've marked this task as not done yet:\n");
            output.append(taskList.unmarkTask(unmarkIndex)).append("\n");
            break;
        case "todo":
            if (parts.length == 2) {
                String result = taskList.addContent(parts[0], parts[1]);

                if (result.startsWith("Sorry!!")) {
                    output.append(result).append("\n");
                } else {
                    output.append("Got it. I've added this task:\n");
                    output.append(result).append("\n");
                    output.append("Now you have ").append(taskList.getSize()).append(" tasks in the list.\n");
                }

            } else {
                throw new IncorrectInputException(
                        "Sorry!! Please ensure your command matches the following example and "
                                + "has a description after your command. "
                                + "(e.g. \"todo read a book\")");
            }
            break;
        case "deadline":
            if (parts.length == 2) {
                if (parts[1].contains("/by")) {
                    String result = taskList.addContent(parts[0], parts[1]);
                    if (result.startsWith("Sorry!!")) {
                        output.append(result).append("\n");
                    } else {
                        output.append("Got it. I've added this task:\n");
                        output.append(result).append("\n");
                        output.append("Now you have ").append(taskList.getSize()).append(" tasks in the list.\n");
                    }
                } else {
                    throw new IncorrectInputException("Sorry!! Please ensure your deadline command has "
                            + "the deadline specified after /by. "
                            + "(e.g. \"deadline description /by \"deadline_date\" \")");
                }
            } else {
                throw new IncorrectInputException("Sorry!! Please ensure your command matches the following example"
                        + "and has a description and deadline after your command. "
                        + "(e.g. \"deadline description /by \"deadline_date\" \")");
            }
            break;
        case "event":
            if (parts.length == 2) {
                if (parts[1].contains("/from") && parts[1].contains("/to")) {
                    String result = taskList.addContent(parts[0], parts[1]);
                    if (result.startsWith("Sorry!!")) {
                        output.append(result).append("\n");
                    } else {
                        output.append("Got it. I've added this task:\n");
                        output.append(result).append("\n");
                        output.append("Now you have ").append(taskList.getSize()).append(" tasks in the list.\n");
                    }
                } else {
                    throw new IncorrectInputException("Sorry!! Please ensure your event command "
                            + "has both a start time (/from) and an end time (/to). "
                            + "(e.g. \"event description /from \"start_time\" /to \"end_time\" \")");
                }
            } else {
                throw new IncorrectInputException("Sorry!! Please ensure your command matches "
                        + "the following example and has a description with the timeline after your command. "
                        + "(e.g. \"event description /from \"start_time\" /to \"end_time\" \")");
            }
            break;
        case "find":
            if (parts.length == 2) {
                List<Task> tasks = taskList.findTasks(parts[1]);
                int index = 1;
                output.append("Here are the matching tasks in your list:\n");
                for (Task t : tasks) {
                    output.append(index).append(".").append(t.toString()).append("\n");
                    index++;
                }
            } else {
                throw new IncorrectInputException("Sorry!! Please ensure your command"
                        + "matches the following example" + " (e.g. \"find book\")");
            }
            break;
        case "save":
            try {
                storage.saveToFile(taskList);
                output.append("successfully saved!");
            } catch (IOException e) {
                output.append(e.getMessage()).append("\n");
            }
            break;
        default:
            throw new InvalidCommandException("Sorry!! I didn't recognise that request. These are the "
                    + "following supported commands:\n" + supportedCommands);
        }
        return output.toString();
    }

    /**
     * Retrieves the index of a task from the task list based on the input provided by the user.
     * <p>
     * This method attempts to parse the task number from the input string, checks if the task list is empty,
     * and verifies that the task number is within a valid range. If any of these checks fail, an appropriate
     * exception is thrown. The index is adjusted to be 0-based (i.e., the first task is index 0).
     * </p>
     *
     * @param parts an array of strings representing the command and its arguments. The second element
     *              (index 1) is expected to be the task number to mark or unmark.
     * @param taskList the task list containing tasks. This is used to check the number of tasks and validate
     *                 the given task number.
     * @return the 0-based index of the task in the list.
     * @throws IncorrectInputException if the task number is not a valid integer, or if it is missing in
     *                                  the command (e.g., "mark" without specifying a task number).
     * @throws EmptyTaskListException if the task list is empty, indicating there are no tasks to mark or unmark.
     * @throws InvalidTaskNumberException if the task number is less than 1 or greater than or equal to the
     *                                     number of tasks in the task list.
     */
    public static int getIndex(String[] parts, TaskList taskList) throws
            IncorrectInputException, EmptyTaskListException, InvalidTaskNumberException {
        int taskIndex;
        try {
            taskIndex = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IncorrectInputException(
                    "Sorry!! The task number must be a valid integer (e.g., \"mark 1\").");
        }

        if (taskList.getSize() == 0) {
            throw new EmptyTaskListException(
                    "Sorry!! The current task list is empty. Please add some tasks first.");
        }

        if (taskIndex < 0 || taskIndex >= taskList.getSize()) {
            throw new InvalidTaskNumberException(
                    "Sorry!! The task number is invalid. Please check the task number again.");
        }
        return taskIndex;
    }
}
