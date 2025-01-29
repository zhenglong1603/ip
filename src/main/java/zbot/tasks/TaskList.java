package zbot.tasks;

import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks with functionality to manage and manipulate them.
 */
public class TaskList {
    List<Task> taskList;

    /**
     * Constructs an empty {@code TaskList}.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Constructs a {@code TaskList} with an existing list of tasks.
     *
     * @param exisitingTaskList The existing list of tasks.
     */
    public TaskList(List<Task> exisitingTaskList) {
        this.taskList = exisitingTaskList;
    }

    /**
     * Adds a new task to the list based on the task type.
     * Depending on the type, either a ToDoTask, EventTask, or DeadlineTask is added.
     *
     * @param type The type of task to add (e.g., "todo", "event", "deadline").
     * @param description The description of the task, including additional information
     *                    like date for event or deadline tasks.
     */
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

    /**
     * Deletes a task from the list based on the index.
     *
     * @param index The index of the task to remove.
     */
    public void deleteContent(int index) {
        Task deletedTask = taskList.get(index);
        taskList.remove(index);
        System.out.println("---------------------------------------------------");
        System.out.println("Noted. I've removed this task:");
        System.out.println(deletedTask.toString());
        System.out.printf("Now you have %d tasks in the list.%n", taskList.size());
        System.out.println("---------------------------------------------------");
    }

    /**
     * Marks a task as done based on the index.
     *
     * @param index The index of the task to mark as done.
     */
    public void markTask(int index) {
        taskList.get(index).markDone();
        System.out.println("---------------------------------------------------");
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(taskList.get(index).toString());
        System.out.println("---------------------------------------------------");
    }

    /**
     * Marks a task as undone based on the index.
     *
     * @param index The index of the task to mark as undone.
     */
    public void unmarkTask(int index) {
        taskList.get(index).markUndone();
        System.out.println("---------------------------------------------------");
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(taskList.get(index).toString());
        System.out.println("---------------------------------------------------");
    }

    /**
     * Clears all tasks from the list.
     */
    public void clearTasks() {
        this.taskList.clear();
    }

    /**
     * Returns the taskList which matches the keyword
     *
     * @return list of task withmatching keyword
     */
    public List<Task> findTasks(String word) {
        List<Task> ans = new ArrayList<>();
        for (Task curr : this.taskList) {
            if (curr.getDescription().contains(word)) {
                ans.add(curr);
            }
        }
        return ans;
    }
    /**
     * Returns the size of the task list.
     *
     * @return The number of tasks in the list.
     */
    public int getSize() {
        return this.taskList.size();
    }

    /**
     * Returns the list of tasks.
     *
     * @return The list of tasks in the task list.
     */
    public List<Task> getTaskList() {
        return this.taskList;
    }
}
