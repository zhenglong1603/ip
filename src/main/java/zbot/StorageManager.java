package zbot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import zbot.exceptions.ZbotFileNotFoundException;
import zbot.tasks.DeadlineTask;
import zbot.tasks.EventTask;
import zbot.tasks.Task;
import zbot.tasks.TaskList;
import zbot.tasks.ToDoTask;

/**
 * Represents a class that acts as storage for persisting and managing information.
 * <p>
 * This class is responsible for:
 * <ul>
 *   <li>Storing data, such as tasks</li>
 *   <li>Retrieving stored data for further processing</li>
 * </ul>
 * The storage class ensures that data is properly stored and can be accessed when needed
 * by other components of the application.
 */
class StorageManager {
    private final String filePath;
    public StorageManager(String filePath) {
        this.filePath = filePath;
    }


    /**
     * Loads the existing task list from filePath
     * <p>
     * This method reads the task data from a file, parses it, and creates a list of tasks based on the file's contents.
     * The file format is assumed to have tasks represented by a type identifier
     * and task details, with each task on a new line.
     * If a task is marked as done (indicated by a "1" in the file), it is marked as completed when created.
     *
     * @return a list of tasks loaded from the file
     * @throws ZbotFileNotFoundException if the file cannot be found
     * @throws IOException if the file format is invalid or there is an error reading the file
     */
    public List<Task> loadExistingFile() throws ZbotFileNotFoundException, IOException {
        List<Task> ans = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new ZbotFileNotFoundException("The data could not be found");
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String curr = scanner.nextLine();
                String[] parts = curr.split(" \\| ");

                if (parts.length < 3) {
                    throw new IOException("File format is invalid");
                }

                Task task = getTask(parts);
                ans.add(task);
            }
        }
        return ans;
    }

    /**
     * This method gets the task from an input
     * - "T" for ToDoTask
     * - "D" for DeadlineTask
     * - "E" for EventTask
     * The method also checks if the task input is in the correct format.
     *
     * @param parts input to be parsed to get th task
     * @throws IOException if the format for the task is invalid
     */
    private static Task getTask(String[] parts) throws IOException {
        Task task;

        switch (parts[0]) {
        case "T":
            task = new ToDoTask(parts[2]);
            break;
        case "D":
            if (parts.length < 4) {
                throw new IOException("Invalid deadline format");
            }
            task = new DeadlineTask(parts[2], parts[3]);
            break;
        case "E":
            if (parts.length < 5) {
                throw new IOException("Invalid event format");
            }
            task = new EventTask(parts[2], parts[3], parts[4]);
            break;
        default:
            throw new IOException("File format is invalid");
        }

        if (parts[1].equals("1")) {
            task.markDone();
        }

        return task;
    }

    /**
     * This method saves all tasks in the given task list to a file.
     * Each task is serialized in a specific format:
     * - "T" for ToDoTask
     * - "D" for DeadlineTask
     * - "E" for EventTask
     * The file will include the task's done status (1 for done, 0 for not done), description
     * and for specific task types, additional details such as deadline or event dates.
     * <p>
     * If the directory where the file is to be stored does not exist, it will be created.
     *
     * @param taskList the task list containing tasks to be saved
     * @throws IOException if an error occurs while creating directories or writing to the file
     */
    public void saveToFile(TaskList taskList) throws IOException {
        assert taskList != null : "taskList cannot be null";
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Sorry !! Failed to create directory: "
                        + parentDir.getAbsolutePath());
            }
        }
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            StringBuilder s = new StringBuilder();
            for (Task task : taskList.getTaskList()) {
                s.append(formatTask(task)).append("\n");
            }
            fileWriter.write(s.toString());
        } catch (IOException exception) {
            throw new IOException("Sorry!! Error occurred while writing to the file: "
                    + exception.getMessage(), exception);
        }
    }

    /**
     * Formats a task into a string representation based on its type.
     *
     * @param task The task to be formatted.
     * @return A string representation of the task.
     */
    private String formatTask(Task task) {
        StringBuilder taskString = new StringBuilder();

        if (task instanceof ToDoTask) {
            taskString.append("T | ")
                    .append(task.getDoneStatus() ? "1 | " : "0 | ")
                    .append(task.getDescription());
        } else if (task instanceof DeadlineTask) {
            taskString.append("D | ")
                    .append(task.getDoneStatus() ? "1 | " : "0 | ")
                    .append(task.getDescription())
                    .append(" | ")
                    .append(((DeadlineTask) task).getDeadline());
        } else if (task instanceof EventTask) {
            taskString.append("E | ")
                    .append(task.getDoneStatus() ? "1 | " : "0 | ")
                    .append(task.getDescription())
                    .append(" | ")
                    .append(((EventTask) task).getFromDate())
                    .append(" | ")
                    .append(((EventTask) task).getToDate());
        }
        return taskString.toString();
    }
}
