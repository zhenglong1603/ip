package zbot.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import zbot.Zbot;

/**
 * The entry point for the Zbot GUI application using JavaFX.
 * This class initializes and launches the JavaFX application.
 */
public class Main extends Application {

    private Zbot zbot = new Zbot("./data/Zbot.txt");

    /**
     * Starts the JavaFX application by loading the FXML layout
     * and setting up the main window.
     *
     * @param stage The primary stage for the application.
     */
    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("ZBot");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setZbot(zbot);
            fxmlLoader.<MainWindow>getController().initializeZbot();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
