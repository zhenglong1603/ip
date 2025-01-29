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

public class StorageManager {
    String filePath;
    public StorageManager(String filePath) {
        this.filePath = filePath;
    }
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
