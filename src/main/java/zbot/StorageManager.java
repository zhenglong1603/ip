package zbot;

import zbot.exceptions.ZBOTFileNotFoundException;

import zbot.tasks.ToDoTask;
import zbot.tasks.EventTask;
import zbot.tasks.DeadlineTask;
import zbot.tasks.Task;
import zbot.tasks.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.util.Scanner;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

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
    String filePath;
    public StorageManager(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the existing task list from filePath
     *
     * This method reads the task data from a file, parses it, and creates a list of tasks based on the file's contents.
     * The file format is assumed to have tasks represented by a type identifier and task details, with each task on a new line.
     * If a task is marked as done (indicated by a "1" in the file), it is marked as completed when created.
     *
     * @return a list of tasks loaded from the file
     * @throws ZBOTFileNotFoundException if the file cannot be found
     * @throws IOException if the file format is invalid or there is an error reading the file
     */
    public List<Task> loadExistingFile() throws ZBOTFileNotFoundException, IOException {
        List<Task> ans = new ArrayList<>();
        try {
            File f = new File(filePath);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String curr = s.nextLine();
                String[] parts = curr.split(" \\| ");
                switch (parts[0]) {
                case "T" :
                    ToDoTask a = new ToDoTask(parts[2]);
                    if (parts[1].equals("1")) {
                        a.markDone();
                    }
                    ans.add(a);
                    break;
                case "D":
                    DeadlineTask b = new DeadlineTask(parts[2], parts[3]);
                    if (parts[1].equals("1")) {
                        b.markDone();
                    }
                    ans.add(b);
                    break;
                case "E" :
                    EventTask c = new EventTask(parts[2], parts[3], parts[4]);
                    if (parts[1].equals("1")) {
                        c.markDone();
                    }
                    ans.add(c);
                    break;
                default :
                    throw new IOException("File format is invalid");
                }
            }
        } catch (FileNotFoundException e) {
            throw new ZBOTFileNotFoundException("The data could not be found");
        }
        return ans;
    }

    /**
     * Saves the task list to a file.
     *
     * This method saves all tasks in the given task list to a file. Each task is serialized in a specific format:
     * - "T" for ToDoTask
     * - "D" for DeadlineTask
     * - "E" for EventTask
     * The file will include the task's done status (1 for done, 0 for not done), description, and for specific task types, additional details such as deadline or event dates.
     *
     * If the directory where the file is to be stored does not exist, it will be created.
     *
     * @param taskList the task list containing tasks to be saved
     * @throws IOException if an error occurs while creating directories or writing to the file
     */
    public void saveToFile(TaskList taskList) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Sorry !! Failed to create directory: " + parentDir.getAbsolutePath());
            }
        }
        try {
            StringBuilder s = new StringBuilder();
            FileWriter fileWriter = new FileWriter(filePath);
            for (Task task : taskList.getTaskList()) {
                if (task instanceof ToDoTask) {
                    s.append("T | ")
                            .append(task.getDoneStatus() ? "1 | " : "0 | ")
                            .append(task.getDescription())
                            .append("\n");
                } else if (task instanceof DeadlineTask) {
                    s.append("D | ")
                            .append(task.getDoneStatus() ? "1 | " : "0 | ")
                            .append(task.getDescription())
                            .append(" | ")
                            .append(((DeadlineTask) task).getDeadline())
                            .append("\n");
                } else if (task instanceof EventTask) {
                    s.append("E | ")
                            .append(task.getDoneStatus() ? "1 | " : "0 | ")
                            .append(task.getDescription())
                            .append(" | ")
                            .append(((EventTask) task).getFromDate())
                            .append(" | ")
                            .append(((EventTask) task).getToDate())
                            .append("\n");
                }
            }
            fileWriter.write(s.toString());
            fileWriter.close();
        } catch (IOException e) {
            throw new IOException("Sorry!! Error occurred while writing to the file: " + e.getMessage(), e);
        }
    }
}
