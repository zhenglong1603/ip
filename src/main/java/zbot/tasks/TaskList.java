package zbot.tasks;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Represents a list of tasks with functionality to manage and manipulate them.
 */
public class TaskList {
    private List<Task> taskList;
    private Stack<ArrayList<Task>> undoHistory;

    /**
     * Constructs an empty {@code TaskList}.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
        this.undoHistory = new Stack<>();
    }

    /**
     * Constructs a {@code TaskList} with an existing list of tasks.
     *
     * @param existingTaskList The existing list of tasks.
     */
    public TaskList(List<Task> existingTaskList) {
        assert taskList != null : "taskList should not be null";
        this.taskList = existingTaskList;
        this.undoHistory = new Stack<>();
    }

    /**
     * Adds a new task to the task list based on the specified task type.
     * Depending on the type, a ToDoTask, EventTask, or DeadlineTask is created and added.
     *
     * @param type The type of task to add (e.g., "todo", "event", "deadline").
     * @param description The description of the task. For event and deadline tasks,
     *                    this should include additional information such as the date/time
     *                    in the format: "description /by yyyy-MM-dd" for deadlines
     *                    or "description /from yyyy-MM-dd /to yyyy-MM-dd" for events.
     * @return A string representation of the newly added task or an error message
     *         if the input format is incorrect.
     */
    public String addContent(String type, String description) {
        switch(type) {
        case "todo":
            Task newToDoTask = new ToDoTask(description);
            saveStateForUndo();
            taskList.add(newToDoTask);
            return newToDoTask.toString();
        case "event":
            Task newEventTask;
            try {
                String[] eventParts = description.split(" /");
                newEventTask = new EventTask(eventParts[0], eventParts[1].substring(5), eventParts[2].substring(3));
                saveStateForUndo();
                taskList.add(newEventTask);
                return newEventTask.toString();
            } catch (DateTimeParseException e) {
                return "Sorry!! Please use yyyy-MM-dd as the proper date format!";
            }
        case "deadline":
            Task newDeadlineTask;
            try {
                String[] deadlineParts = description.split(" /");
                newDeadlineTask = new DeadlineTask(deadlineParts[0], deadlineParts[1].substring(3));
                saveStateForUndo();
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
     * Deletes a task from the task list based on the specified index.
     *
     * @param index The index of the task to remove (0-based index).
     * @return A string representation of the deleted task.
     * @throws IndexOutOfBoundsException If the index is invalid or out of range.
     */
    public String deleteContent(int index) {
        Task deletedTask = taskList.get(index);
        saveStateForUndo();
        taskList.remove(index);
        return deletedTask.toString();
    }

    /**
     * Marks a task as done based on the specified index.
     *
     * @param index The index of the task to mark as done (0-based index).
     * @return A string representation of the updated task after marking it as done.
     * @throws IndexOutOfBoundsException If the index is invalid or out of range.
     */
    public String markTask(int index) {
        saveStateForUndo();
        taskList.get(index).markDone();
        return taskList.get(index).toString();
    }

    /**
     * Marks a task as undone based on the specified index.
     *
     * @param index The index of the task to mark as undone (0-based index).
     * @return A string representation of the updated task after marking it as undone.
     * @throws IndexOutOfBoundsException If the index is invalid or out of range.
     */
    public String unmarkTask(int index) {
        saveStateForUndo();
        taskList.get(index).markUndone();
        return taskList.get(index).toString();
    }

    /**
     * Returns the taskList which matches the keyword
     *
     * @return list of task with matching keyword
     */
    public List<Task> findTasks(String word) {
        assert word != null && !word.isBlank() : "keyword cannot be null or empty";
        List<Task> ans = new ArrayList<>();
        for (Task curr : this.taskList) {
            if (curr.getDescription().contains(word)) {
                ans.add(curr);
            }
        }
        return ans;
    }

    /**
     * Returns a boolean to indicate if previous task list was restored
     *
     * @return boolean indicating s of restoration of task list
     */
    public boolean restore() {
        if (!undoHistory.isEmpty()) {
            taskList = undoHistory.pop();
            return true;
        }
        return false;
    }

    /**
     * Saves the previous state of task list before an action
     *
     */
    private void saveStateForUndo() {
        undoHistory.push(new ArrayList<>(taskList));
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
