package zbot;

import zbot.commands.Command;
import zbot.commands.DeadlineCommand;
import zbot.commands.DeleteCommand;
import zbot.commands.EventCommand;
import zbot.commands.FindCommand;
import zbot.commands.ListCommand;
import zbot.commands.MarkCommand;
import zbot.commands.SaveCommand;
import zbot.commands.ToDoCommand;
import zbot.commands.UndoCommand;
import zbot.commands.UnmarkCommand;
import zbot.exceptions.EmptyTaskListException;
import zbot.exceptions.IncorrectInputException;
import zbot.exceptions.InvalidCommandException;
import zbot.exceptions.InvalidTaskNumberException;
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
     * Parses the user input and executes the corresponding command.
     *
     * @param input The raw input string from the user.
     * @param taskList The task list that stores tasks.
     * @param storage The storage manager responsible for saving and loading tasks.
     * @return The result of executing the command.
     * @throws Exception If an error occurs while processing the command.
     */
    public static String parseInput(String input, TaskList taskList, StorageManager storage) throws Exception {
        Command command = CommandFactory.getCommand(input, taskList);
        return command.execute(taskList, storage);
    }

    /**
     * A factory class that parses user input and returns the appropriate command object.
     */
    public class CommandFactory {

        /**
         * Parses the user input and returns the corresponding command object.
         *
         * @param input The raw input string from the user.
         * @param taskList The task list that stores tasks.
         * @return A Command object corresponding to the user's input.
         * @throws Exception If the input is invalid or the command is not recognized.
         */
        public static Command getCommand(String input, TaskList taskList) throws Exception {
            String[] parts = input.split(" ", 2);
            final String supportedCommands =
                    "- list\n- mark\n- unmark\n- find\n- delete\n- todo\n- deadline\n- event\n- save\n- undo";
            switch (parts[0]) {
            case "list":
                return new ListCommand();
            case "delete":
                if (parts.length != 2) {
                    throw new IncorrectInputException(
                            "Sorry!! Please ensure your command "
                                    + "matches the following example "
                                    + "(e.g. \"delete 1\")");
                }
                int deleteIndex = getIndex(parts, taskList);
                return new DeleteCommand(deleteIndex);
            case "mark":
                if (parts.length != 2) {
                    throw new IncorrectInputException(
                            "Sorry!! Please ensure your command "
                                    + "matches the following example "
                                    + "(e.g. \"mark 1\")");
                }
                int markIndex = getIndex(parts, taskList);
                return new MarkCommand(markIndex);
            case "unmark":
                if (parts.length != 2) {
                    throw new IncorrectInputException(
                            "Sorry!! Please ensure your command "
                                    + "matches the following example "
                                    + "(e.g. \"unmark 1\")");
                }
                int unmarkIndex = getIndex(parts, taskList);
                return new UnmarkCommand(unmarkIndex);
            case "find":
                if (parts.length != 2) {
                    throw new IncorrectInputException(
                            "Sorry!! Please ensure your command "
                                    + "matches the following example "
                                    + "(e.g. \"find book\")");
                }
                return new FindCommand(parts[1]);
            case "todo":
                if (parts.length != 2) {
                    throw new IncorrectInputException(
                            "Sorry!! Please ensure your command matches the following example and "
                                    + "has a description after your command. "
                                    + "(e.g. \"todo read a book\")");
                }
                return new ToDoCommand(parts[1]);
            case "deadline":
                if (parts.length != 2) {
                    throw new IncorrectInputException(
                            "Sorry!! Please ensure your command matches the following example "
                                    + "and has a description and deadline after your command. "
                                    + "(e.g. \"deadline description /by \"deadline_date\" \")");
                }
                return new DeadlineCommand(parts[1]);
            case "event":
                if (parts.length != 2) {
                    throw new IncorrectInputException(
                            "Sorry!! Please provide a description along with the timeline. "
                                    + "Example: \"event description /from start_time /to end_time\"");
                }
                return new EventCommand(parts[1]);
            case "save":
                return new SaveCommand();
            case "undo":
                return new UndoCommand();
            default:
                throw new InvalidCommandException(
                        "Sorry!! That command flew right over my head.\n"
                                + "Here's a list of things of what I can do!\n"
                                + supportedCommands
                                + "\nTry one of those and we'll be back on track!"
                );
            }
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
         *                 (index 1) is expected to be the task number to mark or unmark.
         * @param taskList the task list containing tasks. This is used to check the number of tasks and validate
         *                 the given task number.
         * @return the 0-based index of the task in the list.
         * @throws IncorrectInputException if the task number is not a valid integer, or if it is missing in
         *                                    the command (e.g., "mark" without specifying a task number).
         * @throws EmptyTaskListException if the task list is empty, indicating there are no tasks to mark or unmark.
         * @throws InvalidTaskNumberException if the task number is less than 1 or greater than or equal to the
         *                                    number of tasks in the task list.
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
}
