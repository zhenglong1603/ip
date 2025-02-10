package zbot.tasks;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks with functionality to manage and manipulate them.
 */
public class TaskList {
    private List<Task> taskList;

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
    public String addContent(String type, String description) {
        switch(type) {
        case "todo":
            Task newToDoTask = new ToDoTask(description);
            taskList.add(newToDoTask);
            return newToDoTask.toString();
        case "event":
            Task newEventTask;
            try {
                String[] eventParts = description.split(" /");
                newEventTask = new EventTask(eventParts[0], eventParts[1].substring(5), eventParts[2].substring(3));
                taskList.add(newEventTask);
                return newEventTask.toString();
            } catch (DateTimeParseException e) {
                return "Sorry!! Please use yyyy-MM-dd as the proper date format and provide both start and end times.";
            }
        case "deadline":
            Task newDeadlineTask;
            try {
                String[] deadlineParts = description.split(" /");
                newDeadlineTask = new DeadlineTask(deadlineParts[0], deadlineParts[1].substring(3));
                taskList.add(newDeadlineTask);
                return newDeadlineTask.toString();
            } catch (DateTimeParseException e) {
                return "Sorry!! Please use yyyy-MM-dd as the proper date format and provide both start and end times.";
            }
        default:
            return "Sorry!! I didn't recognise that task type. Please use 'todo', 'event', or 'deadline'.";
        }
    }

    /**
     * Deletes a task from the list based on the index.
     *
     * @param index The index of the task to remove.
     */
    public String deleteContent(int index) {
        Task deletedTask = taskList.get(index);
        taskList.remove(index);
        return deletedTask.toString();
    }

    /**
     * Marks a task as done based on the index.
     *
     * @param index The index of the task to mark as done.
     */
    public String markTask(int index) {
        taskList.get(index).markDone();
        return taskList.get(index).toString();
    }

    /**
     * Marks a task as undone based on the index.
     *
     * @param index The index of the task to mark as undone.
     */
    public String unmarkTask(int index) {
        taskList.get(index).markUndone();
        return taskList.get(index).toString();
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
