package zbot.tasks;

/**
 * Represents a task with a description and completion status.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a {@code Task} with the specified description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        assert description != null && !description.isBlank() : "description cannot be null or empty";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Creates a copy of the current task.
     * This method is intended to be overridden by subclasses of {@link Task}.
     * It should return a new instance of the task with the same description and relevant state.
     *
     * @return A new {@link Task} object with the same description and relevant state as the current task.
     */
    public abstract Task copy();

    /**
     * Marks the task as done.
     */
    public void markDone() {
        isDone = true;
    }

    /**
     * Marks the task as undone.
     */
    public void markUndone() {
        isDone = false;
    }

    /**
     * Returns the completion status of the task.
     *
     * @return {@code true} if the task is done, otherwise {@code false}.
     */
    public boolean getDoneStatus() {
        return this.isDone;
    }

    /**
     * Returns the description of the task.
     *
     * @return A string representation of the task's description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the status icon for the task.
     * The status icon is represented by an "X" if the task is done, or a blank space if the task is not done.
     *
     * @return A string representing the task's completion status as an icon.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns a string representation of the task.
     * The string includes the task's status icon and description.
     *
     * @return A string representing the task in the format: "[statusIcon] description".
     */
    @Override
    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), description);
    }
}


