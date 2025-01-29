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
