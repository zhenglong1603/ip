package zbot.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 * Extends the {@link Task} class.
 */
public class DeadlineTask extends Task {
    private final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private LocalDate deadline;

    /**
     * Constructs a {@code DeadlineTask} with the specified description and deadline.
     * The deadline is expected to be in the format "yyyy-MM-dd".
     *
     * @param description The description of the task.
     * @param deadline The deadline of the task in "yyyy-MM-dd" format.
     */
    public DeadlineTask(String description, String deadline) {
        super(description);
        this.deadline = LocalDate.parse(deadline);

    }

    /**
     * Returns the deadline formatted as a string in the format "MMM dd yyyy".
     *
     * @return A string representation of the deadline in the format "MMM dd yyyy".
     */
    public String deadlineString() {
        return this.deadline.format(outputFormatter);
    }

    /**
     * Returns the deadline of the task as a string in the format "yyyy-MM-dd".
     *
     * @return A string representation of the deadline in the format "yyyy-MM-dd".
     */
    public String getDeadline() {
        return this.deadline.format(inputFormatter);
    }

    /**
     * Creates a copy of the current task.
     * This method is intended to return a new instance of the task with the same description and state.
     *
     * @return A new {@link Task} object with the same description and deadline as the current task.
     */
    @Override
    public Task copy() {
        DeadlineTask taskCopy = new DeadlineTask(this.getDescription(), this.getDeadline());
        if (this.getDoneStatus()) {
            taskCopy.markDone();
        } else {
            taskCopy.markUndone();
        }
        return taskCopy;
    }

    /**
     * Returns a string representation of the {@code DeadlineTask}.
     * The string includes the task's description, completion status, and deadline.
     *
     * @return A string representing the task in the format: "[D][done status](by: deadline)".
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadlineString() + ")";
    }
}
