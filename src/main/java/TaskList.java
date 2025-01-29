import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class TaskList {
    List<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public TaskList(List<Task> exisitingTaskList) {
        this.taskList = exisitingTaskList;
    }

    public void addContent(String type, String description) {
        switch(type) {
            case "todo":
                Task newToDoTask = new ToDoTask(description);
                taskList.add(newToDoTask);
                System.out.println("---------------------------------------------------");
                System.out.println("Got it. I've added this task:");
                System.out.println(newToDoTask);
                System.out.printf("Now you have %d tasks in the list.%n", taskList.size());
                System.out.println("---------------------------------------------------");
                break;
            case "event":
                Task newEventTask = null;
                try {
                    String[] eventParts = description.split(" /");
                    newEventTask = new EventTask(eventParts[0], eventParts[1].substring(5), eventParts[2].substring(3));
                    taskList.add(newEventTask);
                } catch (DateTimeParseException e) {
                    System.out.println("Sorry!! Please use yyyy-MM-dd as the proper date format");
                } finally {
                    if(newEventTask != null) {
                        System.out.println("---------------------------------------------------");
                        System.out.println("Got it. I've added this task:");
                        System.out.println(newEventTask);
                        System.out.printf("Now you have %d tasks in the list.%n", taskList.size());
                        System.out.println("---------------------------------------------------");
                    }
                }
                break;
            case "deadline":
                Task newDeadlineTask = null;
                try {
                    String[] parts = description.split("/by ");
                    newDeadlineTask = new DeadlineTask(parts[0], parts[1]);
                    taskList.add(newDeadlineTask);
                } catch (DateTimeParseException e) {
                    System.out.println("Sorry!! Please use yyyy-MM-dd as the proper date format");
                } finally {
                    if(newDeadlineTask != null) {
                        System.out.println("---------------------------------------------------");
                        System.out.println("Got it. I've added this task:");
                        System.out.println(newDeadlineTask);
                        System.out.printf("Now you have %d tasks in the list.%n", taskList.size());
                        System.out.println("---------------------------------------------------");
                    }
                }
                break;
        }
    }

    public void deleteContent(int index) {
        Task deletedTask = taskList.get(index);
        taskList.remove(index);
        System.out.println("---------------------------------------------------");
        System.out.println("Noted. I've removed this task:");
        System.out.println(deletedTask.toString());
        System.out.printf("Now you have %d tasks in the list.%n", taskList.size());
        System.out.println("---------------------------------------------------");
    }

    public void markTask(int index) {
        taskList.get(index).markDone();
        System.out.println("---------------------------------------------------");
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(taskList.get(index).toString());
        System.out.println("---------------------------------------------------");
    }

    public void unmarkTask(int index) {
        taskList.get(index).markUndone();
        System.out.println("---------------------------------------------------");
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(taskList.get(index).toString());
        System.out.println("---------------------------------------------------");
    }

    public int getSize() {
        return this.taskList.size();
    }

    public List<Task> getTaskList() {
        return this.taskList;
    }
}
