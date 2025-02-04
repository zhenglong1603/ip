package zbot.exceptions;

/**
 * Thrown when an operation is attempted on an empty task list.
 * This exception signals that the task list is empty and the
 * operation cannot be completed.
 */
public class EmptyTaskListException extends Exception {
    public EmptyTaskListException(String message) {
        super(message);
    }
}
