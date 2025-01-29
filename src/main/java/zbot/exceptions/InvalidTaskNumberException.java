package zbot.exceptions;

/**
 * Thrown when the task number provided is invalid.
 * This exception is typically thrown when a user attempts
 * to interact with a task using an out-of-range or non-existent
 * task number
 */
public class InvalidTaskNumberException extends Exception {
    public InvalidTaskNumberException (String message) {
        super(message);
    }
}