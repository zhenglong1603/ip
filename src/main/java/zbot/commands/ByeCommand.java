package zbot.commands;

import java.io.IOException;

import zbot.StorageManager;
import zbot.tasks.TaskList;

/**
 * Represents a command to save the current task list to a file and close the application.
 */
public class ByeCommand implements Command {

    /**
     * Executes the "Save" command by saving the current task list to a file.
     *
     * @param taskList The task list containing the tasks to be saved and close the application.
     * @param storage  The storage manager responsible for saving the task list.
     * @throws IOException If an I/O error occurs while saving the task list to a file.
     */
    @Override
    public String execute(TaskList taskList, StorageManager storage) throws IOException {
        storage.saveToFile(taskList);
        System.exit(0);
        return null;
    }
}

