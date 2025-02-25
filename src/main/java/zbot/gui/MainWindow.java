package zbot.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import zbot.Zbot;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    private Zbot zbot;

    private Image userImage = new
            Image(this.getClass().getResourceAsStream("/images/Dog.png"));
    private Image dukeImage = new
            Image(this.getClass().getResourceAsStream("/images/Cat.png"));

    /**
     * Initializes the Zbot GUI by setting up the dialog container and displaying an introductory message.
     * This method is called during the initialization of the main window.
     * It binds the scroll pane's vertical value property to the height of the dialog container
     * and adds a welcome message from Zbot to the dialog container.
     */
    @FXML
    public void initializeZbot() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBox.getZbotDialog(
                "Hello! I'm ZoZo! How can I assist you today?", dukeImage));
    }

    /** Injects the Zbot instance */
    public void setZbot(Zbot zbot) {
        this.zbot = zbot;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = zbot.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getZbotDialog(response, dukeImage)
        );
        userInput.clear();
    }
}
