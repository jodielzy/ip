import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showWelcome() {
        System.out.println("_________________________________");
        System.out.println("Hello! I'm Lucy");
        System.out.println("What can I do for you?");
        System.out.println("_________________________________");
    }

    public void showLine() {
        System.out.println("_________________________________");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
    }
}
