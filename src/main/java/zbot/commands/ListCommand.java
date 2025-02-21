package zbot.commands;

import zbot.StorageManager;
import zbot.tasks.Task;
import zbot.tasks.TaskList;

/**
 * Represents a command to list all tasks in the task list.
 */
public class ListCommand implements Command {

    /**
     * Executes the "List" command by returning all the tasks in the task list.
     *
     * @param taskList The task list containing the tasks.
     * @param storage  The storage manager responsible for storing task data.
     * @return A message listing all the tasks in the task list.
     */
    @Override
    public String execute(TaskList taskList, StorageManager storage) {
        StringBuilder output;
        if (!taskList.getTaskList().isEmpty()) {
            output = new StringBuilder("Here's your to-do list! Looking busy, I see!\n");
            int index = 1;
            for (Task t : taskList.getTaskList()) {
                output.append(index).append(".").append(t.toString()).append("\n");
                index++;
            }
        } else {
            output = new StringBuilder("Your current list looks empty! Add some tasks!!\n");
        }
        return output.toString();
    }
}

