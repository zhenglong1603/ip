package zbot.commands;

import zbot.StorageManager;
import zbot.tasks.TaskList;

/**
 * Represents a generic command that can be executed on the task list.
 * Each command implementation will define its own execution logic.
 */
public interface Command {

    /**
     * Executes the command with the given task list and storage manager.
     *
     * @param taskList The task list on which the command will operate.
     * @param storage  The storage manager responsible for saving and loading task data.
     * @return A string message that provides feedback on the execution of the command.
     * @throws Exception If an error occurs during the execution of the command.
     */
    String execute(TaskList taskList, StorageManager storage) throws Exception;
}
