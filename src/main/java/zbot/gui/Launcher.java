package zbot.gui;

import javafx.application.Application;

/**
 * The launcher class for the Zbot application.
 * This class serves as an entry point to launch the JavaFX application,
 * working around potential classpath issues that may arise when running JavaFX programs.
 */
public class Launcher {

    /**
     * Launches the JavaFX application by invoking {@code launch(args)}.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}

