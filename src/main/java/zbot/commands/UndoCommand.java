package zbot.commands;

import java.io.IOException;

import zbot.StorageManager;
import zbot.tasks.TaskList;

/**
 * Represents a command to undo and restore the task list to the previous edition
 */
public class UndoCommand implements Command {
    @Override
    public String execute(TaskList taskList, StorageManager storage) throws IOException {
        boolean isRestored;
        isRestored = taskList.restore();
        if (isRestored) {
            return "Previous list was restored successfully.";
        } else {
            return "Previous list was not restored successfully.";
        }
    }
}
