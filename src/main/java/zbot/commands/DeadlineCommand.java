package zbot.commands;

import zbot.StorageManager;
import zbot.exceptions.IncorrectInputException;
import zbot.tasks.TaskList;

/**
 * Represents a command to add a deadline task to the task list.
 */
public class DeadlineCommand implements Command {
    private final String description;

    /**
     * Constructs a DeadlineCommand with the specified description.
     *
     * @param description The description of the deadline task, which should include the "/by" keyword for the deadline.
     */
    public DeadlineCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the "Deadline" command by adding a deadline task to the task list.
     * The task description must include the "/by" keyword to specify the deadline.
     *
     * @param taskList The task list where the deadline task will be added.
     * @param storage  The storage manager responsible for saving and loading task data.
     * @return A message confirming the addition of the deadline task and the updated task list size.
     * @throws IncorrectInputException If the description does not contain the required "/by" keyword for the deadline.
     */
    @Override
    public String execute(TaskList taskList, StorageManager storage) throws IncorrectInputException {
        String result = taskList.addContent("deadline", description);
        if (result.startsWith("Sorry!!")) {
            return result;
        }
        return "Tick-tock! I've set your deadline!\n" + result
                + "\nYou have " + taskList.getSize() + " tasks in the list. Let's go!!";
    }
}
