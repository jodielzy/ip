package lucy;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import javafx.application.Platform;

/**
 * Controller for MainWindow.
 */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Lucy lucy;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.jpg"));
    private Image lucyImage = new Image(this.getClass().getResourceAsStream("/images/Lucy.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Lucy instance.
     */
    public void setLucy(Lucy lucy) {
        this.lucy = lucy;
    }

    /**
     * Handle user input and response.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = lucy.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getLucyDialog(response,lucyImage)
        );
        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            Platform.exit();
        }
    }
}
