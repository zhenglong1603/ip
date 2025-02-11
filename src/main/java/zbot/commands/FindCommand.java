package zbot.commands;

import java.util.List;

import zbot.StorageManager;
import zbot.tasks.Task;
import zbot.tasks.TaskList;

/**
 * Represents a command to find tasks in the task list that match a given keyword.
 */
public class FindCommand implements Command {
    private final String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     *
     * @param keyword The keyword to search for in the task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the "Find" command by searching for tasks that match the keyword.
     *
     * @param taskList The task list containing all tasks.
     * @param storage  The storage manager responsible for saving and loading task data.
     * @return A message with the tasks that match the keyword.
     */
    @Override
    public String execute(TaskList taskList, StorageManager storage) {
        List<Task> tasks = taskList.findTasks(keyword);
        StringBuilder output = new StringBuilder("Here are the matching tasks in your list:\n");
        int index = 1;
        for (Task t : tasks) {
            output.append(index).append(". ").append(t.toString()).append("\n");
            index++;
        }
        return output.toString();
    }
}

