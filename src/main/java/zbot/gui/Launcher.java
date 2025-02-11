package zbot.gui;

import javafx.application.Application;

/**
 * The launcher class for the Zbot application.
 * This class serves as an entry point to launch the JavaFX application,
 * working around potential classpath issues that may arise when running JavaFX programs.
 */
public class Launcher {

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}

