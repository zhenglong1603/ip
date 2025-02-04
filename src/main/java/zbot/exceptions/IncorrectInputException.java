package zbot.exceptions;

/**
 * Thrown when the command input is missing required information.
 * This exception is typically thrown when a user provides incomplete
 * input, such as missing parameters or missing fields that are
 * necessary for executing a command.
 */
public class IncorrectInputException extends Exception {
    public IncorrectInputException(String message) {
        super(message);
    }
}
