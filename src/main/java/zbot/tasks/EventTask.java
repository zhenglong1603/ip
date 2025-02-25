package zbot.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a start and end date.
 * Extends the {@link Task} class.
 */
public class EventTask extends Task {
    private final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private LocalDate fromDate;
    private LocalDate toDate;

    /**
     * Constructs an {@code EventTask} with the specified description, start date, and end date.
     * The dates are expected to be in the format "yyyy-MM-dd".
     *
     * @param description The description of the event task.
     * @param fromDate The start date of the event in "yyyy-MM-dd" format.
     * @param toDate The end date of the event in "yyyy-MM-dd" format.
     */
    public EventTask(String description, String fromDate, String toDate) throws IllegalArgumentException {
        super(description);
        this.fromDate = LocalDate.parse(fromDate, inputFormatter);
        this.toDate = LocalDate.parse(toDate, inputFormatter);

        if (this.toDate.isBefore(this.fromDate)) {
            throw new IllegalArgumentException(
                    "Sorry!! The 'to' date cannot be earlier than the 'from' date. Maybe check again?");
        }
    }

    /**
     * Returns the start date of the event formatted as a string in the format "yyyy-MM-dd".
     *
     * @return A string representation of the start date in the format "yyyy-MM-dd".
     */
    public String getFromDate() {
        return this.fromDate.format(inputFormatter);
    }

    /**
     * Returns the end date of the event formatted as a string in the format "yyyy-MM-dd".
     *
     * @return A string representation of the end date in the format "yyyy-MM-dd".
     */
    public String getToDate() {
        return this.toDate.format(inputFormatter);
    }

    /**
     * Returns the start date of the event formatted as a string in the format "MMM dd yyyy".
     *
     * @return A string representation of the start date in the format "MMM dd yyyy".
     */
    public String fromDateString() {
        return this.fromDate.format(outputFormatter);
    }

    /**
     * Returns the end date of the event formatted as a string in the format "MMM dd yyyy".
     *
     * @return A string representation of the end date in the format "MMM dd yyyy".
     */
    public String toDateString() {
        return this.toDate.format(outputFormatter);
    }

    /**
     * Creates a copy of the current task.
     * This method is intended to return a new instance of the task with the same description and state.
     *
     * @return A new {@link Task} object with the same description and dates as the current task.
     */
    @Override
    public Task copy() {
        EventTask taskCopy = new EventTask(this.getDescription(), this.getFromDate(), this.getToDate());
        if (this.getDoneStatus()) {
            taskCopy.markDone();
        } else {
            taskCopy.markUndone();
        }
        return taskCopy;
    }

    /**
     * Returns a string representation of the {@code EventTask}.
     * The string includes the task's description, completion status, and the event's date range.
     *
     * @return A string representing the task in the format: "[E][done status] (from: fromDate to: toDate)".
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromDateString() + " to: " + toDateString() + ")";
    }
}
