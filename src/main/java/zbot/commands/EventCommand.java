package zbot.commands;

import zbot.StorageManager;
import zbot.exceptions.IncorrectInputException;
import zbot.tasks.TaskList;

/**
 * Represents a command to add an event task to the task list.
 * The event must include start and end times specified with /from and /to.
 */
public class EventCommand implements Command {
    private final String description;

    /**
     * Constructs an EventCommand with the specified description.
     *
     * @param description The description of the event, including start and end times.
     */
    public EventCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the "Event" command by adding an event task to the task list.
     * The description must contain both a start time (specified with /from) and an end time (specified with /to).
     *
     * @param taskList The task list to which the event task will be added.
     * @param storage  The storage manager responsible for saving and loading task data.
     * @return A message confirming the addition of the event task or an error message if input is incorrect.
     * @throws IncorrectInputException If the description does not contain both start and end times.
     */
    @Override
    public String execute(TaskList taskList, StorageManager storage) throws IncorrectInputException {
        if (!description.contains("/from") || !description.contains("/to")) {
            throw new IncorrectInputException("Hmm.. did you specify both start and end times using /from and /to?\n"
                    + "Example: \"event task /from start_time /to end_time\"");
        }
        String result = taskList.addContent("event", description);
        if (result.startsWith("Sorry!!")) {
            return result;
        }
        return "Event locked in! Let's hope it's fun!\n" + result
                + "\nYou have " + taskList.getSize() + " tasks in the list. Let's go!!";
    }
}
