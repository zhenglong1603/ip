import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
public class ZBOT {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private void generateResponse(String input) {
        switch(input) {
            case "start":
                System.out.println("---------------------------------------------------");
                System.out.println("Hello! I'm ZBOT!!");
                System.out.println("What can I do for you?");
                System.out.println("---------------------------------------------------");
                break;
            case "end":
                System.out.println("---------------------------------------------------");
                System.out.println("Bye. Hope to see you again soon :)");
                System.out.println("---------------------------------------------------");
                break;
        }
    }

    private void parseInput(String input) throws IncorrectInputException, InvalidTaskException, EmptyTaskListException, InvalidTaskNumberException {
        String[] parts = input.split(" ", 2);
        final String SUPPORTED_COMMANDS = "- list\n- mark\n- unmark\n- todo\n- deadline\n- event\n- bye";
        switch (parts[0]) {
            case "list":
                if (parts.length == 1) {
                    showContents();
                } else {
                    throw new IncorrectInputException("Sorry!! Please ensure your command matches the following example and have nothing else. " +
                            "(e.g. \"list\")");
                }
                break;
            case "delete":
                if (parts.length != 2) {
                    throw new IncorrectInputException("Sorry!! Please ensure your command matches the example: \"delete 1\"");
                }

                int deleteIndex = getIndex(parts);
                deleteContent(deleteIndex);
                break;
            case "mark":
                if (parts.length != 2) {
                    throw new IncorrectInputException("Sorry!! Please ensure your command matches the example: \"mark 1\"");
                }

                int markIndex = getIndex(parts);
                markTask(markIndex);
                break;
            case "unmark":
                if (parts.length != 2) {
                    throw new IncorrectInputException("Sorry!! Please ensure your command matches the example: \"unmark 1\"");
                }

                int unmarkIndex = getIndex(parts);
                unmarkTask(unmarkIndex);
                break;
            case "todo":
                if (parts.length == 2) {
                    addContent(parts[0], parts[1]);
                } else {
                    throw new IncorrectInputException("Sorry!! Please ensure your command matches the following example and has a description after your command. " +
                            "(e.g. \"todo read a book\")");
                }
                break;
            case "deadline":
                if (parts.length == 2) {
                    addContent(parts[0], parts[1]);
                } else {
                    throw new IncorrectInputException("Sorry!! Please ensure your command matches the following example and has a description and deadline after your command. " +
                            "(e.g. \"deadline description /by \"deadline\" \")");
                }
                break;
            case "event":
                if (parts.length == 2) {
                    addContent(parts[0], parts[1]);
                } else {
                    throw new IncorrectInputException("Sorry!! Please ensure your command matches the following example and has a description with the timeline after your command. " +
                            "(e.g. \"event description /from \"start_time\" /to \"end_time\" \")");
                }
                break;
            default:
                throw new InvalidTaskException("Sorry!!  I didn't recognise that request. These are the " +
                        "following supported commands:\n" + SUPPORTED_COMMANDS);
        }
    }

    private int getIndex(String[] parts) throws IncorrectInputException, EmptyTaskListException, InvalidTaskNumberException {
        int markIndex;
        try {
            markIndex = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IncorrectInputException("Sorry!! The task number must be a valid integer (e.g., \"mark 1\").");
        }

        if (tasks.isEmpty()) {
            throw new EmptyTaskListException("Sorry!! The current task list is empty. Please add some tasks first.");
        }

        if (markIndex < 0 || markIndex >= tasks.size()) {
            throw new InvalidTaskNumberException("Sorry!! The task number is invalid. Please check the task number again.");
        }
        return markIndex;
    }

    private void addContent(String type, String description) {
        switch(type) {
            case "todo":
                Task newToDoTask = new ToDoTask(description);
                tasks.add(newToDoTask);
                System.out.println("---------------------------------------------------");
                System.out.println("Got it. I've added this task:");
                System.out.println(newToDoTask);
                System.out.printf("Now you have %d tasks in the list.%n", tasks.size());
                System.out.println("---------------------------------------------------");
                break;
            case "event":
                String[] eventParts = description.split(" /");
                Task newEventTask = new EventTask(eventParts[0], eventParts[1].substring(5), eventParts[2].substring(3));
                tasks.add(newEventTask);
                System.out.println("---------------------------------------------------");
                System.out.println("Got it. I've added this task:");
                System.out.println(newEventTask);
                System.out.printf("Now you have %d tasks in the list.%n", tasks.size());
                System.out.println("---------------------------------------------------");
                break;
            case "deadline":
                String[] parts = description.split(" /by ");
                Task newDeadlineTask = new DeadlineTask(parts[0], parts[1]);
                tasks.add(newDeadlineTask);
                System.out.println("---------------------------------------------------");
                System.out.println("Got it. I've added this task:");
                System.out.println(newDeadlineTask);
                System.out.printf("Now you have %d tasks in the list.%n", tasks.size());
                System.out.println("---------------------------------------------------");
                break;
        }
    }

    private void deleteContent(int index) {
        Task deletedTask = tasks.get(index);
        tasks.remove(index);
        System.out.println("---------------------------------------------------");
        System.out.println("Noted. I've removed this task:");
        System.out.println(deletedTask.toString());
        System.out.printf("Now you have %d tasks in the list.%n", tasks.size());
        System.out.println("---------------------------------------------------");
    }

    private void showContents() {
        int size = tasks.size();
        System.out.println("---------------------------------------------------");
        System.out.println("Here are the tasks in your list:");
        for (int i = 1; i < size + 1; i++) {
            System.out.println(i + ". " + tasks.get(i - 1).toString());
        }
        System.out.println("---------------------------------------------------");
    }

    private void markTask(int index) {
            tasks.get(index).markDone();
            System.out.println("---------------------------------------------------");
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(tasks.get(index).toString());
            System.out.println("---------------------------------------------------");
    }

    private void unmarkTask(int index) {
        tasks.get(index).markUndone();
        System.out.println("---------------------------------------------------");
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(tasks.get(index).toString());
        System.out.println("---------------------------------------------------");
    }

    private void loadExistingFile(String filePath) throws IOException {
        try {
            File f = new File(filePath);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String curr = s.nextLine();
                String[] parts = curr.split(" \\| ");
                switch (parts[0]) {
                    case "T" -> {
                        ToDoTask i = new ToDoTask(parts[2]);
                        if (parts[1].equals("1")) {
                            i.markDone();
                        }
                        tasks.add(i);
                    }
                    case "D" -> {
                        DeadlineTask i = new DeadlineTask(parts[2], parts[3]);
                        if (parts[1].equals("1")) {
                            i.markDone();
                        }
                        tasks.add(i);
                    }
                    case "E" -> {
                        EventTask i = new EventTask(parts[2], parts[3], parts[4]);
                        if (parts[1].equals("1")) {
                            i.markDone();
                        }
                        tasks.add(i);
                    }
                    default -> throw new IOException("File format is invalid");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not load existing data");
        }
    }

    private void saveToFile(String filePath) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Sorry !! Failed to create directory: " + parentDir.getAbsolutePath());
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            StringBuilder s = new StringBuilder();
            for (Task task : tasks) {
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filePath = "./data/ZBOT.txt";
        ZBOT myBot = new ZBOT();
        String input;
        boolean isRunning = true;
        myBot.generateResponse("start");

        try {
            myBot.loadExistingFile(filePath);
            myBot.showContents();
        } catch (IOException e) {
            System.out.println("---------------------------------------------------");
            System.out.println(e.getMessage());
            System.out.println("---------------------------------------------------");
        }

        while (isRunning) {
            input = scanner.nextLine();

            if (input.equals("bye")) {
                isRunning = false;
            } else {
                try {
                    myBot.parseInput(input);
                } catch (InvalidTaskException | IncorrectInputException | EmptyTaskListException | InvalidTaskNumberException e) {
                    System.out.println("---------------------------------------------------");
                    System.out.println(e.getMessage());
                    System.out.println("---------------------------------------------------");
                }
            }
        }

        try {
            myBot.saveToFile(filePath);
        } catch (IOException e) {
            System.out.println("-----------------------");
            System.out.println(e.getMessage());
            System.out.println("-----------------------");
        }

        myBot.generateResponse("end");
    }
}

