package zbot.gui;

import javafx.application.Application;

/**
 * The launcher class for the Zbot application.
 * This class serves as an entry point to launch the JavaFX application,
 * working around potential classpath issues that may arise when running JavaFX programs.
 */
public class Launcher {

    /**
     * Represents a launcher for the GUI.
     * This class is adopted from SE JavaFX tutorial:
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}

