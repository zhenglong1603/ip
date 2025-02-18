package zbot.commands;

import zbot.StorageManager;
import zbot.exceptions.IncorrectInputException;
import zbot.tasks.TaskList;

/**
 * Represents a command to add a "ToDo" task to the task list.
 * The task is added with the specified description.
 */
public class ToDoCommand implements Command {
    private final String description;

    /**
     * Constructs a {@code ToDoCommand} with the specified description.
     *
     * @param description The description of the "ToDo" task to be added.
     */
    public ToDoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the "ToDo" command by adding a "ToDo" task with the specified description
     * to the task list.
     *
     * @param taskList The task list where the "ToDo" task will be added.
     * @param storage  The storage manager to handle file operations (not used in this command).
     * @return A message indicating that the task has been added, along with the current task count.
     * @throws IncorrectInputException If the input is incorrect or if the task description is invalid.
     */
    @Override
    public String execute(TaskList taskList, StorageManager storage)
            throws IncorrectInputException {
        String result = taskList.addContent("todo", description);
        if (result.startsWith("Sorry!!")) {
            return result;
        }
        return "Got it! Another task added to your plate!\n" + result
                + "\nYou have " + taskList.getSize() + " tasks in the list. Let's go!!";
    }
}
