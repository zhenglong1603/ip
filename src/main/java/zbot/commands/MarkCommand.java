package zbot.commands;

import zbot.StorageManager;
import zbot.exceptions.InvalidTaskNumberException;
import zbot.tasks.TaskList;

/**
 * Represents a command to mark a task as done in the task list.
 */
public class MarkCommand implements Command {
    private final int taskIndex;

    /**
     * Creates a MarkCommand object with the given task index.
     *
     * @param taskIndex The index of the task to mark as done.
     */
    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Executes the "Mark" command by marking the specified task as done.
     *
     * @param taskList The task list containing the tasks.
     * @param storage  The storage manager responsible for storing task data.
     * @return A message confirming that the task has been marked as done.
     * @throws InvalidTaskNumberException If the provided task index is invalid.
     */
    @Override
    public String execute(TaskList taskList, StorageManager storage) throws InvalidTaskNumberException {
        return "Nice! I've marked this task as done:\n" + taskList.markTask(taskIndex);
    }
}

