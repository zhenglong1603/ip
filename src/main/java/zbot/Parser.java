package zbot;
import zbot.exceptions.*;
import zbot.tasks.TaskList;

public class Parser {
    public static void parseInput(String input, Ui ui, TaskList taskList) throws IncorrectInputException, InvalidTaskException, EmptyTaskListException, InvalidTaskNumberException {
        String[] parts = input.split(" ", 2);
        final String SUPPORTED_COMMANDS = "- list\n- mark\n- unmark\n- find\n- delete\n- todo\n- deadline\n- event\n- bye";
        switch (parts[0]) {
            case "list":
                if (parts.length == 1) {
                    ui.showContents(taskList);
                } else {
                    throw new IncorrectInputException("Sorry!! Please ensure your command matches the following example and have nothing else. " +
                            "(e.g. \"list\")");
                }
                break;
            case "delete":
                if (parts.length != 2) {
                    throw new IncorrectInputException("Sorry!! Please ensure your command matches the example: \"delete 1\"");
                }

                int deleteIndex = getIndex(parts,taskList);
                taskList.deleteContent(deleteIndex);
                break;
            case "mark":
                if (parts.length != 2) {
                    throw new IncorrectInputException("Sorry!! Please ensure your command matches the example: \"mark 1\"");
                }

                int markIndex = getIndex(parts, taskList);
                taskList.markTask(markIndex);
                break;
            case "unmark":
                if (parts.length != 2) {
                    throw new IncorrectInputException("Sorry!! Please ensure your command matches the example: \"unmark 1\"");
                }

                int unmarkIndex = getIndex(parts, taskList);
                taskList.unmarkTask(unmarkIndex);
                break;
            case "todo":
                if (parts.length == 2) {
                    taskList.addContent(parts[0], parts[1]);
                } else {
                    throw new IncorrectInputException("Sorry!! Please ensure your command matches the following example and has a description after your command. " +
                            "(e.g. \"todo read a book\")");
                }
                break;
            case "deadline":
                if (parts.length == 2) {
                    taskList.addContent(parts[0], parts[1]);
                } else {
                    throw new IncorrectInputException("Sorry!! Please ensure your command matches the following example and has a description and deadline after your command. " +
                            "(e.g. \"deadline description /by \"deadline\" \")");
                }
                break;
            case "event":
                if (parts.length == 2) {
                    taskList.addContent(parts[0], parts[1]);
                } else {
                    throw new IncorrectInputException("Sorry!! Please ensure your command matches the following example and has a description with the timeline after your command. " +
                            "(e.g. \"event description /from \"start_time\" /to \"end_time\" \")");
                }
                break;
            case "find":
                if (parts.length == 2) {
                    ui.displayFind(taskList.findTasks(parts[1]));
                } else {
                    throw new IncorrectInputException("Sorry!! Please ensure your command matches the following example" + " (e.g. \"find book\")");
                }
                break;
            default:
                throw new InvalidTaskException("Sorry!!  I didn't recognise that request. These are the " +
                        "following supported commands:\n" + SUPPORTED_COMMANDS);
        }
    }

    public static int getIndex(String[] parts, TaskList taskList) throws IncorrectInputException, EmptyTaskListException, InvalidTaskNumberException {
        int markIndex;
        try {
            markIndex = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IncorrectInputException("Sorry!! The task number must be a valid integer (e.g., \"mark 1\").");
        }

        if (taskList.getSize() == 0) {
            throw new EmptyTaskListException("Sorry!! The current task list is empty. Please add some tasks first.");
        }

        if (markIndex < 0 || markIndex >= taskList.getSize()) {
            throw new InvalidTaskNumberException("Sorry!! The task number is invalid. Please check the task number again.");
        }
        return markIndex;
    }
}
