package zbot.commands;

import zbot.StorageManager;
import zbot.exceptions.InvalidTaskNumberException;
import zbot.tasks.TaskList;

/**
 * Represents a command to unmark a task as incomplete.
 * This command will unmark a task in the task list based on its index.
 */
public class UnmarkCommand implements Command {
    private final int taskIndex;

    /**
     * Constructs an {@code UnmarkCommand} with the specified task index.
     *
     * @param taskIndex The index of the task to unmark as incomplete.
     */
    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Executes the unmark command by unmarking the task at the specified index.
     *
     * @param taskList The task list that contains the tasks to be unmarked.
     * @param storage  The storage manager to handle any file operations (not used in this command).
     * @return A message indicating the task has been unmarked, along with the task's details.
     * @throws InvalidTaskNumberException If the task index is invalid or out of bounds.
     */
    @Override
    public String execute(TaskList taskList, StorageManager storage) throws InvalidTaskNumberException {
        return "Alright, back on the to-do list it goes!\n" + taskList.unmarkTask(taskIndex);
    }
}

