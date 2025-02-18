package zbot.commands;

import java.io.IOException;

import zbot.StorageManager;
import zbot.tasks.TaskList;

/**
 * Represents a command to save the current task list to a file.
 */
public class SaveCommand implements Command {

    /**
     * Executes the "Save" command by saving the current task list to a file.
     *
     * @param taskList The task list containing the tasks to be saved.
     * @param storage  The storage manager responsible for saving the task list.
     * @return A message indicating that the tasks have been successfully saved.
     * @throws IOException If an I/O error occurs while saving the task list to a file.
     */
    @Override
    public String execute(TaskList taskList, StorageManager storage) throws IOException {
        storage.saveToFile(taskList);
        return "Saved! Just in case your memory isn't as good as mine!";
    }
}

