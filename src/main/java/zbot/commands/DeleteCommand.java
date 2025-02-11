package zbot.commands;

import zbot.StorageManager;
import zbot.tasks.TaskList;

/**
 * Represents a command to delete a task from the task list based on the task index.
 */
public class DeleteCommand implements Command {
    private final int taskIndex;

    /**
     * Constructs a DeleteCommand with the specified task index.
     *
     * @param taskIndex The index of the task to delete from the task list.
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Executes the "Delete" command by removing the task at the specified index from the task list.
     *
     * @param taskList The task list from which the task will be deleted.
     * @param storage  The storage manager responsible for saving and loading task data.
     * @return A message confirming the deletion of the task and the updated task list size.
     * @throws Exception If the deletion process encounters an error.
     */
    @Override
    public String execute(TaskList taskList, StorageManager storage) throws Exception {
        StringBuilder output = new StringBuilder();
        output.append("OK, I've removed this task from the list:\n");
        output.append(taskList.deleteContent(taskIndex)).append("\n");
        output.append("Now you have ").append(taskList.getSize()).append(" tasks in the list.\n");
        return output.toString();
    }
}

