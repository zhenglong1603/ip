package zbot.tasks;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import zbot.Ui;

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
     * @param existingTaskList The existing list of tasks.
     */
    public TaskList(List<Task> existingTaskList) {
        this.taskList = existingTaskList;
    }

    /**
     * Adds a new task to the list based on the task type.
     * Depending on the type, either a ToDoTask, EventTask, or DeadlineTask is added.
     *
     * @param type The type of task to add (e.g., "todo", "event", "deadline").
     * @param description The description of the task, including additional information
     *                    like date for event or deadline tasks.
     */
    public void addContent(String type, String description, Ui ui) {
        switch(type) {
        case "todo":
            Task newToDoTask = new ToDoTask(description);
            taskList.add(newToDoTask);
            ui.printTaskResponse(newToDoTask, taskList.size());
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
                if (newEventTask != null) {
                    ui.printTaskResponse(newEventTask, taskList.size());
                }
            }
            break;
        case "deadline":
            Task newDeadlineTask = null;
            try {
                String[] deadlineParts = description.split(" /");
                newDeadlineTask = new DeadlineTask(deadlineParts[0], deadlineParts[1].substring(3));
                taskList.add(newDeadlineTask);
            } catch (DateTimeParseException e) {
                System.out.println("Sorry!! Please use yyyy-MM-dd as the proper date format");
            } finally {
                if (newDeadlineTask != null) {
                    ui.printTaskResponse(newDeadlineTask, taskList.size());
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
    public void deleteContent(int index, Ui ui) {
        Task deletedTask = taskList.get(index);
        taskList.remove(index);
        int size = taskList.size();
        String message1 = "Noted. I've removed this task:";
        String message2 = deletedTask.toString();
        String message3 = String.format("Now you have %d tasks in the list.%n", size);
        ui.printResponse(message1, message2, message3);
    }

    /**
     * Marks a task as done based on the index.
     *
     * @param index The index of the task to mark as done.
     */
    public void markTask(int index, Ui ui) {
        taskList.get(index).markDone();
        String message1 = "Nice! I've marked this task as done:";
        String message2 = taskList.get(index).toString();
        ui.printResponse(message1, message2);
    }

    /**
     * Marks a task as undone based on the index.
     *
     * @param index The index of the task to mark as undone.
     */
    public void unmarkTask(int index, Ui ui) {
        taskList.get(index).markUndone();
        String message1 = "OK, I've marked this task as not done yet:";
        String message2 = taskList.get(index).toString();
        ui.printResponse(message1, message2);
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
     * @return list of task with matching keyword
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
     * @return A copy of the list of tasks in the task list.
     */
    public List<Task> getTaskList() {
        return new ArrayList<>(this.taskList);
    }
}
