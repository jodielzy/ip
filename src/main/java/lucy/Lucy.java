package lucy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the main application logic for Lucy.
 */
public class Lucy {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a Lucy instance with a file path for task storage.
     * @param filePath The path to the storage file.
     */
    public Lucy(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.loadTasks());
    }

    /**
     * Runs the main loop of the application, handling user commands.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                String[] parsedCommand = Parser.parseCommand(fullCommand);

                switch (parsedCommand[0]) {
                    case "bye":
                        isExit = true;
                        ui.showMessage("Bye. Hope to see you soon!");
                        break;
                    case "list" :
                        tasks.listTasks();
                        break;
                    case "mark":
                        int markIndex = Integer.parseInt(parsedCommand[1]) - 1;
                        tasks.markTask(markIndex, true, storage);
                        ui.showMessage("Nice! I've marked this task as done.");
                        break;
                    case "unmark":
                        int unmarkIndex = Integer.parseInt(parsedCommand[1]) - 1;
                        tasks.markTask(unmarkIndex, false, storage);
                        ui.showMessage("I've marked this task as not done.");
                        break;
                    case "todo":
                        tasks.addTask(new Todo(parsedCommand[1]), storage);
                        ui.showMessage("Got it. I've added this task.");
                        break;
                    case "deadline":
                        if (!parsedCommand[1].contains(" /by ")) {
                            throw new LucyException("Invalid format! Use: deadline <task> /by yyyy-MM-dd");
                        }
                        String[] deadlineParts = parsedCommand[1].split(" /by ");
                        if (deadlineParts.length < 2 || deadlineParts[0].isEmpty() || deadlineParts[1].isEmpty()) {
                            throw new LucyException("Invalid format! Use: deadLine task> /by yyyy-MM-dd");
                        }
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate date = LocalDate.parse(deadlineParts[1], formatter);
                            tasks.addTask(new Deadline(deadlineParts[0], date), storage);
                            ui.showMessage("Got it. I've added this task.");
                        } catch (DateTimeParseException e) {
                            ui.showError("Invalid date format! Please use yyyy-MM-dd.");
                        }
                        break;
                    case "event":
                        String[] eventParts = parsedCommand[1].split(" /from | /to ");
                        tasks.addTask(new Event(eventParts[0], eventParts[1], eventParts[2]), storage);
                        ui.showMessage("Got it. I've added this task.");
                        break;
                    case "delete":
                        int deleteIndex = Integer.parseInt(parsedCommand[1]) - 1;
                        tasks.deleteTask(deleteIndex, storage);
                        ui.showMessage("Noted. I've removed this task.");
                        break;
                    default:
                        ui.showError("I'm sorry, but I don't know what that means.");
                        break;
                }
            } catch (LucyException | ArrayIndexOutOfBoundsException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * The main entry point of the application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Lucy("data/lucy.txt").run();
    }
}