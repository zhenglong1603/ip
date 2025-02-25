package zbot.tasks;

/**
 * Represents a to-do task that extends the {@code Task} class.
 * A to-do task has a description and a completion status.
 */
public class ToDoTask extends Task {
    /**
     * Constructs a new {@code ToDoTask} with the specified description.
     * The task is initially marked as not done.
     *
     * @param description The description of the to-do task.
     */
    public ToDoTask(String description) {
        super(description);
    }

    /**
     * Creates a copy of the current task.
     * This method is intended to return a new instance of the task with the same description and state.
     *
     * @return A new {@link Task} object with the same description as the current task.
     */
    @Override
    public Task copy() {
        ToDoTask taskCopy = new ToDoTask(this.getDescription());
        if (this.getDoneStatus()) {
            taskCopy.markDone();
        } else {
            taskCopy.markUndone();
        }
        return taskCopy;
    }

    /**
     * Returns a string representation of the to-do task.
     * The format is "[T][status] description".
     *
     * @return A string representation of the to-do task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
