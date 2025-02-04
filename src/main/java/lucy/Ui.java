package lucy;

import java.util.Scanner;

/**
 * Handles user interface interactions.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Constructs a Ui instance.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a command from the user.
     * @return The user input string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        System.out.println("_________________________________");
        System.out.println("Hello! I'm lucy.Lucy");
        System.out.println("What can I do for you?");
        System.out.println("_________________________________");
    }

    /**
     * Displays a seperator line.
     */
    public void showLine() {
        System.out.println("_________________________________");
    }

    /**
     * Displays a message.
     * @param message The message to display.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Start of an error message.
     * @param message Displays all error messages starting with "OOPS!!!"
     */
    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
    }
}
