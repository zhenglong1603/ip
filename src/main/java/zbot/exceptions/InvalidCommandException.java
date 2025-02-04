package zbot.exceptions;

/**
 * Thrown when the command refers to an invalid task.
 * This exception is typically thrown when a task
 * or is not recognized as a valid task by the application.
 */
public class InvalidCommandException extends Exception {
    public InvalidCommandException(String message) {
        super(message);
    }
}
